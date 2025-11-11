# Implementación de Principios SOLID

## S - Single Responsibility Principle (Principio de Responsabilidad Única)

Cada clase tiene una única responsabilidad:

- **Usuario, Evento, Asistencia, Recomendacion**: Solo representan datos (modelos)
- **IUsuarioService, IEventoService**: Solo definen contratos de servicios
- **UsuarioService, EventoService**: Solo implementan lógica de negocio
- **IUsuarioRepository, IEventoRepository**: Solo definen contratos de persistencia
- **UsuarioRepositoryJson, EventoRepositoryJson**: Solo manejan persistencia en JSON
- **EventoView, UsuarioView**: Solo manejan presentación de datos
- **CliController**: Solo orquesta la interacción CLI
- **LoginFrame, MainFrame, Panels**: Solo manejan interfaz gráfica

## O - Open/Closed Principle (Abierto/Cerrado)

El código está abierto a extensión pero cerrado a modificación:

- **BaseRepository<T>**: Clase abstracta que permite extender funcionalidad sin modificar el código base
- **Interfaces (IUsuarioService, IEventoService, IUsuarioRepository, IEventoRepository)**: Permiten crear nuevas implementaciones sin modificar las existentes
- **Ejemplo**: Se puede agregar `EventoRepositorySQL` implementando `IEventoRepository` sin modificar `EventoService`

## L - Liskov Substitution Principle (Sustitución de Liskov)

Los objetos de subclases pueden reemplazar a los de superclases:

- **UsuarioRepositoryJson** y **UsuarioRepositoryImpl** pueden usarse indistintamente donde se requiera `IUsuarioRepository`
- **EventoRepositoryJson** y **EventoRepositoryImpl** pueden usarse indistintamente donde se requiera `IEventoRepository`
- Las clases internas **EventoRepo**, **AsistenciaRepo**, **RecomendacionRepo** heredan de `BaseRepository<T>` y pueden sustituirse sin problemas
- Todos los servicios dependen de interfaces, no de implementaciones concretas

## I - Interface Segregation Principle (Segregación de Interfaces)

Las interfaces son específicas y cohesivas:

- **IUsuarioService**: Solo métodos relacionados con usuarios (registrar, obtener, iniciar sesión)
- **IEventoService**: Solo métodos relacionados con eventos y sus interacciones
- **IUsuarioRepository**: Solo operaciones CRUD de usuarios
- **IEventoRepository**: Solo operaciones relacionadas con eventos, asistencias y recomendaciones
- Ninguna interfaz fuerza a implementar métodos innecesarios

## D - Dependency Inversion Principle (Inversión de Dependencias)

Las clases de alto nivel dependen de abstracciones:

- **UsuarioService** depende de `IUsuarioRepository` (abstracción), no de `UsuarioRepositoryJson` (implementación)
- **EventoService** depende de `IEventoRepository` (abstracción), no de `EventoRepositoryJson` (implementación)
- **CliController** depende de `IUsuarioService` e `IEventoService` (abstracciones)
- **Todos los Panels de GUI** dependen de `IUsuarioService` e `IEventoService` (abstracciones)
- **RepositorioConfig** actúa como Factory para inyectar dependencias concretas

Este diseño permite cambiar implementaciones sin afectar el código de alto nivel, cumpliendo perfectamente con el principio de Inversión de Dependencias.