# Documentación API - Banco

**Base URL:** `http://localhost:8080`

---

## 1. Clientes

### GET - Obtener todos los clientes
```bash
curl -X GET http://localhost:8080/api/clientes
```

### GET - Obtener cliente por ID
```bash
curl -X GET http://localhost:8080/api/clientes/1
```

### GET - Obtener cliente por número de identificación
```bash
curl -X GET http://localhost:8080/api/clientes/numero-identificacion/12345678
```

### GET - Obtener cliente por correo
```bash
curl -X GET http://localhost:8080/api/clientes/correo/juan@example.com
```

### POST - Crear nuevo cliente
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Juan Perez\",\"apellido\":\"Garcia\",\"numeroIdentificacion\":\"12345678\",\"correo\":\"juan@example.com\",\"tipoIdentificacionId\":1}"
```

### PUT - Actualizar cliente
```bash
curl -X PUT http://localhost:8080/api/clientes/1 \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Juan Actualizado\",\"apellido\":\"Garcia\",\"numeroIdentificacion\":\"12345678\",\"correo\":\"juan.nuevo@example.com\",\"tipoIdentificacionId\":1}"
```

### DELETE - Eliminar cliente
```bash
curl -X DELETE http://localhost:8080/api/clientes/1
```

---

## 2. Cuentas

### GET - Obtener todas las cuentas
```bash
curl -X GET http://localhost:8080/api/cuentas
```

### GET - Obtener cuenta por ID
```bash
curl -X GET http://localhost:8080/api/cuentas/1
```

### GET - Obtener cuenta por número de cuenta
```bash
curl -X GET http://localhost:8080/api/cuentas/numero/1001001
```

### GET - Obtener cuentas de un cliente
```bash
curl -X GET http://localhost:8080/api/cuentas/cliente/1
```

### POST - Crear nueva cuenta
```bash
curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d "{\"numeroCuenta\":\"1001001\",\"clienteId\":1,\"tipoCuentaId\":1,\"estadoId\":1,\"saldo\":5000.00}"
```

### PUT - Activar cuenta
```bash
curl -X PUT http://localhost:8080/api/cuentas/1/activar
```

### PUT - Inactivar cuenta
```bash
curl -X PUT http://localhost:8080/api/cuentas/1/inactivar
```

### DELETE - Cancelar cuenta
```bash
curl -X DELETE http://localhost:8080/api/cuentas/1/cancelar
```

### DELETE - Eliminar cuenta
```bash
curl -X DELETE http://localhost:8080/api/cuentas/1
```

---

## 3. Estados

### GET - Obtener todos los estados
```bash
curl -X GET http://localhost:8080/api/estados
```

### GET - Obtener estado por ID
```bash
curl -X GET http://localhost:8080/api/estados/1
```

### GET - Obtener estado por nombre
```bash
curl -X GET http://localhost:8080/api/estados/nombre/Activo
```

### POST - Crear nuevo estado
```bash
curl -X POST http://localhost:8080/api/estados \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Activo\",\"descripcion\":\"Cuenta activa y disponible\"}"
```

### PUT - Actualizar estado
```bash
curl -X PUT http://localhost:8080/api/estados/1 \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Activo\",\"descripcion\":\"Cuenta activa y disponible para transacciones\"}"
```

### DELETE - Eliminar estado
```bash
curl -X DELETE http://localhost:8080/api/estados/1
```

---

## 4. Tipos de Cuenta

### GET - Obtener todos los tipos de cuenta
```bash
curl -X GET http://localhost:8080/api/tipos-cuenta
```

### GET - Obtener tipo de cuenta por ID
```bash
curl -X GET http://localhost:8080/api/tipos-cuenta/1
```

### GET - Obtener tipo de cuenta por nombre
```bash
curl -X GET http://localhost:8080/api/tipos-cuenta/nombre/Ahorros
```

### POST - Crear nuevo tipo de cuenta
```bash
curl -X POST http://localhost:8080/api/tipos-cuenta \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Ahorros\",\"descripcion\":\"Cuenta de ahorros con intereses\"}"
```

### PUT - Actualizar tipo de cuenta
```bash
curl -X PUT http://localhost:8080/api/tipos-cuenta/1 \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Ahorros\",\"descripcion\":\"Cuenta de ahorros con intereses actualizados\"}"
```

### DELETE - Eliminar tipo de cuenta
```bash
curl -X DELETE http://localhost:8080/api/tipos-cuenta/1
```

---

## 5. Tipos de Identificación

### GET - Obtener todos los tipos de identificación
```bash
curl -X GET http://localhost:8080/api/tipos-identificacion
```

### GET - Obtener tipo de identificación por ID
```bash
curl -X GET http://localhost:8080/api/tipos-identificacion/1
```

### GET - Obtener tipo de identificación por nombre
```bash
curl -X GET http://localhost:8080/api/tipos-identificacion/nombre/Cédula
```

### POST - Crear nuevo tipo de identificación
```bash
curl -X POST http://localhost:8080/api/tipos-identificacion \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Cédula\",\"descripcion\":\"Cédula de identidad\"}"
```

### PUT - Actualizar tipo de identificación
```bash
curl -X PUT http://localhost:8080/api/tipos-identificacion/1 \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Cédula\",\"descripcion\":\"Cédula de identidad actualizada\"}"
```

### DELETE - Eliminar tipo de identificación
```bash
curl -X DELETE http://localhost:8080/api/tipos-identificacion/1
```

---

## 6. Tipos de Transacción

### GET - Obtener todos los tipos de transacción
```bash
curl -X GET http://localhost:8080/api/tipos-transaccion
```

### GET - Obtener tipo de transacción por ID
```bash
curl -X GET http://localhost:8080/api/tipos-transaccion/1
```

### GET - Obtener tipo de transacción por nombre
```bash
curl -X GET http://localhost:8080/api/tipos-transaccion/nombre/Transferencia
```

### POST - Crear nuevo tipo de transacción
```bash
curl -X POST http://localhost:8080/api/tipos-transaccion \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Transferencia\",\"descripcion\":\"Transferencia entre cuentas\"}"
```

### PUT - Actualizar tipo de transacción
```bash
curl -X PUT http://localhost:8080/api/tipos-transaccion/1 \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Transferencia\",\"descripcion\":\"Transferencia entre cuentas actualizadas\"}"
```

### DELETE - Eliminar tipo de transacción
```bash
curl -X DELETE http://localhost:8080/api/tipos-transaccion/1
```

---

## 7. Transacciones

### GET - Obtener todas las transacciones
```bash
curl -X GET http://localhost:8080/api/transacciones
```

### GET - Obtener transacción por ID
```bash
curl -X GET http://localhost:8080/api/transacciones/1
```

### GET - Obtener transacciones por cuenta origen
```bash
curl -X GET http://localhost:8080/api/transacciones/cuenta-origen/1
```

### GET - Obtener transacciones por cuenta destino
```bash
curl -X GET http://localhost:8080/api/transacciones/cuenta-destino/2
```

### GET - Obtener transacciones por tipo
```bash
curl -X GET http://localhost:8080/api/transacciones/tipo/1
```

### POST - Crear transferencia entre cuentas
```bash
curl -X POST http://localhost:8080/api/transacciones \
  -H "Content-Type: application/json" \
  -d "{\"cuentaOrigenId\":1,\"cuentaDestinoId\":2,\"tipoTransaccionId\":1,\"monto\":500.00,\"descripcion\":\"Transferencia de prueba\"}"
```

### POST - Crear retiro de dinero
```bash
curl -X POST http://localhost:8080/api/transacciones \
  -H "Content-Type: application/json" \
  -d "{\"cuentaOrigenId\":1,\"tipoTransaccionId\":2,\"monto\":200.00,\"descripcion\":\"Retiro de dinero\"}"
```

### POST - Crear consignación (depósito)
```bash
curl -X POST http://localhost:8080/api/transacciones \
  -H "Content-Type: application/json" \
  -d "{\"cuentaOrigenId\":2,\"tipoTransaccionId\":3,\"monto\":1000.00,\"descripcion\":\"Consignación de dinero\"}"
```

### DELETE - Eliminar transacción
```bash
curl -X DELETE http://localhost:8080/api/transacciones/1
```

---

## Guía Rápida de Prueba

**Paso 1: Verificar tipos de referencia**
```bash
curl -X GET http://localhost:8080/api/tipos-identificacion
curl -X GET http://localhost:8080/api/tipos-cuenta
curl -X GET http://localhost:8080/api/tipos-transaccion
curl -X GET http://localhost:8080/api/estados
```

**Paso 2: Crear un cliente**
```bash
curl -X POST http://localhost:8080/api/clientes \
  -H "Content-Type: application/json" \
  -d "{\"nombre\":\"Carlos\",\"apellido\":\"López\",\"numeroIdentificacion\":\"98765432\",\"correo\":\"carlos@example.com\",\"tipoIdentificacionId\":1}"
```

**Paso 3: Crear cuentas para el cliente**
```bash
curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d "{\"numeroCuenta\":\"2001001\",\"clienteId\":1,\"tipoCuentaId\":1,\"estadoId\":1,\"saldo\":10000.00}"

curl -X POST http://localhost:8080/api/cuentas \
  -H "Content-Type: application/json" \
  -d "{\"numeroCuenta\":\"2001002\",\"clienteId\":1,\"tipoCuentaId\":2,\"estadoId\":1,\"saldo\":5000.00}"
```

**Paso 4: Hacer transacciones**
```bash
# Transferencia entre las cuentas creadas
curl -X POST http://localhost:8080/api/transacciones \
  -H "Content-Type: application/json" \
  -d "{\"cuentaOrigenId\":1,\"cuentaDestinoId\":2,\"tipoTransaccionId\":1,\"monto\":1500.00,\"descripcion\":\"Transferencia entre mis cuentas\"}"

# Retiro
curl -X POST http://localhost:8080/api/transacciones \
  -H "Content-Type: application/json" \
  -d "{\"cuentaOrigenId\":1,\"tipoTransaccionId\":2,\"monto\":300.00,\"descripcion\":\"Retiro en cajero\"}"
```

**Paso 5: Consultar transacciones**
```bash
curl -X GET http://localhost:8080/api/transacciones
curl -X GET http://localhost:8080/api/transacciones/cuenta-origen/1
curl -X GET http://localhost:8080/api/transacciones/tipo/1
```