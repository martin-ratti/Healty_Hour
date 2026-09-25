# 🤝 Guía de Contribución — TimeLens

> Convenciones, reglas y flujo de trabajo para mantener el proyecto organizado.

---

## 📌 Convenciones de Git

### Branches

| Branch | Propósito |
|:---|:---|
| `main` | Código estable, listo para producción |
| `develop` | Integración de features antes de mergear a main |
| `feature/nombre` | Nueva funcionalidad (ej: `feature/session-calculator`) |
| `fix/nombre` | Corrección de bug (ej: `fix/midnight-session-split`) |
| `refactor/nombre` | Refactorización sin cambio funcional |

### Commits — Conventional Commits

Formato: `tipo(scope): descripción`

```
feat(metrics): add longest session calculation
fix(usage): handle missing PAUSED events
docs(readme): update setup instructions
refactor(data): extract UsageDataSource to separate class
test(session): add edge case tests for midnight sessions
style(theme): adjust primary color palette
chore(gradle): update Compose BOM version
```

**Tipos válidos:**
- `feat` — Nueva funcionalidad
- `fix` — Corrección de bug
- `docs` — Documentación
- `refactor` — Refactorización
- `test` — Tests
- `style` — Cambios de estilo/formato
- `chore` — Tareas de mantenimiento
- `perf` — Mejora de rendimiento

---

## 🏗️ Convenciones de Código

### Kotlin
- Seguir las [convenciones oficiales de Kotlin](https://kotlinlang.org/docs/coding-conventions.html)
- Nombres de clases: `PascalCase`
- Nombres de funciones y variables: `camelCase`
- Nombres de constantes: `UPPER_SNAKE_CASE`
- Usar `data class` para modelos de datos
- Preferir `val` sobre `var`
- Usar coroutines en vez de callbacks

### Jetpack Compose
- Nombres de Composables: `PascalCase` (como clases)
- Parámetro `modifier` siempre como primer parámetro opcional
- Extraer componentes cuando superan ~50 líneas
- Preview con `@Preview` para cada pantalla

### Arquitectura
- **No importar Android en la capa de dominio**
- **Un UseCase = una responsabilidad**
- ViewModels no deben contener lógica de negocio directa
- Los repositorios devuelven modelos de dominio, nunca entidades de Room

---

## 📁 Estructura de Archivos

- Un archivo por clase/interfaz principal
- Nombre del archivo = nombre de la clase
- Los tests van en el directorio mirror (`test/` para unit, `androidTest/` para instrumentación)

---

## ✅ Checklist antes de un PR

- [ ] El código compila sin errores
- [ ] Los tests existentes pasan
- [ ] Se agregaron tests para código nuevo
- [ ] Se siguieron las convenciones de commits
- [ ] Se actualizó documentación si es necesario
- [ ] Se probó en un dispositivo real (si toca funcionalidad de usage stats)

---

*Documento actualizado: 25 de septiembre de 2026*
