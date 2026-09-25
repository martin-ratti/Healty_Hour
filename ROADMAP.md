# 🗺️ Roadmap — TimeLens

> Plan de desarrollo por fases, con tareas específicas y cronograma estimado.

---

## 📊 Progreso General

| Fase | Estado | Progreso |
|:---|:---|:---|
| Fase 0 — Preparación | 🟡 En progreso | ▓░░░░░░░░░ 10% |
| Fase 1 — Permisos y Datos | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 2 — Motor de Métricas | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 3 — Base de Datos Room | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 4 — UI con Compose | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 5 — Notificaciones | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 6 — Testing y Pulido | ⬜ Pendiente | ░░░░░░░░░░ 0% |
| Fase 7 — Publicación | ⬜ Pendiente | ░░░░░░░░░░ 0% |

---

## 📋 Fase 0 — Preparación y Setup (~1 día)

🔴 **Prioridad: Crítica**

- [x] Crear repositorio en GitHub
- [x] Crear documentación inicial (README, SETUP, ARCHITECTURE, etc.)
- [ ] Instalar Android Studio
- [ ] Crear proyecto con Empty Compose Activity
- [ ] Configurar `minSdk = 26`, `targetSdk = 35`
- [ ] Agregar dependencias iniciales (Compose, Room, Hilt, Navigation, Vico)
- [ ] Crear estructura de carpetas del proyecto
- [ ] Configurar Hilt en la Application class
- [ ] Primer commit con el proyecto base

---

## 📋 Fase 1 — Permisos y Lectura de Datos (~2-3 días)

🔴 **Prioridad: Crítica**

### 1.1 Permisos
- [ ] Agregar `PACKAGE_USAGE_STATS` al AndroidManifest.xml
- [ ] Crear pantalla de onboarding que explique el permiso
- [ ] Implementar redirección a Ajustes del sistema
- [ ] Verificar si el permiso fue concedido al volver a la app
- [ ] Manejar caso sin permiso (pantalla amigable)

### 1.2 Lectura de datos
- [ ] Crear `UsageDataSource` — wrapper de `UsageStatsManager`
- [ ] Implementar `queryUsageStats()` para estadísticas diarias
- [ ] Implementar `queryEvents()` para eventos granulares
- [ ] Resolver nombres de apps con `PackageManager.getApplicationLabel()`
- [ ] Resolver íconos de apps con `PackageManager.getApplicationIcon()`
- [ ] Cachear nombres e íconos para no repetir consultas

### 🎯 Entregable
> La app muestra en un `Text()` la lista de apps usadas hoy con su tiempo.

---

## 📋 Fase 2 — Motor de Métricas (~3-4 días)

🔴 **Prioridad: Crítica**

### Nivel Básico
- [ ] Tiempo total de pantalla del día
- [ ] Top 5 apps más usadas
- [ ] Cantidad de desbloqueos

### Nivel Intermedio
- [ ] Sesión continua más larga por app
- [ ] Cantidad de aperturas por app
- [ ] Tiempo promedio por sesión
- [ ] Horario pico (franja horaria con más actividad)

### Nivel Avanzado
- [ ] "Racha tóxica" — sesión continua más larga sin importar la app
- [ ] Comparativa día a día ("Hoy +23% vs ayer")
- [ ] Tendencia semanal (últimos 7 días)
- [ ] "Momento más productivo" — franja con menos uso
- [ ] Categorización automática de apps

### Testing del motor
- [ ] Crear `SessionCalculator` con tests unitarios
- [ ] Crear `MetricsEngine` con tests
- [ ] Tests para edge cases (sesiones superpuestas, falta de PAUSED, medianoche)

### 🎯 Entregable
> Todas las métricas calculándose correctamente con tests que lo demuestren.

---

## 📋 Fase 3 — Base de Datos Room (~2 días)

🟡 **Prioridad: Alta**

- [ ] Crear entidades Room (`DailyUsage`, `AppDailyUsage`)
- [ ] Crear DAOs con queries útiles
- [ ] Crear `TimeLensDatabase`
- [ ] Implementar `Worker` (WorkManager) para guardar snapshots cada hora
- [ ] Tarea periódica de resumen al final del día
- [ ] Implementar migración de esquema para futuras versiones

### 🎯 Entregable
> Datos históricos persistidos, consultables por rango de fechas.

---

## 📋 Fase 4 — UI con Jetpack Compose (~5-7 días)

🟡 **Prioridad: Alta**

### Pantallas
- [ ] **Home / Dashboard**
  - Círculo animado con tiempo total
  - Barra comparativa con ayer
  - Top 3 apps con íconos
  - Tarjeta sesión más larga
  - Tarjeta desbloqueos
- [ ] **Detalle por App**
  - Gráfico de barras por hora
  - Historial últimos 7 días
  - Métricas detalladas
- [ ] **Historial / Trends**
  - Gráfico de línea 7/30 días
  - Mejor/peor día
  - Promedio semanal
- [ ] **Settings**
  - Objetivo diario
  - Notificaciones on/off
  - Tema oscuro/claro
  - Exportar datos (CSV)

### Diseño visual
- [ ] Definir paleta de colores (oscura con acentos neón)
- [ ] Configurar Material 3
- [ ] Implementar tema oscuro desde el inicio
- [ ] Animaciones con `animateFloatAsState` y `AnimatedVisibility`
- [ ] Integrar gráficos con Vico

### Navegación
- [ ] Configurar Navigation Compose
- [ ] Bottom navigation entre pantallas principales

### 🎯 Entregable
> Todas las pantallas funcionales con datos reales y gráficos.

---

## 📋 Fase 5 — Notificaciones y Extras (~3-4 días)

🟢 **Prioridad: Media**

- [ ] Alerta de sesión larga en tiempo real ("Llevás 2h en Instagram")
- [ ] Resumen diario a las 22:00
- [ ] Notificación de récord ("Nueva sesión más larga")
- [ ] Alerta al superar objetivo diario
- [ ] ForegroundService liviano para monitoreo
- [ ] Canal de notificación dedicado

### 🎯 Entregable
> Notificaciones inteligentes funcionando sin drenar batería.

---

## 📋 Fase 6 — Testing y Pulido (~3-4 días)

🟡 **Prioridad: Alta**

- [ ] Tests unitarios completos (SessionCalculator, MetricsEngine, UseCases)
- [ ] Tests de integración para DAOs
- [ ] Tests de UI básicos con Compose Testing
- [ ] Probar en dispositivos reales (mínimo 2 dispositivos diferentes)
- [ ] Probar en Android 8.0 (API 26) hasta Android 14 (API 35)
- [ ] Revisar rendimiento con apps de muchos eventos
- [ ] Manejar edge cases: sin datos, primer día, permisos revocados
- [ ] Pulir animaciones y transiciones
- [ ] Revisar accesibilidad básica

### 🎯 Entregable
> App estable, probada y pulida, lista para publicar.

---

## 📋 Fase 7 — Publicación (~1-2 días)

🟢 **Prioridad: Media**

- [ ] Crear cuenta de Google Play Developer ($25 USD)
- [ ] Generar signing key (keystore) — ¡GUARDARLA EN LUGAR SEGURO!
- [ ] Preparar ícono de la app (512x512 PNG)
- [ ] Preparar feature graphic (1024x500)
- [ ] Tomar mínimo 4 screenshots
- [ ] Escribir descripción optimizada (ASO)
- [ ] Crear política de privacidad (GitHub Pages)
- [ ] Completar formulario Data Safety
- [ ] Subir AAB a Google Play Console
- [ ] Publicar en testing interno
- [ ] Recopilar feedback → iterar
- [ ] Publicar en producción

### 🎯 Entregable
> App publicada en Google Play Store.

---

## 🗓️ Cronograma Estimado

| Fase | Duración | Acumulado |
|:---|:---|:---|
| Fase 0 | 1 día | Día 1 |
| Fase 1 | 2-3 días | Día 3-4 |
| Fase 2 | 3-4 días | Día 7-8 |
| Fase 3 | 2 días | Día 9-10 |
| Fase 4 | 5-7 días | Día 15-17 |
| Fase 5 | 3-4 días | Día 19-21 |
| Fase 6 | 3-4 días | Día 23-25 |
| Fase 7 | 1-2 días | Día 25-27 |

> 📌 **Total: ~3-4 semanas** (con dedicación de 3-4 horas/día). Con full-time se reduce a ~2 semanas.

---

*Documento actualizado: 25 de septiembre de 2026*
