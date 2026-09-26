# 📝 Changelog — TimeLens

Todos los cambios notables del proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/).

## [0.3.0] — 2026-09-26
 
### Agregado
- 🔍 **Pantalla de Detalle de Aplicación (`AppDetailScreen`)**:
  - Encabezado interactivo con ícono oficial, nombre limpio de app y badge con categoría de uso.
  - 4 tarjetas KPI: Tiempo acumulado hoy, cantidad de aperturas, duración de la sesión más larga y duración promedio por sesión.
  - Gráfico interactivo por hora (`HourlyBarChart`) que desglosa el uso en las 24 horas del día actual (00:00 a 23:00).
  - Gráfico de tendencia histórica (`WeeklyBarChart`) mostrando el uso de los últimos 7 días específicos para esa app.
- 🌓 **Modo Claro y Oscuro 100% Dinámico**:
  - Definición completa de `LightColorScheme` en Material Design 3 con fondo slate y tarjetas blancas de alto contraste.
  - Conexión reactiva en `MainActivity` mediante `UserPreferencesManager` (DataStore) para aplicar el cambio inmediatamente al tocar el switch en Ajustes sin requerir reinicio de la app.
  - Migración de todas las pantallas y tarjetas para consumir `MaterialTheme.colorScheme` de manera adaptativa.
- 🤝 **Compartir TimeLens (Share Intent)**:
  - Reemplazo de la función de exportación de CSV por un diálogo amigable de compartir usando el selector nativo de Android (`Intent.ACTION_SEND` con `Intent.createChooser`).
  - Mensaje cálido invitando a amigos a probar TimeLens junto con el enlace al repositorio oficial.
- ℹ️ **Diálogo "Acerca de" rediseñado**:
  - Visualización por tarjetas con la Misión de bienestar digital, garantía de Privacidad 100% Local (sin telemetría ni servidores externos), autoría (Martín Ratti) y botón directo para abrir el repositorio de GitHub en el navegador.
- 🏷️ **Categorización inteligente de aplicaciones**:
  - Detección y clasificación automática de apps (`Social`, `Entretenimiento`, `Productividad`, `Juegos`, `Educación`, `Salud y Bienestar`, `Herramientas`, `Otros`) con badges de colores personalizados.

### Corregido
- ⏱️ **Cálculo de Tiempo de Pantalla Sincronizado**:
  - Solución al error donde el tiempo reportado excedía las 24 horas diarias por sumas directas de paquetes en segundo plano o eventos superpuestos.
  - Implementación de un algoritmo basado en intervalos temporales combinados en `SessionCalculator`, igualando con exactitud los minutos reportados por Bienestar Digital (Digital Wellbeing) de Android.
- 🚫 **Filtrado de Aplicaciones del Sistema**:
  - Exclusión de Launchers del sistema (ej. Nova Launcher, Pixel Launcher, OneUI Home), System UI y la propia app TimeLens de las estadísticas para reflejar únicamente las apps utilizadas por el usuario.
- ⚡ **Optimización de Rendimiento y Carga de UI**:
  - Implementación de caché en memoria de nombres de apps e íconos en `UsageDataSource`, eliminando el lag y jitter en las listas de Compose.

---

## [0.2.0] — 2026-09-25

### Agregado
- 🧭 Navegación principal con `AppNavHost` y barra inferior (`NavigationBar` de Material 3).
- 📱 Pantalla de bienvenida y solicitud de permisos: `OnboardingScreen`.
- 📊 Pantalla de historial y tendencias semanales: `HistoryScreen`.
- ⚙️ Pantalla de configuración completa con toggles y objetivos: `SettingsScreen`.
- 🎨 Iconos outlined modernos de Material Icons Extended en sustitución de todos los emojis.

### Modificado
- 🏷️ Renombrado integral del proyecto a **TimeLens**:
  - Package migrado a `com.timelens.app`.
  - Base de datos Room renombrada a `TimeLensDatabase` (`timelens_db`).
  - Application class renombrada a `TimeLensApp`.
  - Tema renombrado a `TimeLensTheme`.
  - Gradle `namespace` y `applicationId` actualizados a `com.timelens.app`.
  - Repositorio remoto actualizado a `TimeLens.git`.

---

## [0.1.0] — 2026-09-25

### Agregado
- 🏗️ Estructura inicial con Clean Architecture (data, domain, presentation).
- 📱 Dashboard visual con datos demostrativos en Compose.
- 📄 Documentación inicial (`README.md`, `SETUP.md`, `ARCHITECTURE.md`, `ROADMAP.md`, `CONTRIBUTING.md`).

---

## Versionado

Este proyecto usa [Semantic Versioning](https://semver.org/):
- **MAJOR** — Cambios incompatibles (ej: rediseño completo)
- **MINOR** — Nueva funcionalidad retrocompatible
- **PATCH** — Corrección de bugs

---

*Documento actualizado: 25 de septiembre de 2026*
