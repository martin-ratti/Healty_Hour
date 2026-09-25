# 📝 Changelog — TimeLens

Todos los cambios notables del proyecto se documentan en este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es-ES/1.1.0/).

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
