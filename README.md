# 🕐 Healty Hour — Screen Time App para Android

> **Tu aliado para entender y controlar el tiempo que pasás en tu teléfono.**

Healty Hour es una app Android nativa que lee los datos de uso de pantalla del sistema y los presenta en una interfaz visualmente atractiva, con métricas como tiempo por app, sesión continua más larga, horarios pico, cantidad de aperturas, y más.

---

## ✨ Funcionalidades Principales

- 📊 **Dashboard en tiempo real** — Visualizá cuánto tiempo usás tu teléfono hoy
- 📱 **Top apps** — Descubrí cuáles apps consumen más tu tiempo
- ⏱️ **Sesiones detalladas** — Sabé cuánto duró cada sesión en cada app
- 🔥 **Racha tóxica** — La sesión continua más larga sin soltar el teléfono
- 📈 **Tendencias** — Gráficos semanales y mensuales de tu uso
- 🔔 **Notificaciones inteligentes** — Alertas cuando te excedés
- 🌙 **Horarios pico** — Identificá en qué momento del día usás más el teléfono

---

## 🛠️ Stack Tecnológico

| Componente | Tecnología |
|:---|:---|
| Lenguaje | **Kotlin** |
| UI | **Jetpack Compose** |
| Base de datos | **Room** |
| Gráficos | **Vico** |
| Inyección de dependencias | **Hilt** |
| Arquitectura | **MVVM + Clean Architecture** |
| Navegación | **Navigation Compose** |
| Min SDK | **26** (Android 8.0) |

---

## 📂 Estructura del Proyecto

```
com.healthyhour.app/
├── data/
│   ├── local/
│   │   ├── db/              # Room Database, DAOs, Entities
│   │   └── usage/           # Wrapper de UsageStatsManager
│   └── repository/          # Implementación de repositorios
├── domain/
│   ├── model/               # Modelos de dominio
│   ├── repository/          # Interfaces de repositorios
│   └── usecase/             # Casos de uso
├── presentation/
│   ├── theme/               # Colores, tipografía, shapes
│   ├── components/          # Componentes reutilizables
│   ├── screens/             # Pantallas (Home, Detail, History, Settings)
│   └── navigation/          # NavHost y rutas
├── di/                      # Módulos de Hilt
├── util/                    # Helpers y extensiones
└── HealthyHourApp.kt        # Application class
```

---

## 📚 Documentación

| Documento | Descripción |
|:---|:---|
| [SETUP.md](./SETUP.md) | **Guía de preparación** — Todo lo que necesitás instalar y configurar antes de empezar |
| [ARCHITECTURE.md](./ARCHITECTURE.md) | Arquitectura del proyecto y decisiones técnicas |
| [ROADMAP.md](./ROADMAP.md) | Fases del proyecto y cronograma estimado |
| [CONTRIBUTING.md](./CONTRIBUTING.md) | Guía de contribución y convenciones del proyecto |
| [CHANGELOG.md](./CHANGELOG.md) | Historial de cambios del proyecto |

---

## 🚀 Cómo Empezar

1. Leé [SETUP.md](./SETUP.md) para preparar tu entorno
2. Cloná este repositorio
3. Abrí el proyecto en Android Studio
4. Sincronizá Gradle
5. Conectá un dispositivo real (recomendado) o usá el emulador
6. ¡Ejecutá la app!

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Ver [LICENSE](./LICENSE) para más detalles.

---

*Creado con ❤️ por Martin Ratti*
