# 🏛️ Arquitectura del Proyecto (Clean Architecture)

Este proyecto usa algo llamado **Clean Architecture** (Arquitectura Limpia) junto con el patrón **MVVM** (Model-View-ViewModel). 

¡Suena complejo, pero vamos a explicarlo con una analogía muy simple! 🍔

## La Analogía del Restaurante 🍽️

Imagina que nuestra aplicación "Healthy Hour" es un restaurante muy elegante:

1.  **Capa `presentation` (El Comedor y los Meseros):**
    *   Es lo que el cliente ve y con lo que interactúa.
    *   **UI / Composables:** Son las mesas, las luces, la decoración (la pantalla de tu celular).
    *   **ViewModel:** Es el **mesero**. El mesero no cocina, solo toma tu pedido, va a la cocina, espera tu plato y te lo entrega en la mesa. El ViewModel recibe la orden (clicks) y trae los datos para mostrarlos en pantalla.
2.  **Capa `domain` (El Libro de Recetas y las Reglas de Etiqueta):**
    *   Es el corazón del negocio. Aquí no nos importa si cocinamos en estufa de gas o eléctrica (no nos importan las librerías de Android), solo nos importa *qué* se hace.
    *   **Models:** Son la definición de los ingredientes. (¿Qué es un "uso de aplicación"? Es una foto, un nombre y unos minutos).
    *   **Use Cases:** Son las reglas. Por ejemplo, "calcular el exceso de uso comparado con ayer".
3.  **Capa `data` (La Cocina y los Proveedores):**
    *   Es donde ocurre el trabajo duro de obtener los ingredientes.
    *   **Repository:** Es el **Chef supervisor**. El ViewModel le pide comida al Chef, y el Chef decide si saca los ingredientes de la nevera local (Base de datos Room) o si llama al proveedor externo (Internet / API del Sistema Android).
    *   **Data Sources / DAO:** Son los ayudantes de cocina que van físicamente a buscar los ingredientes a la base de datos.

---

## 🗺️ Mapa de Archivos (¿Dónde está cada cosa?)

Aquí tienes un mapa para que no te pierdas en el proyecto:

```text
C:\Users\Marto\Desktop\Healty_Hour\app\src\main\java\com\example\healthyhour\
│
├── 📂 data/                 # 👨‍🍳 LA COCINA (Datos)
│   ├── 📂 local/            # La despensa (Base de datos Room)
│   │   ├── AppDao.kt        # El ayudante que guarda y lee de la BD.
│   │   └── AppDatabase.kt   # La configuración de la base de datos local.
│   ├── 📂 repository/       # El Chef que orquesta los datos.
│   │   └── AppUsageRepositoryImpl.kt # Implementación real de cómo conseguir datos.
│   └── 📂 usage_stats/      # El proveedor externo (El sistema Android).
│       └── UsageStatsManager.kt # Habla con el sistema del celular para ver los tiempos de uso.
│
├── 📂 domain/               # 📖 EL LIBRO DE RECETAS (Reglas de negocio)
│   ├── 📂 model/            # Conceptos de nuestro negocio.
│   │   └── AppUsage.kt      # Define qué tiene una App (nombre, tiempo, ícono).
│   └── 📂 repository/       # La interfaz (el contrato) para el Chef.
│       └── AppUsageRepository.kt # Define qué DEBE hacer el Chef, pero no cómo.
│
├── 📂 presentation/         # 🍽️ EL COMEDOR (Lo que ve el usuario)
│   ├── 📂 home/             # La pantalla principal.
│   │   ├── HomeScreen.kt    # El diseño de la pantalla principal (Jetpack Compose).
│   │   └── HomeViewModel.kt # El mesero que controla la HomeScreen.
│   ├── 📂 components/       # Elementos visuales reutilizables (Botones, gráficas).
│   └── 📂 theme/            # Colores, fuentes y estilos de la app.
│
├── 📂 di/                   # 🪄 LA MAGIA (Inyección de dependencias)
│   └── AppModule.kt         # (Hilt) Aquí le decimos a la app cómo conectar las capas automáticamente.
│
└── MainActivity.kt          # 🚪 LA PUERTA DE ENTRADA de la aplicación.
```

---

## 🔄 El Flujo de la Información (Diagrama)

¿Cómo viaja la información cuando abres la app? Es una vía de un solo sentido para mantener el orden (Unidirectional Data Flow):

```text
  📱 PANTALLA (HomeScreen)
         │
         │ (1) El usuario abre la app / Pide datos
         ▼
  🤵 MESERO (HomeViewModel)
         │
         │ (2) Pide los datos de uso de hoy
         ▼
  👨‍🍳 CHEF (AppUsageRepository)
         │
         ├── (3a) Va a preguntar al sistema Android 
         │        por los minutos usados hoy (UsageStatsManager)
         │
         └── (3b) Va a la base de datos local 
                  a ver cuánto se usó ayer (AppDao / Room)
         │
         │ (4) El Chef junta todo y lo devuelve
         ▼
  🤵 MESERO (HomeViewModel)
         │
         │ (5) El mesero prepara los datos bonitos (estado)
         ▼
  📱 PANTALLA (HomeScreen)
         (6) ¡La pantalla se actualiza y dibuja los tiempos! 🎉
```

¡Con esta estructura, si algún día queremos cambiar cómo guardamos los datos en la base de datos, no tenemos que tocar la pantalla (UI) en absoluto! Cada uno hace su trabajo por separado.
