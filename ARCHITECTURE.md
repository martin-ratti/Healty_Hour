# 🏛️ Arquitectura del Proyecto (Clean Architecture)

Este proyecto usa **Clean Architecture** (Arquitectura Limpia) junto con el patrón **MVVM** (Model-View-ViewModel). 

¡Vamos a explicarlo con una analogía muy simple! 🍔

## La Analogía del Restaurante 🍽️

Imagina que nuestra aplicación **TimeLens** es un restaurante muy elegante:

1.  **Capa `presentation` (El Comedor y los Meseros):**
    *   Es lo que el cliente ve y con lo que interactúa.
    *   **UI / Composables:** Son las mesas, las luces, la decoración (la pantalla de tu celular).
    *   **ViewModel:** Es el **mesero**. El mesero no cocina, solo toma tu pedido, va a la cocina, espera tu plato y te lo entrega en la mesa. El ViewModel recibe la orden (clicks) y trae los datos para mostrarlos en pantalla.
2.  **Capa `domain` (El Libro de Recetas y las Reglas de Etiqueta):**
    *   Es el corazón del negocio. Aquí no nos importa si cocinamos en estufa de gas o eléctrica (no nos importan las librerías de Android), solo nos importa *qué* se hace.
    *   **Models:** Son la definición de los ingredientes (ej: `AppUsageInfo`, `DaySummary`, `Session`).
    *   **Use Cases:** Son las reglas y casos de uso (ej: `GetDailySummaryUseCase`, `GetTopAppsUseCase`, `GetWeeklyTrendUseCase`).
3.  **Capa `data` (La Cocina y los Proveedores):**
    *   Es donde ocurre el trabajo duro de obtener los ingredientes.
    *   **Repository:** Es el **Chef supervisor** que orquesta los datos.
    *   **Data Sources / Room DAOs:** Los ayudantes que van a buscar a la base de datos local (`TimeLensDatabase`) o al sistema (`UsageDataSource`).

---

## 🗺️ Mapa de Archivos (¿Dónde está cada cosa?)

```text
app/src/main/java/com/timelens/app/
│
├── 📂 data/                 # 👨‍🍳 LA COCINA (Datos)
│   ├── 📂 local/            # Despensa local
│   │   ├── 📂 db/           # Base de datos Room
│   │   │   ├── TimeLensDatabase.kt
│   │   │   ├── 📂 dao/      # AppDailyUsageDao, DailyUsageDao
│   │   │   └── 📂 entity/   # AppDailyUsageEntity, DailyUsageEntity
│   │   └── 📂 usage/        # Proveedor del sistema Android
│   │       └── UsageDataSource.kt # Habla con UsageStatsManager del sistema
│   └── 📂 repository/       # Implementaciones de repositorios
│
├── 📂 domain/               # 📖 EL LIBRO DE RECETAS (Reglas de negocio)
│   ├── 📂 model/            # AppUsageInfo, DaySummary, Session
│   ├── 📂 repository/       # UsageRepository (contrato del Chef)
│   └── 📂 usecase/          # GetDailySummaryUseCase, GetTopAppsUseCase, GetWeeklyTrendUseCase
│
├── 📂 presentation/         # 🍽️ EL COMEDOR (Lo que ve el usuario)
│   ├── 📂 screens/          # Pantallas principales
│   │   ├── 📂 home/         # HomeScreen.kt
│   │   ├── 📂 history/      # HistoryScreen.kt
│   │   ├── 📂 settings/     # SettingsScreen.kt
│   │   └── 📂 onboarding/   # OnboardingScreen.kt
│   ├── 📂 navigation/       # AppNavHost.kt, NavRoutes.kt (Bottom Navigation)
│   ├── 📂 components/       # AppUsageCard, CircularProgressCard, StatCard
│   └── 📂 theme/            # Color.kt, Theme.kt (TimeLensTheme), Type.kt
│
├── 📂 di/                   # 🪄 INYECCIÓN DE DEPENDENCIAS (Hilt)
│   └── AppModule.kt         # Provee TimeLensDatabase y DAOs
│
├── 📂 util/                 # 🛠️ UTILIDADES
│   └── TimeFormatter.kt     # Formateador de tiempos y porcentajes
│
├── TimeLensApp.kt           # 🚀 Application class (Hilt)
└── MainActivity.kt          # 🚪 Entrada principal de la app
```

---

## 🔄 El Flujo de la Información (Diagrama)

```text
  📱 PANTALLA (HomeScreen / HistoryScreen / SettingsScreen)
         │
         │ (1) El usuario abre la app / Pide datos
         ▼
  🤵 MESERO (ViewModel)
         │
         │ (2) Invoca el UseCase correspondiente
         ▼
  📖 CASO DE USO (GetDailySummaryUseCase)
         │
         ▼
  👨‍🍳 CHEF (UsageRepository)
         │
         ├── (3a) Consulta al sistema por minutos de hoy (UsageDataSource)
         │
         └── (3b) Consulta historial en Room (TimeLensDatabase)
         │
         │ (4) El Chef devuelve modelos limpios de dominio
         ▼
  🤵 MESERO (ViewModel)
         │
         │ (5) Prepara el estado para la UI
         ▼
  📱 PANTALLA (HomeScreen)
         (6) ¡Compose dibuja los gráficos e iconos! ✨
```
