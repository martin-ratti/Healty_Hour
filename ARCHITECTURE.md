# 🏗️ Arquitectura — Healty Hour

> Decisiones técnicas, patrones de diseño y la lógica detrás de la estructura del proyecto.

---

## 📐 Patrón: MVVM + Clean Architecture

```
┌─────────────────────────────────────────────┐
│              PRESENTATION                    │
│  (Compose Screens, ViewModels, Navigation)   │
│                                              │
│  Screen ←→ ViewModel ←→ UseCase              │
└─────────────┬───────────────────────────────┘
              │ (depende de)
┌─────────────▼───────────────────────────────┐
│               DOMAIN                         │
│  (Models, Repository Interfaces, UseCases)   │
│                                              │
│  UseCase → Repository (interface)            │
└─────────────┬───────────────────────────────┘
              │ (implementa)
┌─────────────▼───────────────────────────────┐
│                DATA                          │
│  (Room DB, UsageStatsManager, Repositories)  │
│                                              │
│  RepositoryImpl → DataSource / DAO           │
└─────────────────────────────────────────────┘
```

### ¿Por qué esta arquitectura?

| Principio | Beneficio |
|:---|:---|
| **Separación de responsabilidades** | Cada capa tiene un rol claro |
| **Independencia del framework** | El dominio no depende de Android |
| **Testeable** | Podés testear la lógica sin UI ni base de datos |
| **Escalable** | Agregar features no rompe lo existente |

---

## 📂 Capas en Detalle

### 1. Data Layer (`data/`)

Responsable de obtener y persistir datos. Tiene dos fuentes principales:

#### `data/local/usage/` — Lectura del sistema
- **`UsageDataSource`**: Wrapper de `UsageStatsManager` y `UsageEvents`
- Lee datos crudos de uso del teléfono
- Resuelve nombres e íconos de apps via `PackageManager`
- **No almacena nada**, solo lee

#### `data/local/db/` — Base de datos Room
- **Entities**: `DailyUsage`, `AppDailyUsage` (tablas de la DB)
- **DAOs**: Interfaces con queries SQL (consultas de los últimos 7 días, top apps, etc.)
- **Database**: Clase `HealthyHourDatabase` que configura Room

#### `data/repository/` — Implementaciones de repositorios
- **`UsageRepositoryImpl`**: Implementa la interfaz del dominio
- Combina datos de `UsageDataSource` (tiempo real) con Room (histórico)
- Decide de dónde leer según lo que se necesite

### 2. Domain Layer (`domain/`)

La lógica pura del negocio. **No importa ninguna dependencia de Android.**

#### `domain/model/` — Modelos de dominio
```kotlin
// Ejemplos:
data class AppUsageInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable?,
    val totalTimeMs: Long,
    val sessionCount: Int,
    val longestSessionMs: Long,
    val category: AppCategory?
)

data class DaySummary(
    val date: LocalDate,
    val totalScreenTimeMs: Long,
    val totalUnlocks: Int,
    val topApps: List<AppUsageInfo>,
    val longestSession: Session?,
    val peakHour: Int // 0-23
)

data class Session(
    val packageName: String,
    val startTime: Long,
    val endTime: Long,
    val durationMs: Long
)
```

#### `domain/repository/` — Interfaces
```kotlin
interface UsageRepository {
    suspend fun getTodaySummary(): DaySummary
    suspend fun getAppUsageToday(): List<AppUsageInfo>
    suspend fun getWeeklyTrend(): List<DaySummary>
    suspend fun getSessions(packageName: String, date: LocalDate): List<Session>
}
```

#### `domain/usecase/` — Casos de uso
Cada caso de uso tiene una sola responsabilidad:
- `GetDailySummaryUseCase`
- `GetTopAppsUseCase`
- `GetLongestSessionUseCase`
- `GetWeeklyTrendUseCase`
- `CalculatePeakHourUseCase`

### 3. Presentation Layer (`presentation/`)

Todo lo relacionado con la UI.

#### `presentation/screens/` — Pantallas
- **Home**: Dashboard principal con métricas del día
- **Detail**: Detalle de una app específica
- **History**: Tendencias semanales/mensuales
- **Settings**: Configuración de la app

#### `presentation/components/` — Componentes reutilizables
- `CircularProgressIndicator` animado para tiempo total
- `AppUsageCard` — Card con ícono, nombre y barra de progreso
- `SessionTimeline` — Timeline visual de sesiones
- `UsageChart` — Wrapper de Vico para gráficos

#### `presentation/theme/` — Diseño visual
- Paleta de colores (oscura con acentos)
- Tipografía
- Shapes y dimensiones

---

## 🔌 Inyección de Dependencias (Hilt)

```
@Module ── UsageModule
  ├── provideUsageStatsManager()
  ├── provideUsageDataSource()
  └── providePackageManager()

@Module ── DatabaseModule
  ├── provideDatabase()
  ├── provideDailyUsageDao()
  └── provideAppDailyUsageDao()

@Module ── RepositoryModule
  └── provideUsageRepository()
```

Cada ViewModel recibe sus UseCases por constructor injection:

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailySummary: GetDailySummaryUseCase,
    private val getTopApps: GetTopAppsUseCase,
    private val getLongestSession: GetLongestSessionUseCase
) : ViewModel() { ... }
```

---

## 🔄 Flujo de Datos

```
UI (Compose) observa State del ViewModel
         ↑ StateFlow
    ViewModel llama UseCase
         ↑ suspend fun
    UseCase llama Repository (interfaz)
         ↑ suspend fun
    RepositoryImpl consulta DataSource o Room
         ↑ UsageStatsManager / DAO
    Sistema Android / SQLite
```

**Principio: los datos fluyen hacia arriba, las dependencias apuntan hacia adentro.**

---

## 🧮 Motor de Métricas — `SessionCalculator`

El componente más importante de la lógica de negocio:

```
Eventos crudos del sistema (RESUMED / PAUSED)
         ↓
    SessionCalculator
    ├── Emparejar RESUMED → PAUSED por packageName
    ├── Manejar edge cases (sin PAUSED, sesiones superpuestas)
    ├── Timeout máximo de sesión (4 horas)
    └── Agrupar por franja horaria
         ↓
    Lista de Session + métricas derivadas
```

### Edge Cases a Manejar

| Caso | Solución |
|:---|:---|
| RESUMED sin PAUSED posterior | Usar el siguiente RESUMED de otra app como fin |
| Sesión que cruza medianoche | Dividir en dos sesiones |
| Múltiples RESUMED seguidos | Ignorar duplicados, tomar el primero |
| Timeout de sesión > 4h | Asumir que terminó, marcar como "estimada" |

---

## 📊 Base de Datos — Esquema

```
┌───────────────┐        ┌──────────────────┐
│  DailyUsage   │        │  AppDailyUsage   │
├───────────────┤        ├──────────────────┤
│ date (PK)     │───┐    │ id (PK)          │
│ totalScreen   │   │    │ date (FK)        │←──┐
│ totalUnlocks  │   └──→ │ packageName      │   │
│ longestSession│        │ appName          │   │
│ topApp        │        │ totalTimeMs      │   │
│ ...           │        │ sessionCount     │   │
└───────────────┘        │ longestSessionMs │   │
                         │ category         │   │
                         └──────────────────┘   │
                                                │
                         1 DailyUsage : N AppDailyUsage
```

---

## 🔐 Permisos

| Permiso | Tipo | Uso |
|:---|:---|:---|
| `PACKAGE_USAGE_STATS` | Especial (Settings) | Leer datos de uso del sistema |
| `POST_NOTIFICATIONS` | Runtime (API 33+) | Enviar notificaciones al usuario |
| `FOREGROUND_SERVICE` | Normal | Servicio para monitoreo en tiempo real |
| `RECEIVE_BOOT_COMPLETED` | Normal | Re-programar WorkManager después de reinicio |

---

*Documento actualizado: 25 de septiembre de 2026*
