# Lautaro Gómez Salvi
# Buscador de Eventos Locales

Este proyecto es una aplicación desarrollada en **Kotlin** que permite buscar, visualizar y gestionar eventos locales. Está orientado a facilitar que los usuarios encuentren actividades, espectáculos, talleres y otros eventos en su ciudad o región de forma rápida y sencilla.

## Funcionalidades principales

- **Búsqueda de eventos** por nombre, fecha, ubicación y categoría.
- **Visualización detallada** de cada evento, incluyendo fecha, lugar, descripción y organizador.
- **Filtrado y ordenamiento** para encontrar eventos relevantes según preferencias.
- **Gestión de favoritos** para guardar eventos de interés.
- **Interfaz intuitiva** y amigable, pensada para una experiencia óptima tanto en escritorio como en dispositivos móviles.

## Objetivo

El objetivo de este proyecto es brindar una herramienta útil y moderna para la comunidad local, facilitando la difusión y el acceso a eventos y actividades culturales, deportivas, educativas y recreativas.

---

# Uso del Buscador de Eventos Locales

Aplicación completa en Kotlin para gestionar eventos locales con interfaces CLI y GUI (Swing).

## Requisitos Previos

Antes de ejecutar la aplicación, asegurate de tener instalado:

### 1. Java Development Kit (JDK)
- **Versión requerida**: JDK 17 o superior

### 2. Gradle (Opcional)
El proyecto incluye Gradle Wrapper, por lo que no es necesario instalar Gradle manualmente. Los scripts `gradlew` (Linux/Mac) y `gradlew.bat` (Windows) se encargan de todo.

### 3. Git (Para clonar el repositorio)
- **Descarga**: [Git](https://git-scm.com/downloads)
- **Verificar instalación**:
  ```bash
  git --version
  ```

## Instalación y Configuración

### Paso 1: Clonar el Repositorio

Abre una terminal y ejecuta:

```bash
git clone https://github.com/etec-programacion-2/programaci-n-2-2025-gomez-eventos-LautiiGomez.git
cd programaci-n-2-2025-gomez-eventos-LautiiGomez
```

### Paso 2: Verificar Estructura del Proyecto

Asegurate de que la estructura sea similar a:

```
programaci-n-2-2025-gomez-eventos-LautiiGomez/
├── app/
│   ├── src/
│   │   └── main/
│   │       └── kotlin/
│   │           └── org/example/
│   ├── build.gradle.kts
├── gradle/
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
└── README.md
```

### Paso 3: Dar Permisos de Ejecución (Linux)

En Linux, hay que darle permisos de ejecución al script de Gradle:

```bash
chmod +x gradlew
```

## Compilación del Proyecto

Antes de ejecutar, compila el proyecto para verificar que todo esté en orden:

### Linux:
```bash
./gradlew clean
./gradlew build
```

### Windows:
```cmd
gradlew.bat clean
gradlew.bat build
```

Si hay errores:
- Verifica que Java 17 esté instalado correctamente
- Asegurate de estar en la carpeta raíz del proyecto
- Revisa que no haya problemas de conexión (Gradle descarga dependencias)

## Ejecución de la Aplicación

El proyecto ofrece dos interfaces: **GUI (Interfaz Gráfica)** y **CLI (Línea de Comandos)**.

### Opción 1: Interfaz Gráfica (GUI) - Recomendada

Esta es la forma más visual y amigable de usar la aplicación.

#### Linux:
```bash
./gradlew run
```

Otra herramienta muy útil de ejecución que usé a lo largo de todo el proyecto es la siguiente:
```bash
./gradlew run --no-configuration-cache
```

#### Windows:
```cmd
gradlew.bat run
```

O también:
```cmd
gradlew.bat run --no-configuration-cache
```

### Opción 2: Interfaz de Línea de Comandos (CLI)

Para usar la versión de consola:

#### Linux:
```bash
./gradlew runCli --console=plain
```

#### Windows:
```cmd
gradlew.bat runCli --console=plain
```

## Archivos de Datos

La aplicación guarda los datos en archivos JSON en la carpeta raíz del proyecto:

- `usuarios.json` - Usuarios registrados
- `eventos.json` - Eventos creados
- `asistencias.json` - Confirmaciones de asistencia
- `recomendaciones.json` - Calificaciones de eventos

Estos archivos se crean automáticamente la primera vez que ejecutas la aplicación y guardas datos.

## Uso de la Aplicación

- **Primera Vez: Creando tu Cuenta**
La primera vez que uses la aplicación, vas a necesitar crear una cuenta. Cuando veas la ventana de inicio, vas a ver dos opciones: "Iniciar Sesión" y un enlace que dice "¿No tienes cuenta? Regístrate". Hacé clic en el enlace de registro.
Te va a aparecer un formulario muy sencillo con dos campos. En el primer campo escribí tu nombre completo, por ejemplo "Lautaro Gómez". En el segundo campo escribí tu dirección de email, por ejemplo "lautaro.gomez@email.com". Este email va a ser tu identificador único en el sistema, así que asegurate de escribirlo correctamente. Una vez completados ambos campos, hacé clic en el botón "Registrarse".
Si todo salió bien, vas a ver un mensaje de confirmación y automáticamente entrarás a la aplicación. 
- **Iniciando Sesión**
Cuando vuelvas a usar la aplicación después de cerrarla, tu cuenta seguirá existiendo. Para ingresar, simplemente escribí tu email en el campo correspondiente y hacé clic en "Iniciar Sesión". El sistema recordará tu información y te dará acceso inmediato.
Conociendo la Ventana Principal
Una vez dentro de la aplicación, vas a ver una ventana con tu nombre y email en la parte superior derecha, junto a un botón de "Cerrar Sesión". En el área principal vas a encontrar cuatro pestañas, cada una con una función específica. Podés hacer clic en cualquiera de estas pestañas para acceder a diferentes funcionalidades.
- **Creando tu Primer Evento**
Para crear un evento, asegurate de estar en la primera pestaña que dice "Crear Evento". Vas a ver un formulario con varios campos que debes completar.
En el campo "Nombre del evento", escribí un título atractivo y descriptivo, por ejemplo "Fiesta de fin de año". En el área de "Descripción", agregá detalles de tu evento. Podés escribir varios párrafos explicando qué incluye el evento, qué pueden esperar los asistentes, y cualquier información relevante.
En "Fecha y hora" tenés que ser muy cuidadoso con el formato. Podés escribir la fecha así: "15/12/2024 18:30” significa el 15 de diciembre de 2024 a las 6:30 de la tarde.
En "Ubicación" escribí el lugar donde se realizará el evento, siendo lo más específico posible. Por ejemplo: "Colegio ETEC" o "Escuela Técnica de la Universidad de Mendoza, Perito Moreno 2397, Godoy Cruz, Mendoza".
En "Categoría" podés escribir lo que quieras para clasificar tu evento. Algunos ejemplos: "Música", "Deportes", "Gastronomía", "Arte", "Tecnología", "Familiar", etc.
Para el tipo de entrada, vas a ver un menú desplegable con dos opciones. Si seleccionás "Gratis", el evento será de entrada gratuita. Si seleccionás "Pago", va a aparecer un campo adicional donde debes escribir el precio numérico. Por ejemplo, si la entrada cuesta 5000 pesos, escribí "5000" (sin puntos ni comas).
Una vez completados todos los campos, hacé clic en el botón "Crear Evento". Vas a ver un mensaje de confirmación indicando que tu evento fue creado exitosamente. Si querés crear otro evento inmediatamente, podés hacer clic en el botón "Limpiar" para vaciar el formulario y empezar de nuevo.
- **Viendo Todos los Eventos Disponibles**
Para ver qué eventos hay disponibles, haz clic en la segunda pestaña "Todos los Eventos". Verás una tabla con todos los eventos que existen en el sistema, tanto los que tú creaste como los creados por otros usuarios. La tabla muestra información resumida de cada evento: su nombre, cuándo será, dónde, su categoría, si es gratis o cuánto cuesta, y su calificación promedio si tiene recomendaciones.
Para ver más detalles de un evento específico, haz clic en cualquier fila de la tabla para seleccionarla (se pondrá de color azul), y luego haz clic en el botón "Ver Detalles" ubicado abajo. Aparecerá una ventana emergente mostrando toda la información completa del evento, incluyendo su descripción detallada y el promedio de calificaciones que ha recibido.
Si quieres asistir a un evento, selecciónalo en la tabla y haz clic en el botón "Confirmar Asistencia". El sistema guardará tu confirmación. Ten en cuenta que solo puedes confirmar una vez por evento; si intentas confirmar nuevamente, te avisará que ya lo hiciste previamente.
Para calificar un evento, selecciónalo y haz clic en el botón "Recomendar". Aparecerá una ventana preguntándote cuántas estrellas le das al evento, desde 1 estrella (no te gustó) hasta 5 estrellas (excelente). Selecciona tu calificación y confirma. Tu opinión se sumará al promedio general del evento que otros usuarios verán.
Si se agregaron eventos nuevos mientras tenías la aplicación abierta, puedes hacer clic en el botón "Refrescar" para actualizar la tabla con la información más reciente.
- **Buscando Eventos Específicos**
Cuando hay muchos eventos disponibles, puede ser útil filtrarlos. La tercera pestaña "Buscar Eventos" te permite hacer esto de varias formas.
Para buscar eventos en una ubicación específica, escribí el nombre de la ciudad o lugar en el campo "Ubicación". No necesitás escribir la ubicación completa exacta; el sistema buscará coincidencias parciales. Por ejemplo, si escribís "Mendoza", encontrará todos los eventos que tengan "Mendoza" en su ubicación. Después hacé clic en el botón "Buscar" que está al lado del campo.
Para buscar eventos por precio, escribe en el campo "Precio máximo" cuánto estás dispuesto a pagar como máximo. Por ejemplo, si escribís "3000", el sistema te mostrará todos los eventos que cuesten 3000 pesos o menos, además de los eventos gratuitos. Hacé clic en el botón "Buscar" correspondiente.
Si solo te interesan los eventos gratuitos, simplemente hacé clic en el botón "Solo Gratuitos". La tabla se actualizará mostrando únicamente eventos con entrada gratis.
Si querés volver a ver todos los eventos sin filtros, hacé clic en el botón "Mostrar Todos".
Los eventos encontrados se mostrarán en la tabla igual que en la pestaña de todos los eventos, y podés interactuar con ellos de la misma manera: ver detalles o confirmar asistencia.
- **Gestionando tus Propios Eventos**
La cuarta pestaña “Mis Eventos" te muestra únicamente los eventos que has creado. Esta es tu área personal de gestión.
La tabla muestra todos tus eventos con su información básica. Para ver los detalles completos de alguno, seleccionalo y hacé clic en "Ver Detalles".
Si necesitás modificar algún evento que creaste, selecciónalo en la tabla y hacé clic en el botón "Editar Evento". Se abrirá una ventana nueva con un formulario similar al de creación, pero ya completado con la información actual del evento. Podés cambiar el nombre, la descripción, la fecha, la ubicación y la categoría.
Importante: el tipo de entrada (gratis o pago) y el precio NO se pueden modificar una vez creado el evento. Si necesitás cambiar estos datos, tendrías que crear un evento nuevo.
Para guardar tus cambios, hacé clic en el botón "Guardar" en la ventana de edición. Si cambiás de opinión y no querés guardar los cambios, hacé clic en "Cancelar". Una vez guardados los cambios, vas a ver un mensaje de confirmación y la tabla se actualizará automáticamente mostrando la información nueva.
- **Cerrando Sesión**
Cuando termines de usar la aplicación, es recomendable cerrar sesión. Hacé clic en el botón "Cerrar Sesión" ubicado en la esquina superior derecha de la ventana. El sistema te preguntará si estás seguro. Si confirmás, volverás a la pantalla de inicio de sesión. Toda tu información quedará guardada de forma segura y podrás volver a acceder cuando quieras usando tu email.


### Funcionalidades Disponibles

#### GUI (Interfaz Gráfica)
- **Pestaña "Crear Evento"**: Formulario para crear nuevos eventos
- **Pestaña "Todos los Eventos"**: Lista de todos los eventos con opciones para:
    - Ver detalles
    - Confirmar asistencia
    - Recomendar (1-5 estrellas)
- **Pestaña "Buscar Eventos"**: Filtros por ubicación, precio y eventos gratuitos
- **Pestaña "Mis Eventos"**: Gestiona los eventos que creaste
    - Editar información
    - Ver estadísticas

#### CLI (Línea de Comandos)
Menú numerado del 1 al 8:
1. Crear evento
2. Editar evento
3. Listar todos los eventos
4. Buscar por ubicación
5. Buscar por precio
6. Confirmar asistencia
7. Recomendar evento
8. Salir

## Solución de Problemas

### Error: "Java version incompatible"
**Solución**: Instala JDK 17 o superior.

### Error: "Permission denied" (Linux/Mac)
**Solución**: Ejecuta `chmod +x gradlew`

### Error: "Could not find or load main class"
**Solución**:
1. Ejecuta `./gradlew clean`
2. Ejecuta `./gradlew build`
3. Intenta ejecutar nuevamente

### Tecnologías Usadas

- **Lenguaje**: Kotlin 2.2.10
- **JDK**: Java 17
- **Build Tool**: Gradle con Kotlin DSL
- **Serialización**: Gson 2.10.1
- **GUI**: Java Swing
- **Testing**: JUnit Jupiter 5.12.1


¡Disfruta usando el Buscador de Eventos Locales! 
