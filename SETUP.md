# 🛠️ Guía de Preparación — Healty Hour

> **Todo lo que necesitás instalar, configurar y tener listo antes de escribir la primera línea de código.**

---

## 📋 Checklist Rápido

- [ ] Instalar Android Studio
- [ ] Instalar JDK 17
- [ ] Configurar un dispositivo Android real o emulador
- [ ] Instalar Git
- [ ] Clonar este repositorio
- [ ] Crear el proyecto en Android Studio
- [ ] Familiarizarte con Kotlin y Jetpack Compose (si es necesario)

---

## 1. 💻 Android Studio

### Instalación

1. Descargá **Android Studio** desde: https://developer.android.com/studio
2. Instalá la versión más reciente estable (Ladybug o superior)
3. Durante la instalación, asegurate de incluir:
   - ✅ Android SDK
   - ✅ Android SDK Platform (API 35 recomendado como target)
   - ✅ Android Virtual Device (AVD)
   - ✅ Android SDK Build-Tools
   - ✅ Android SDK Command-line Tools

### Configuración Post-Instalación

1. Abrí Android Studio → **SDK Manager** (Settings → Languages & Frameworks → Android SDK)
2. En la pestaña **SDK Platforms**, asegurate de tener instalado:
   - Android 14.0 (API 35) — Target SDK
   - Android 8.0 (API 26) — Minimum SDK (para testing)
3. En la pestaña **SDK Tools**, verificá que estén instalados:
   - Android SDK Build-Tools
   - Android Emulator
   - Android SDK Platform-Tools
   - Google Play services (opcional pero recomendado)

> 💡 **Espacio en disco:** Android Studio + SDK necesitan ~10-15 GB libres.

---

## 2. ☕ JDK (Java Development Kit)

Android Studio trae un JDK integrado, pero para estar seguro:

- **Recomendado:** JDK 17 (LTS)
- Descarga: https://adoptium.net/ (Eclipse Temurin)
- Verificar instalación:
  ```bash
  java -version
  # Esperado: openjdk version "17.x.x"
  ```

> ⚠️ **Importante:** No uses JDK 21 todavía para Android — algunas herramientas de Gradle aún no lo soportan completamente.

---

## 3. 📱 Dispositivo de Pruebas

### Opción A: Dispositivo Real (⭐ RECOMENDADO)

> **Para esta app en particular, un dispositivo real es ESENCIAL** porque el emulador no genera datos reales de uso de pantalla.

1. Habilitá **Opciones de desarrollador** en tu teléfono:
   - Ajustes → Acerca del teléfono → Tocar "Número de compilación" 7 veces
2. Activá **Depuración USB**:
   - Ajustes → Opciones de desarrollador → Depuración USB → ON
3. Conectá el teléfono por USB al PC
4. Aceptá el aviso de depuración en el teléfono
5. En Android Studio, tu dispositivo debería aparecer en el selector de dispositivos

### Opción B: Emulador (para UI solamente)

1. Android Studio → **Device Manager** → Create Device
2. Elegí un dispositivo (ej: Pixel 7)
3. Seleccioná la imagen del sistema: API 35 con Google APIs
4. Configurá RAM: mínimo 2 GB
5. Iniciá el emulador

> ⚠️ **Limitación:** El emulador NO genera datos de `UsageStatsManager`. Solo sirve para probar la UI con datos mock.

---

## 4. 🔧 Git

### Instalación

- **Windows:** Descargá desde https://git-scm.com/download/win
- Verificar:
  ```bash
  git --version
  # Esperado: git version 2.x.x
  ```

### Configuración inicial

```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

### Clonar el repositorio

```bash
git clone https://github.com/martin-ratti/Healty_Hour.git
cd Healty_Hour
```

---

## 5. 📦 Dependencias del Proyecto (Gradle)

Cuando crees el proyecto en Android Studio, necesitarás agregar estas dependencias en `build.gradle.kts` (Module: app):

```kotlin
// --- Jetpack Compose ---
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.ui:ui")
implementation("androidx.compose.ui:ui-graphics")
implementation("androidx.compose.ui:ui-tooling-preview")
implementation("androidx.compose.material3:material3")
implementation("androidx.activity:activity-compose:1.9.2")

// --- Navigation Compose ---
implementation("androidx.navigation:navigation-compose:2.8.0")

// --- Room (Base de datos local) ---
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")

// --- Hilt (Inyección de dependencias) ---
implementation("com.google.dagger:hilt-android:2.51.1")
kapt("com.google.dagger:hilt-compiler:2.51.1")
implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

// --- Gráficos (Vico) ---
implementation("com.patrykandpatrick.vico:compose:2.0.0-alpha.22")
implementation("com.patrykandpatrick.vico:compose-m3:2.0.0-alpha.22")

// --- WorkManager (Tareas en background) ---
implementation("androidx.work:work-runtime-ktx:2.9.1")

// --- Coroutines ---
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

// --- Lifecycle ---
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.5")

// --- Testing ---
testImplementation("junit:junit:4.13.2")
androidTestImplementation("androidx.test.ext:junit:1.2.1")
androidTestImplementation("androidx.compose.ui:ui-test-junit4")
```

> 💡 **Nota:** Las versiones pueden variar. Al crear el proyecto, Android Studio te sugerirá las últimas estables.

---

## 6. 📖 Conocimientos Previos Recomendados

### Imprescindibles ✅

| Tema | Recurso | Tiempo estimado |
|:---|:---|:---|
| **Kotlin Básico** | [Kotlin Koans](https://kotlinlang.org/docs/koans.html) | 3-4 horas |
| **Jetpack Compose Básico** | [Compose Tutorial Oficial](https://developer.android.com/jetpack/compose/tutorial) | 2-3 horas |
| **Coroutines y Flow** | [Guía oficial](https://developer.android.com/kotlin/coroutines) | 2 horas |

### Recomendados 📚

| Tema | Recurso | Tiempo estimado |
|:---|:---|:---|
| **Room Database** | [Codelab Room](https://developer.android.com/codelabs/android-room-with-a-view-kotlin) | 2 horas |
| **Hilt (DI)** | [Codelab Hilt](https://developer.android.com/codelabs/android-hilt) | 1-2 horas |
| **MVVM en Android** | [Guide to app architecture](https://developer.android.com/topic/architecture) | 1 hora |
| **UsageStatsManager** | [Documentación API](https://developer.android.com/reference/android/app/usage/UsageStatsManager) | 30 min |

### Opcionales pero útiles 🎯

| Tema | Recurso |
|:---|:---|
| Material Design 3 | [m3.material.io](https://m3.material.io/) |
| Vico Charts | [GitHub Vico](https://github.com/patrykandpatrick/vico) |
| WorkManager | [Guía oficial](https://developer.android.com/topic/libraries/architecture/workmanager) |

---

## 7. 🖥️ Requisitos del Sistema

### Mínimos

| Componente | Requisito |
|:---|:---|
| OS | Windows 10 64-bit / macOS 10.14+ / Linux |
| RAM | 8 GB (16 GB recomendado) |
| Disco | 15 GB libres mínimo |
| CPU | x86_64 con soporte de virtualización (para emulador) |
| Resolución | 1280 x 800 mínimo |

### Verificar virtualización (para emulador)

En Windows, abrí el Administrador de Tareas → pestaña Rendimiento → CPU → verificá que "Virtualización" diga **Habilitado**.

Si no está habilitado, entrá a la BIOS y activá **Intel VT-x** o **AMD-V**.

---

## 8. 🚀 Primer Paso Concreto

Una vez que tengas todo instalado:

1. **Abrí Android Studio** → New Project → Empty Compose Activity
2. **Nombre del proyecto:** `HealthyHour`
3. **Package name:** `com.healthyhour.app`
4. **Minimum SDK:** API 26 (Android 8.0 Oreo)
5. **Build configuration language:** Kotlin DSL (Recommended)
6. Esperá a que Gradle sincronice (~5-10 min la primera vez)
7. Conectá tu teléfono, dale Run ▶️, y deberías ver "Hello Android!"
8. **Copiá todos los archivos generados al repositorio clonado** y hacé tu primer commit

```bash
git add .
git commit -m "feat: initial project setup with Empty Compose Activity"
git push origin main
```

> 🎯 **Meta del Día 1:** Que la app lea y muestre en un `Text()` de Compose la lista de apps usadas hoy con su tiempo. Ese es tu "Hello World" para este proyecto.

---

## ❓ Problemas Comunes

### "No puedo ver mi dispositivo en Android Studio"
- ¿Habilitaste Depuración USB?
- ¿Instalaste los drivers USB del fabricante?
- Probá con otro cable USB (los de solo carga no funcionan)

### "Gradle tarda eternamente"
- La primera sincronización descarga muchas dependencias. Sé paciente.
- Verificá tu conexión a internet.
- Si se queda colgado, invalidá caches: File → Invalidate Caches → Restart.

### "El emulador no arranca"
- ¿Está habilitada la virtualización en tu BIOS?
- ¿Tenés suficiente RAM libre?
- Probá con una imagen del sistema x86_64 en vez de ARM.

### "Error con el permiso PACKAGE_USAGE_STATS"
- Este permiso NO se pide con diálogo. Hay que redirigir al usuario a Ajustes del sistema.
- Verificá que estés probando en un dispositivo real para ver datos reales.

---

*Documento actualizado: 25 de septiembre de 2026*
