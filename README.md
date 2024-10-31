# Client Service

Es un proyecto hecho con Sprinboot para gestion clientes, cuentas y movimientos financiones, es un API RESTful con **Spring Boot** y **Maven**. Permite el CRUD en los clientes personas asociados, asi como el manejo de cuentas y sus movimientos. Esta api incluye una pruebas unitarias y de integración, y se organiza bajo una escrutura de microservicios.

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
#### Obtener Cliente por ID
- **Endpoint**: `GET /clientes/{clienteId}`
- **Descripción**: Obtiene la información de un cliente específico por su clienteId.

### Actualizar Cliente
-**Endpoint**: `PUT /clientes/{clienteId}`
Descripción: Actualiza los datos de un cliente y su persona asociada.
Request JSON:
  ```json
  {
    "contrasena": "nuevaContrasena",
    "estado": false,
    "persona": {
      "nombre": "Juan Pérez Modificado",
      "edad": 36
    }
  }

