Clientes

GET /api/clientes
GET /api/clientes/{id}
GET /api/clientes/numero-identificacion/{numeroIdentificacion}
GET /api/clientes/correo/{correo}
POST /api/clientes
PUT /api/clientes/{id}
DELETE /api/clientes/{id}
Fuente: ClienteController.java

Cuentas

GET /api/cuentas
GET /api/cuentas/{id}
GET /api/cuentas/numero/{numeroCuenta}
GET /api/cuentas/cliente/{clienteId}
POST /api/cuentas
PUT /api/cuentas/{id}/activar
PUT /api/cuentas/{id}/inactivar
DELETE /api/cuentas/{id}/cancelar
DELETE /api/cuentas/{id}
Fuente: CuentaController.java

Estados

GET /api/estados
GET /api/estados/{id}
GET /api/estados/nombre/{nombre}
POST /api/estados
PUT /api/estados/{id}
DELETE /api/estados/{id}
Fuente: EstadoController.java

Tipos de cuenta

GET /api/tipos-cuenta
GET /api/tipos-cuenta/{id}
GET /api/tipos-cuenta/nombre/{nombre}
POST /api/tipos-cuenta
PUT /api/tipos-cuenta/{id}
DELETE /api/tipos-cuenta/{id}
Fuente: TipoCuentaController.java

Tipos de identificación

GET /api/tipos-identificacion
GET /api/tipos-identificacion/{id}
GET /api/tipos-identificacion/nombre/{nombre}
POST /api/tipos-identificacion
PUT /api/tipos-identificacion/{id}
DELETE /api/tipos-identificacion/{id}
Fuente: TipoIdentificacionController.java

Tipos de transacción

GET /api/tipos-transaccion
GET /api/tipos-transaccion/{id}
GET /api/tipos-transaccion/nombre/{nombre}
POST /api/tipos-transaccion
PUT /api/tipos-transaccion/{id}
DELETE /api/tipos-transaccion/{id}
Fuente: TipoTransaccionController.java

Transacciones

GET /api/transacciones
GET /api/transacciones/{id}
GET /api/transacciones/cuenta-origen/{cuentaOrigenId}
GET /api/transacciones/cuenta-destino/{cuentaDestinoId}
GET /api/transacciones/tipo/{tipoTransaccionId}
POST /api/transacciones
DELETE /api/transacciones/{id}
Fuente: TransaccionController