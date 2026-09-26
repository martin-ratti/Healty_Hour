# TimeLens - Android App

**TimeLens** es una aplicación nativa de Android moderna, construida con Kotlin y Jetpack Compose, que ayuda a los usuarios a ser más conscientes del tiempo que pasan en sus teléfonos, promoviendo hábitos digitales saludables.

## Características

- 📊 **Dashboard diario en tiempo real:** Resumen de tiempo de pantalla sincronizado con el sistema de Android.
- 📱 **Top apps más usadas:** Barras de progreso animadas, iconos reales del sistema, conteo de aperturas y badges de categoría.
- 🔍 **Detalle por aplicación (`AppDetailScreen`):**
  - Métricas clave: Tiempo hoy, total de aperturas, sesión más larga y promedio por sesión.
  - Gráfico de barras de actividad por hora (00:00 a 23:00).
  - Historial y tendencias de los últimos 7 días.
- 📈 **Tendencias e historial semanal:** Gráficos nativos en Canvas con detección automática de mejor y peor día.
- 🎯 **Objetivos diarios:** Configura tu meta diaria de uso y visualiza el progreso circular con gradientes neón.
- 🌓 **Soporte Dinámico de Temas:**
  - **Modo Oscuro Neón:** Estilo moderno de alto contraste con acentos neón azul, púrpura y verde.
  - **Modo Claro:** Interfaz limpia, luminosa y elegante adaptada a Material Design 3.
- 🤝 **Compartir TimeLens:** Selector nativo de Android para compartir la app y promover bienestar digital entre amigos.
- 🛡️ **100% Local & Privado:** Todos los datos se procesan y almacenan exclusivamente en tu teléfono. Sin servidores externos, sin telemetría y sin publicidad.

---

## Primeros Pasos

Si es tu primer proyecto de Android:

1. Lee primero [SETUP.md](SETUP.md): instalación de Android Studio y configuración del proyecto.
2. Luego lee [BEGINNER_GUIDE.md](BEGINNER_GUIDE.md): introducción a Kotlin, Jetpack Compose y la arquitectura.
3. Revisa [ARCHITECTURE.md](ARCHITECTURE.md): explicación detallada de Clean Architecture y mapa del proyecto.
4. Consulta el [ROADMAP.md](ROADMAP.md) para ver el estado de cada fase y el [CHANGELOG.md](CHANGELOG.md) para el historial de versiones.

---

## Documentación

| Documento | Descripción | Audiencia |
| :--- | :--- | :--- |
| **[SETUP.md](SETUP.md)** | Guía de instalación y ejecución paso a paso | Principiantes |
| **[BEGINNER_GUIDE.md](BEGINNER_GUIDE.md)** | Curso de Kotlin, Compose y flujo de la app | Principiantes |
| **[ARCHITECTURE.md](ARCHITECTURE.md)** | Clean Architecture, MVVM y mapa de archivos | Todos |
| **[ROADMAP.md](ROADMAP.md)** | Plan de desarrollo por fases y cronograma | Todos |
| **[CHANGELOG.md](CHANGELOG.md)** | Historial detallado de versiones y cambios | Todos |
| **[CONTRIBUTING.md](CONTRIBUTING.md)** | Guía de contribución, ramas y commits | Desarrolladores |

---

## Tech Stack

- **Lenguaje:** [Kotlin](https://kotlinlang.org/) (100%)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) + Material Design 3
- **Iconos:** Material Icons Extended
- **Carga de imágenes / Iconos de apps:** Coil Compose
- **Arquitectura:** Clean Architecture + MVVM + Unidirectional Data Flow (UDF)
- **Inyección de Dependencias:** [Hilt](https://dagger.dev/hilt/)
- **Persistencia Local:** [Room](https://developer.android.com/training/data-storage/room) & DataStore Preferences
- **Tareas en Segundo Plano:** [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager)
- **Navegación:** Navigation Compose con rutas seguras y paso de argumentos
- **Gráficos:** Componentes nativos optimizados con Compose Canvas
- **Concurrencia:** Kotlin Coroutines & StateFlow / Flow reactivos
