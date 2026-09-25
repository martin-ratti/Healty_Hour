# 🐣 Guía Completa para Principiantes: Tu Primera App en Android

¡Bienvenido(a)! Si nunca has tocado código para Android, este es tu lugar. Esta guía te explicará cómo funciona todo con palabras sencillas y analogías. ¡Vamos a descubrir la magia detrás de las apps! ✨

---

## Parte 1: Entendiendo el Proyecto 📱

*   **¿Qué es una app de Android?** Es un programa diseñado para funcionar en teléfonos y tablets. Al final, todo tu código se empaqueta en un archivo llamado **APK** o **AAB**, que es el instalador que descargas desde la Play Store.
*   **¿Qué es Kotlin?** Es el lenguaje de programación. Imagina que es como aprender español, pero para hablarle a la computadora. Fue creado por JetBrains y es el idioma oficial y recomendado por Google para crear apps en Android. ¡Es muy moderno y amigable!
*   **¿Qué es Jetpack Compose?** Antiguamente, las pantallas de Android se diseñaban arrastrando botones en un lienzo o escribiendo largos archivos XML. Compose es la forma moderna: solo *describes* lo que quieres usando código Kotlin. Le dices "Quiero un texto azul debajo de un botón rojo", y Compose se encarga de dibujarlo.
*   **¿Qué es un Activity?** Piensa en un Activity como una "página" o la "ventana" de tu aplicación. Nuestra app tiene un `MainActivity` (la actividad principal), que es el punto de entrada, como la portada del libro.
*   **¿Qué es un ViewModel?** Es el "cerebro" detrás de la pantalla. La pantalla solo sabe mostrar cosas, no sabe pensar. El ViewModel guarda los datos (como la lista de apps) y toma decisiones lógicas. Si la pantalla se gira y se redibuja, el ViewModel sobrevive y recuerda los datos para que no se pierdan.

---

## Parte 2: Cómo se Organiza el Código (Clean Architecture) 🏢

El proyecto está organizado usando algo llamado "Arquitectura Limpia" (Clean Architecture). Imagina que tu aplicación es **un restaurante**:

1.  **Capa `presentation` (El Comedor / Salón):**
    *   Es lo que ve el cliente (el usuario).
    *   Aquí están las pantallas (`Screens`), los colores, los botones, y los meseros (`ViewModels`) que interactúan con los clientes.
2.  **Capa `domain` (El Libro de Recetas / Reglas):**
    *   Son las reglas de negocio, la esencia de tu app.
    *   Aquí definimos qué es un "Uso de App" y cómo se calcula el progreso, independientemente de si los datos vienen de internet o del teléfono.
3.  **Capa `data` (La Cocina):**
    *   Es donde se consigue y se prepara la información (la comida).
    *   Aquí se habla con las bases de datos locales, o se le pide información al sistema Android.

**Términos extraños que verás por ahí:**
*   **`@Composable`**: Cualquier función de Kotlin que tenga esto arriba significa que es capaz de "dibujar" algo en la pantalla (UI).
*   **`@Inject`**: Significa que una herramienta llamada **Hilt** nos va a entregar eso mágicamente. Si tu ViewModel necesita un repositorio, en lugar de crearlo a mano, le pones `@Inject` y Hilt te lo da.
*   **`@Entity`**: Le dice a la base de datos (Room) "Oye, esta clase de Kotlin quiero que la conviertas en una tabla tipo Excel".
*   **DAO (Data Access Object)**: Es como el mesero que lleva los pedidos de la cocina (base de datos) al chef. Tiene funciones como `getTodo()` o `insert()`.

---

## Parte 3: Cómo Funciona la App (El Flujo) 🔄

1.  **El usuario abre la app:** El sistema Android busca tu `MainActivity` y la inicia.
2.  **El Tema (Theme):** Se aplica el `HealthyHourTheme`, que define los colores (claro/oscuro).
3.  **La Pantalla:** Se llama al composable `HomeScreen`, que dibuja la interfaz.
4.  **El Cerebro:** `HomeScreen` le pide los datos al `HomeViewModel`.
5.  **Los Datos:** El ViewModel usa un Repositorio (la cocina) para conseguir estadísticas de uso del celular a través del sistema de Android (UsageStatsManager).
6.  **El Resultado:** Los datos fluyen de regreso hasta la pantalla y se dibujan los gráficos.

**Cosas importantes de esta app:**
*   **Permiso PACKAGE_USAGE_STATS:** Para ver cuánto tiempo usa el usuario otras apps (como TikTok), Android requiere un permiso *muy especial*. No basta con preguntar, hay que enviar al usuario a las configuraciones del sistema para que lo active manualmente.
*   **Room:** Es una librería que usamos para guardar datos en el celular, ¡como hojas de cálculo (tablas) incrustadas en tu app!
*   **WorkManager:** Imagina que necesitas que la app revise el uso del celular cada hora, *incluso si la app está cerrada*. WorkManager es como poner una alarma para que el teléfono haga una tarea en segundo plano.

---

## Parte 4: Kotlin Básico para este Proyecto 📝

Aquí tienes los "superpoderes" de Kotlin que necesitas conocer:

*   **`val` vs `var`:**
    *   `val` (Value) = Marcador permanente. Una vez que le asignas un valor, no puede cambiar. ¡Úsalo siempre que puedas!
    *   `var` (Variable) = Lápiz. Puedes borrar y asignarle un valor diferente más tarde.
*   **`data class`:** Es una clase hecha específicamente para guardar datos. Es como un formulario o plantilla pre-hecha. (Ej: `data class Usuario(val nombre: String, val edad: Int)`)
*   **`fun` y `suspend fun`:**
    *   `fun` = Una función (una tarea que el código hace instantáneamente).
    *   `suspend fun` = Una función que "toma tiempo" (como ir a la base de datos o descargar de internet). La palabra `suspend` pausa la tarea sin congelar la pantalla.
*   **Seguridad contra Nulos (`?`):** En Java, los errores de "esto está vacío (nulo)" rompían la app. En Kotlin, si algo puede estar vacío, se le pone un `?`. Ej: `val nombre: String? = null`.
*   **`when`:** Es como un interruptor de múltiples opciones, mucho más elegante que usar mil `if / else`.
*   **Plantillas de texto (String templates):** Para unir texto y variables, usa el símbolo de dólar. Ejemplo: `val saludo = "Hola, mi nombre es $nombre"` (¡No más `"Hola, " + nombre`!).

---

## Parte 5: Compose Básico (Piezas de Lego) 🧱

Compose es como armar Legos. Solo necesitas conocer las piezas base:

*   **`Column` (Columna):** Apila cosas verticalmente (una arriba de otra).
*   **`Row` (Fila):** Apila cosas horizontalmente (una al lado de otra).
*   **`Box` (Caja):** Apila cosas una encima de la otra (como capas de una hamburguesa).
*   **`Modifier` (Modificador):** ¡Son los accesorios de tus Legos! Con `Modifier` le das tamaño, fondo, padding (espacio interno), clics, etc. Ej: `Modifier.fillMaxSize().padding(16.dp)`.
*   **`LazyColumn`:** Es como una `Column`, pero "perezosa" (Lazy). Si tienes 1000 elementos, solo dibuja los 10 que se ven en la pantalla, ahorrando muchísima memoria. Ideal para listas.
*   **`State` y `remember`:** Compose no sabe que una variable cambió. Si quieres que la pantalla se actualice al cambiar un número, usas "estado" y "remember" (para que no se le olvide al rotar la pantalla).

---

## Parte 6: Tus Primeros Cambios (¡A jugar!) 🎮

La mejor forma de aprender es rompiendo cosas (¡con cuidado!). Intenta hacer estos cambios en el proyecto y fíjate qué pasa:

1.  **Cambiar el nombre de la app:**
    *   Ve a `app/src/main/res/values/strings.xml`.
    *   Busca `app_name` y cambia "Healthy Hour" por "Mi App Genial". ¡Corre la app!
2.  **Cambiar colores:**
    *   Ve a `app/src/main/java/com/example/healthyhour/ui/theme/Color.kt`.
    *   Cambia los códigos HEX (como `0xFF00FF00`) para ver cómo cambian los botones y fondos.
3.  **Cambiar los datos de prueba (Mock Data):**
    *   Ve al archivo `HomeScreen.kt` (o donde estén los datos simulados en la capa de UI).
    *   Busca la lista de aplicaciones (como "TikTok", "Instagram").
    *   ¡Cambia los emojis, pon el nombre de tus apps favoritas o cambia los minutos!
4.  **Añade una sexta app:**
    *   En esa misma lista de prueba en el código, copia un bloque entero de una app, pégalo debajo, y cambia sus datos para tener 6 elementos.
5.  **Juega con los Textos:**
    *   Cambia cualquier texto que diga "VS ayer" por "Comparado con el día de ayer".

¡No tengas miedo a equivocarte! Si el texto se pone rojo, solo usa el botón de deshacer (`Ctrl+Z`). ¡Tú puedes! 🚀
