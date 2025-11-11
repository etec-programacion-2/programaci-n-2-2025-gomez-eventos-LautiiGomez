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

Antes de ejecutar la aplicación, asegúrate de tener instalado:

### 1. Java Development Kit (JDK)
- **Versión requerida**: JDK 21 o superior
- **Descarga**: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) o [OpenJDK](https://adoptium.net/)
- **Verificar instalación**:
  ```bash
  java -version
  ```
  Deberías ver algo como: `java version "21.0.x"`

### 2. Gradle (Opcional)
El proyecto incluye Gradle Wrapper, por lo que NO necesitas instalar Gradle manualmente. Los scripts `gradlew` (Linux/Mac) y `gradlew.bat` (Windows) se encargan de todo.

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
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

Si descargaste un archivo ZIP, descomprímelo y navega a la carpeta del proyecto:

```bash
cd ruta/a/tu-proyecto
```

### Paso 2: Verificar Estructura del Proyecto

Asegúrate de que la estructura sea similar a:

```
proyecto/
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

### Paso 3: Dar Permisos de Ejecución (Linux/Mac)

En Linux o macOS, dale permisos de ejecución al script de Gradle:

```bash
chmod +x gradlew
```

## Compilación del Proyecto

Antes de ejecutar, compila el proyecto para verificar que todo esté en orden:

### Linux/Mac:
```bash
./gradlew clean
./gradlew build
```

### Windows:
```cmd
gradlew.bat clean
gradlew.bat build
```

**Salida esperada**: Deberías ver `BUILD SUCCESSFUL` al final.

Si hay errores:
- Verifica que Java 21 esté instalado correctamente
- Asegúrate de estar en la carpeta raíz del proyecto
- Revisa que no haya problemas de conexión (Gradle descarga dependencias)

## Ejecución de la Aplicación

El proyecto ofrece dos interfaces: **GUI (Interfaz Gráfica)** y **CLI (Línea de Comandos)**.

### Opción 1: Interfaz Gráfica (GUI) - Recomendada

Esta es la forma más visual y amigable de usar la aplicación.

#### Linux/Mac:
```bash
./gradlew run
```

o específicamente:
```bash
./gradlew runGui
```

#### Windows:
```cmd
gradlew.bat run
```

o específicamente:
```cmd
gradlew.bat runGui
```

**Resultado**: Se abrirá una ventana gráfica con la pantalla de login.

### Opción 2: Interfaz de Línea de Comandos (CLI)

Para usar la versión de consola:

#### Linux/Mac:
```bash
./gradlew runCli --console=plain
```

#### Windows:
```cmd
gradlew.bat runCli --console=plain
```

**Resultado**: Verás un menú interactivo en la terminal.

## Archivos de Datos

La aplicación guarda los datos en archivos JSON en la carpeta raíz del proyecto:

- `usuarios.json` - Usuarios registrados
- `eventos.json` - Eventos creados
- `asistencias.json` - Confirmaciones de asistencia
- `recomendaciones.json` - Calificaciones de eventos

Estos archivos se crean automáticamente la primera vez que ejecutas la aplicación y guardas datos.

## Uso de la Aplicación

### Primera Vez

1. Ejecuta la aplicación (GUI o CLI)
2. Selecciona "Registrarse" o la opción de registro
3. Ingresa tu nombre y email
4. ¡Listo! Ya puedes usar todas las funcionalidades

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
**Solución**: Instala JDK 21 o superior.

### Error: "Permission denied" (Linux/Mac)
**Solución**: Ejecuta `chmod +x gradlew`

### Error: "Could not find or load main class"
**Solución**:
1. Ejecuta `./gradlew clean`
2. Ejecuta `./gradlew build`
3. Intenta ejecutar nuevamente

### La ventana GUI no aparece
**Solución**:
1. Verifica que estés usando el comando correcto: `./gradlew run` o `./gradlew runGui`
2. Asegúrate de tener un entorno gráfico (no funciona en servidores sin GUI)

### Error: "Address already in use"
**Solución**: Ya hay una instancia de la aplicación corriendo. Ciérrala antes de ejecutar otra.

### Los datos no se guardan
**Solución**: Verifica que tienes permisos de escritura en la carpeta del proyecto.

## Documentación Adicional

- **SOLID.md**: Explicación de cómo el proyecto implementa los principios SOLID
- **Instructivo de usuario**: Guía completa para usuarios no técnicos (ver sección anterior de este README)

## Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

```
Presentación (CLI/GUI)
        ↓
Servicios (Lógica de Negocio)
        ↓
Repositorios (Abstracción)
        ↓
Persistencia (JSON)
```

### Tecnologías Utilizadas

- **Lenguaje**: Kotlin 2.2.10
- **JDK**: Java 21
- **Build Tool**: Gradle con Kotlin DSL
- **Serialización**: Gson 2.10.1
- **GUI**: Java Swing
- **Testing**: JUnit Jupiter 5.12.1

### Patrones de Diseño Implementados

- Patrón Repositorio
- Inversión de Dependencias (DIP)
- Inyección de Dependencias
- Modelo-Vista-Controlador (MVC)
- Factory (RepositorioConfig)
- Herencia (BaseRepository)

## Contribución

Si deseas contribuir al proyecto:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/NuevaFuncionalidad`)
3. Commit tus cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/NuevaFuncionalidad`)
5. Crea un Pull Request
---

## Checklist de Verificación

Antes de ejecutar, verifica:

- [ ] JDK 21 instalado (`java -version`)
- [ ] Proyecto clonado o descargado
- [ ] Terminal abierta en la carpeta del proyecto
- [ ] Permisos de ejecución dados a `gradlew` (Linux/Mac)
- [ ] Compilación exitosa (`./gradlew build`)
- [ ] Comando de ejecución correcto según la interfaz deseada

¡Disfruta usando el Buscador de Eventos Locales! 
