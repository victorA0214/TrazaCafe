# Persistencia de usuarios

Se modificó `src/main/java/Modelo/Usuario.java` para conservar los usuarios registrados sin reemplazar el `ArrayList`.

- Al registrar, actualizar, activar o desactivar un usuario, la lista se guarda en un archivo.
- Al iniciar la aplicación, los usuarios se cargan automáticamente desde ese archivo.
- El archivo se ubica en `${user.home}/TrazaCafeWeb/usuarios.dat` (por ejemplo, en Windows suele ser `C:\\Users\\TU_USUARIO\\TrazaCafeWeb\\usuarios.dat`).
- Las contraseñas se guardan en la lista como hash SHA-256, no como texto original.
- Esta corrección conserva usuarios. Las fincas y las muestras siguen en memoria y pueden perderse al reiniciar.

Para aplicar el cambio, abra el proyecto actualizado en NetBeans y ejecute **Clean and Build**; después vuelva a desplegarlo. Si usa otro equipo o cambia la cuenta del sistema operativo, el archivo de usuarios no se moverá automáticamente: deberá copiarse al directorio indicado si necesita migrar las cuentas.
