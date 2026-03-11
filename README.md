# Secretaría de Tránsito

Proyecto para **Programación Distribuida y Paralela**

- Arquitectura MVC (Modelo - Vista - Controlador)
- CRUD completo de propietarios y vehículos
- CRUD de infracciones (crear/listar/eliminar)
- Reportes en tablas JSP
- Sistema de puntos de licencia
- Persistencia simple con `ArrayList` + archivos JSON (sin base de datos)

## Estructura del proyecto

```text
src/main/java/com/transito
├── controller
│   ├── HomeController
│   ├── PropietarioController
│   ├── VehiculoController
│   ├── InfraccionController
│   └── ReporteController
├── model
│   ├── Propietario
│   ├── Persona
│   ├── Empresa
│   ├── Vehiculo
│   ├── Infraccion
│   ├── Agente
│   ├── Camara
│   ├── TipoVehiculo
│   ├── TipoDeteccion
│   └── TipoSeveridad
├── repository
│   ├── PropietarioRepository
│   ├── VehiculoRepository
│   └── InfraccionRepository
├── service
│   ├── PropietarioService
│   ├── VehiculoService
│   ├── InfraccionService
│   └── ReporteService
└── util
    └── FileManager

src/main/webapp/WEB-INF/jsp
├── index.jsp
├── propietarios.jsp
├── vehiculos.jsp
├── infracciones.jsp
└── reportes.jsp

data
├── propietarios.json
├── vehiculos.json
└── infracciones.json
```

- `http://localhost:8080/`

Menú principal:

- Propietarios
- Vehículos
- Infracciones
- Reportes

## Reglas implementadas

1. Un propietario puede tener varios vehículos.
2. Un vehículo tiene un solo propietario.
3. Un vehículo puede tener varias infracciones.
4. Una infraccion le pertenece a un solo vehiculo.

## Sistema de puntos de licencia

- Cada propietario inicia con **20 puntos**.
- Al registrar infracción:
  - LEVE: -2
  - MEDIA: -4
  - GRAVE: -6
- Si llega a 0 puntos, se muestra:

```text
Licencia suspendida por exceso de infracciones
```

## Reportes implementados

1. Infracciones por vehículo.
2. Propietarios con más infracciones.
3. Vehículos con más multas.
4. Total de dinero en multas.