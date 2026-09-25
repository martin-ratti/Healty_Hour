# 🚀 Guía de Configuración Inicial (Setup Guide)

¡Hola y bienvenido(a) a tu primer proyecto en Android Studio! 🎉 

Sabemos que abrir una herramienta profesional por primera vez puede ser intimidante, ¡pero no te preocupes! Esta guía está diseñada para llevarte de la mano paso a paso. Piensa en esto como preparar tu cocina antes de empezar a cocinar tu primera receta. 👨‍🍳👩‍🍳

---

## 📚 Conceptos Básicos (¿Qué es todo esto?)

Antes de instalar cosas, entendamos qué son:

*   **🛠️ Android Studio (IDE):** Es como tu taller o tu cocina. Es el programa (Entorno de Desarrollo Integrado) donde vas a escribir tu código, diseñar tus pantallas y probar tu aplicación.
*   **👨‍🍳 Gradle:** Es como el chef o asistente de cocina. Tú le das las instrucciones (los archivos `build.gradle.kts`) y él se encarga de buscar los ingredientes (librerías de internet) y mezclar todo para "cocinar" tu aplicación final (el archivo APK que se instala en el celular).
*   **🧰 Android SDK (Software Development Kit):** Es la caja de herramientas oficial que Google nos da. Tiene todo lo necesario para que tu código entienda cómo hablar con el teléfono (cómo usar la cámara, la pantalla, etc.).
*   **📱 Emulador vs. Dispositivo Real:** Para probar tu app, puedes usar un "Emulador" (un celular virtual que vive dentro de tu computadora) o tu celular físico conectado por cable. El emulador es muy cómodo, pero usa mucha memoria de tu PC.
*   **⚙️ JDK (Java Development Kit):** Es el motor que permite que tu computadora entienda y ejecute el código Kotlin o Java que vas a escribir.

---

## 📝 Requisitos Previos

*   Una computadora con Windows, Mac o Linux (mínimo 8GB de RAM, se recomiendan 16GB).
*   Unos 10-15 GB de espacio libre en tu disco duro.
*   ¡Paciencia y ganas de aprender! 💡

---

## 🛠️ Paso 1: Instalar las Herramientas

1.  **Descarga Android Studio:** Ve a [developer.android.com/studio](https://developer.android.com/studio) y haz clic en el botón verde gigante de descarga.
2.  **Instala el programa:** Abre el archivo que descargaste y sigue las instrucciones (puedes darle "Siguiente" o "Next" a todo, las opciones por defecto están bien).
3.  **Abre Android Studio por primera vez:** Al abrirlo, te pedirá descargar el **Android SDK**. Deja que lo haga. Esto puede tardar varios minutos dependiendo de tu internet (¡son varios gigabytes!).

---

## 📂 Paso 2: Abrir este Proyecto (TimeLens)

1.  Abre Android Studio.
2.  Haz clic en **"Open"** (Abrir).
3.  Busca la carpeta donde guardaste este proyecto y selecciónala.
4.  Haz clic en "OK".

> **⚠️ IMPORTANTE: ¡El Primer Sync!**
> En cuanto abras el proyecto, notarás que en la parte de abajo de la pantalla aparece una barra cargando. ¡Esto es **Gradle** trabajando!
> La primera vez que abres el proyecto, Gradle tiene que descargar de internet todas las librerías necesarias. **Esto puede tardar de 5 a 10 minutos y descargará cerca de 1GB de datos.**
> 🛑 **Regla de oro:** NO toques nada, no intentes editar código, ni intentes darle play hasta que la barra de abajo termine de cargar y desaparezca. Ve por un café ☕.

---

## 📱 Paso 3: Configurar cómo probar tu App

Para ver tu aplicación funcionando, necesitas un lugar donde ejecutarla.

**Opción A: Usar un Emulador (Celular Virtual)**
1.  Arriba a la derecha en Android Studio, busca un ícono que parece un celular con un logo de Android pequeño (Device Manager).
2.  Haz clic en **"Create Device"** (Crear dispositivo).
3.  Elige un modelo (ej. Pixel 7) y dale Next.
4.  Elige una versión de Android (ej. API 34 o "Upside Down Cake"). Tendrás que darle al botón de descarga (Download) junto al nombre si es la primera vez.
5.  Cuando termine, dale a Next y Finish.
6.  Ahora, arriba en el centro, verás el nombre de tu emulador. ¡Haz clic en el botón de **Play verde** (▶️) para encenderlo!

**Opción B: Usar tu Celular Real (¡Más rápido!)**
1.  En tu celular Android, ve a Configuración > Acerca del teléfono.
2.  Busca "Número de compilación" y tócalo 7 veces rápido. Te dirá "¡Ya eres desarrollador!".
3.  Vuelve atrás, busca "Opciones de desarrollador" y activa la "Depuración por USB".
4.  Conecta tu celular a la PC con un cable USB.
5.  En tu celular aparecerá un mensaje preguntando si confías en la computadora, dile que "Sí" (Permitir).
6.  En Android Studio, arriba en el centro, debería aparecer el nombre de tu celular. Selecciónalo y dale al botón de **Play verde** (▶️).

---

## 🚨 Sección de Solución de Problemas (Troubleshooting)

¿Algo salió mal? ¡No te asustes! A todos nos pasa.

*   **Problema:** "Veo líneas rojas debajo de mis palabras en el código (Red Squiggles)"
    *   **Qué significa:** El IDE encontró un error o no reconoce algo.
    *   **Solución:** A veces es solo que Gradle no ha terminado. Si ya terminó, pon el mouse (sin hacer clic) encima de la palabra en rojo. Te dará una pista del error. A veces solo falta importar algo (Android Studio te sugerirá presionar `Alt + Enter` para arreglarlo automáticamente).
*   **Problema:** "Me da un error de Java o JDK al sincronizar"
    *   **Solución:** Ve a `File -> Settings -> Build, Execution, Deployment -> Build Tools -> Gradle`. Asegúrate de que en "Gradle JDK" esté seleccionado "jbr-17" (JetBrains Runtime 17) o la versión 17 de Java.
*   **Problema:** "El botón de Play está gris y no me deja hacer clic"
    *   **Solución:** Significa que Gradle está sincronizando (mira la barra de abajo) o que hay un error muy grave de configuración. Intenta ir a `File -> Sync Project with Gradle Files` (un ícono de elefante con una flecha azul arriba a la derecha).
*   **Problema:** "El emulador va súper lento"
    *   **Solución:** Los emuladores consumen muchos recursos. Si tu PC se traba, lo mejor es usar tu celular físico con un cable USB (Opción B).

¡Listo! Ya tienes todo preparado. Tu siguiente parada debería ser leer la guía `BEGINNER_GUIDE.md` para entender qué es este código mágico que vas a modificar. ✨
