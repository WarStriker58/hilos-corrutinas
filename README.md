# ¿Qué tarea realizar el proyecto?
Esperar 5 segundos y luego cambiar el texto.

### 1. Ejemplo SIN hilos (Bloqueo de UI)
Por qué es correcto: Ejecuta una tarea pesada (Thread.sleep(5000)) directamente sobre el hilo principal. Demuestra didácticamente el peor escenario en Android: la interfaz se congela, el usuario no puede interactuar y el sistema operativo podría lanzar el temido error ANR (Application Not Responding).

### 2. Ejemplo CON hilos (Multihilo clásico)
Por qué es correcto: Separa el trabajo en dos capas. Abre un hilo secundario real mediante Thread { ... } donde se procesa la tarea pesada sin afectar la fluidez de la pantalla. Al terminar, utiliza Handler(Looper.getMainLooper()).post para regresar de forma segura al hilo principal. Esto respeta la regla de oro de Android: las tareas pesadas van de fondo, la UI se actualiza en el hilo principal.

### 3. Ejemplo SIN corrutinas (Asincronismo tradicional)
Por qué es correcto: Representa la forma en que se programaba en Android antes de que existiera Kotlin o las corrutinas (usando Java). Handler.postDelayed no simula un proceso computacional pesado de fondo, sino que demuestra el concepto de programación asíncrona basada en eventos o callbacks futuros manejados por el sistema de mensajería nativo del sistema operativo (Looper).

### 4. Ejemplo CON corrutinas (Moderno y eficiente)
Por qué es correcto: Utiliza rememberCoroutineScope(), que es la herramienta nativa de Jetpack Compose para manejar asincronismo de manera segura. Muestra cómo la función de suspensión delay(5000) detiene el flujo de la corrutina sin necesidad de congelar ni bloquear ningún hilo físico del procesador, liberando memoria y recursos.