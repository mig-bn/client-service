# Client Service

Es un proyecto hecho con Sprinboot para gestion clientes, cuentas y movimientos financiones, es un API RESTful con **Spring Boot** y **Maven**. Permite el CRUD en los clientes personas asociados, asi como el manejo de cuentas y sus movimientos. Esta api incluye una pruebas unitarias y de integración, y se organiza bajo una escrutura de microservicios.

## Funcionalidades Principales
1. **Gestión de Clientes**: CRUD de clientes con datos de personas asociadas.
2. **Gestión de Cuentas**: CRUD de cuentas asociadas a un cliente específico.
3. **Gestión de Movimientos**: Registro de movimientos de tipo "crédito" o "débito" en las cuentas de los clientes.
4. **Reportes de Movimientos**: Generación de reportes de movimientos de cuentas de clientes en un rango de fechas.
---

## Nombre de la base de datos:
## Tablas y Relaciones

### Tabla: `persona`
| Campo            | Tipo          | Descripción                       |
|------------------|---------------|-----------------------------------|
| `id`             | BIGINT        | PK, Identificador único de persona |
| `nombre`         | VARCHAR(255)  | Nombre de la persona             |
| `genero`         | VARCHAR(50)   | Género de la persona             |
| `edad`           | INT           | Edad de la persona               |
| `identificacion` | VARCHAR(100)  | Número de identificación          |
| `direccion`      | VARCHAR(255)  | Dirección de la persona           |
| `telefono`       | VARCHAR(20)   | Teléfono de la persona            |

---

### Tabla: `cliente`
| Campo          | Tipo         | Descripción                         |
|----------------|--------------|-------------------------------------|
| `id`           | BIGINT       | PK, Identificador único de cliente  |
| `cliente_id`   | VARCHAR(100) | ID único para identificación externa|
| `contrasena`   | VARCHAR(100) | Contraseña del cliente              |
| `estado`       | BOOLEAN      | Estado de actividad del cliente     |
| `persona_id`   | BIGINT       | FK, Referencia a `persona(id)`      |

- **Relación**: Un cliente tiene una referencia a una persona (`persona_id`), con una relación `One-to-One`.

---

### Tabla: `cuenta`
| Campo          | Tipo          | Descripción                         |
|----------------|---------------|-------------------------------------|
| `id`           | BIGINT        | PK, Identificador único de cuenta   |
| `numero_cuenta`| VARCHAR(100)  | Número de cuenta única             |
| `tipo_cuenta`  | VARCHAR(50)   | Tipo de cuenta (Ahorro, Corriente)  |
| `saldo_inicial`| DECIMAL(15, 2)| Saldo inicial de la cuenta         |
| `estado`       | BOOLEAN       | Estado de actividad de la cuenta    |
| `cliente_id`   | BIGINT        | FK, Referencia a `cliente(id)`      |

- **Relación**: Una cuenta pertenece a un cliente (`cliente_id`), con una relación `Many-to-One`.

---

### Tabla: `movimiento`
| Campo             | Tipo           | Descripción                          |
|-------------------|----------------|--------------------------------------|
| `id`              | BIGINT         | PK, Identificador único de movimiento|
| `fecha`           | TIMESTAMP      | Fecha y hora del movimiento          |
| `tipo_movimiento` | VARCHAR(50)    | Tipo de movimiento (Débito, Crédito) |
| `monto`           | DECIMAL(15, 2) | Monto del movimiento                 |
| `saldo`           | DECIMAL(15, 2) | Saldo después del movimiento         |
| `cuenta_id`       | BIGINT         | FK, Referencia a `cuenta(id)`        |
| `cliente_id`      | BIGINT         | FK, Referencia a `cliente(id)`       |

- **Relación**: Un movimiento pertenece a una cuenta (`cuenta_id`) y a un cliente (`cliente_id`), con una relación `Many-to-One` para ambas.

---

## Relaciones Principales

- **Persona - Cliente**: `One-to-One`
- **Cliente - Cuenta**: `One-to-Many`
- **Cuenta - Movimiento**: `One-to-Many`

---

Este esquema detalla las tablas y relaciones necesarias para gestionar clientes, personas, cuentas y movimientos en la base de datos `task_quito`.
---

## Endpoints y Estructuras de Request

### 1. Cliente

#### Crear Cliente
- **Endpoint**: `POST /clientes`
- **Descripción**: Crea un nuevo cliente junto con los datos de la persona asociada.
- **Request JSON**:
  ```json
  {
    "clienteId": "C123456",
    "contrasena": "juan12345",
    "estado": true,
    "persona": {
      "nombre": "Juan Pérez 2",
      "genero": "Masculino",
      "edad": 35,
      "identificacion": "123456789",
      "direccion": "Calle Falsa 123",
      "telefono": "0987654321"
    }
  }


Aquí tienes el texto formateado en Markdown para tu archivo README.md en GitHub. Markdown es perfecto para darle estilo al texto en GitHub, y este formato asegurará que tu README.md se vea bien estructurado y claro.

markdown
Copiar código
# Client Service API

Este proyecto es una API RESTful para gestionar clientes, sus cuentas y movimientos financieros, desarrollado con **Spring Boot** y **Maven**. Permite la creación, modificación, y eliminación de clientes y personas asociadas, así como el manejo de cuentas y sus movimientos. Esta API incluye pruebas unitarias y de integración, y se organiza bajo una estructura de microservicios.

## Funcionalidades Principales
1. **Gestión de Clientes**: CRUD de clientes con datos de personas asociadas.
2. **Gestión de Cuentas**: CRUD de cuentas asociadas a un cliente específico.
3. **Gestión de Movimientos**: Registro de movimientos de tipo "crédito" o "débito" en las cuentas de los clientes.
4. **Reportes de Movimientos**: Generación de reportes de movimientos de cuentas de clientes en un rango de fechas.

---

## Endpoints y Estructuras de Request

### 1. Cliente

#### Crear Cliente
- **Endpoint**: `POST /clientes`
- **Descripción**: Crea un nuevo cliente junto con los datos de la persona asociada.
- **Request JSON**:
  ```json
  {
    "clienteId": "C123456",
    "contrasena": "juan12345",
    "estado": true,
    "persona": {
      "nombre": "Juan Pérez 2",
      "genero": "Masculino",
      "edad": 35,
      "identificacion": "123456789",
      "direccion": "Calle Falsa 123",
      "telefono": "0987654321"
    }
  }
  ```
#### Obtener Cliente por ID
- **Endpoint**: `GET /clientes/{clienteId}`
- **Descripción**: Obtiene la información de un cliente específico por su clienteId.

### Actualizar Cliente
- **Endpoint**: `PUT /clientes/{clienteId}`
- Descripción: Actualiza los datos de un cliente y su persona asociada.
- Request JSON:
  ```
  {
    "contrasena": "nuevaContrasena",
    "estado": false,
    "persona": {
      "nombre": "Juan Pérez Modificado",
      "edad": 36
    }
  }


### Movimientos
- **Registrar Movimiento**
- **Endpoint**: `POST /movimientos`
- **Descripción**: Registra un movimiento (crédito o débito) en una cuenta específica.
- **Request JSON**:
```
{
  "numeroCuenta": "1234567890",
  "tipoMovimiento": "debito",
  "monto": 1000.0,
  "clienteId": "C123456"
} 
```

### Reportes
- **Generar Reporte de Movimientos por Fecha**
- **Endpoint**: `GET /reportes/{fechaInicio}/{fechaFin}`
- **Descripción**: Genera un reporte del estado de cuentas y movimientos en un rango de fechas para todos los clientes.
- Ejemplo de Request:
``` GET /reportes/2023-01-01/2023-12-31 ```
