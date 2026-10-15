# Trazacafé Web — configuración local

## Requisitos
- JDK 11 o superior.
- NetBeans con soporte Maven.
- Servidor compatible con Jakarta EE 10 / Servlet 6.0 (por ejemplo Payara 6 o Tomcat 10.1).

## Almacenamiento
No usa base de datos. Todo se guarda en memoria con `ArrayList`:
- Usuarios: `Usuario.getUsuarios()` (contraseña con hash SHA-256).
- Fincas y muestras: clase `Modelo.Almacen`. Los resultados físico y sensorial se guardan dentro de cada `Muestra`.

**Los datos se pierden al reiniciar o volver a desplegar la aplicación**, así que después de cada arranque hay que registrar de nuevo un usuario.

## Ejecutar
1. Abre la carpeta `TrazaCafeWeb` como proyecto Maven en NetBeans.
2. Selecciona el servidor y ejecuta con *Clean and Build* y *Run*.
3. Abre `RegistrarUsuario.jsp` y registra el primer usuario (rol Administrador).
4. Inicia sesión y registra una finca, luego una muestra, y por último los análisis.

## Rutas principales
- `/ServletUsuario`: iniciar sesión, registrar usuario y cerrar sesión (campo `accion`).
- `/ServletFinca`: listar, registrar y desactivar fincas.
- `/ServletMuestra`: registrar muestras.
- `/ServletAnalisis`: guardar análisis físico o sensorial (campo `tipo`).
- `/ServletReporte`: informe consolidado de muestras y resultados.


## Administración de usuarios

- El primer usuario que se registre debe tener el rol **Administrador**.
- Después de crear esa cuenta, solo una sesión autenticada con rol Administrador puede registrar nuevas cuentas.
- En el menú superior y en el panel aparece **Administrar usuarios** para administradores.
- Desde esa pantalla se pueden editar nombre, correo, teléfono y municipio; también activar o desactivar cuentas.
- La aplicación impide desactivar la propia cuenta desde esa pantalla y evita desactivar al último administrador activo.

**Importante sobre esta versión:** el código actual guarda usuarios en una lista en memoria (`Usuario.usuarios`). Por eso, los usuarios y sus cambios se pierden al reiniciar o redesplegar la aplicación. Esta administración sigue el almacenamiento que ya usa el proyecto; para conservar los cambios permanentemente hay que conectar `Usuario` a la tabla `dbo.Usuarios` de SQL Server.
