/* Ejecutar en SQL Server Management Studio con un usuario administrador. */
IF DB_ID(N'TrazaCafe') IS NULL CREATE DATABASE TrazaCafe;
GO
USE TrazaCafe;
GO
IF OBJECT_ID('dbo.Usuarios','U') IS NULL
CREATE TABLE dbo.Usuarios(
 idUsuario INT IDENTITY(1,1) PRIMARY KEY,
 nombre NVARCHAR(120) NOT NULL,
 correo NVARCHAR(150) NOT NULL UNIQUE,
 telefono NVARCHAR(30) NULL,
 municipio NVARCHAR(100) NULL,
 contrasenaHash VARBINARY(32) NOT NULL,
 rol NVARCHAR(20) NOT NULL CHECK (rol IN ('Productor','Catador','Administrador')),
 estado BIT NOT NULL CONSTRAINT DF_Usuarios_estado DEFAULT(1),
 fechaCreacion DATETIME2 NOT NULL CONSTRAINT DF_Usuarios_fecha DEFAULT(SYSDATETIME())
);
GO
IF OBJECT_ID('dbo.Fincas','U') IS NULL
CREATE TABLE dbo.Fincas(
 idFinca INT IDENTITY(1,1) PRIMARY KEY,
 nombre NVARCHAR(120) NOT NULL,
 municipio NVARCHAR(100) NOT NULL,
 vereda NVARCHAR(100) NULL,
 altitud DECIMAL(8,2) NOT NULL CHECK(altitud>=0),
 areaCultivada DECIMAL(10,2) NOT NULL CHECK(areaCultivada>=0),
 variedadPredominante NVARCHAR(100) NULL,
 idProductor INT NOT NULL,
 estado BIT NOT NULL CONSTRAINT DF_Fincas_estado DEFAULT(1),
 fechaRegistro DATETIME2 NOT NULL CONSTRAINT DF_Fincas_fecha DEFAULT(SYSDATETIME()),
 CONSTRAINT FK_Fincas_Usuarios FOREIGN KEY(idProductor) REFERENCES dbo.Usuarios(idUsuario)
);
GO
IF OBJECT_ID('dbo.Muestras','U') IS NULL
CREATE TABLE dbo.Muestras(
 idMuestra INT IDENTITY(1,1) PRIMARY KEY,
 lote NVARCHAR(80) NOT NULL UNIQUE,
 variedad NVARCHAR(100) NOT NULL,
 fechaRecoleccion DATE NOT NULL,
 procesoBeneficio NVARCHAR(50) NULL,
 cantidadProducida NVARCHAR(60) NULL,
 estado NVARCHAR(30) NOT NULL CONSTRAINT DF_Muestras_estado DEFAULT('Pendiente'),
 idFinca INT NOT NULL,
 fechaRegistro DATETIME2 NOT NULL CONSTRAINT DF_Muestras_fecha DEFAULT(SYSDATETIME()),
 CONSTRAINT FK_Muestras_Fincas FOREIGN KEY(idFinca) REFERENCES dbo.Fincas(idFinca)
);
GO
IF OBJECT_ID('dbo.ResultadoFisico','U') IS NULL
CREATE TABLE dbo.ResultadoFisico(
 idResultadoFisico INT IDENTITY(1,1) PRIMARY KEY,
 idMuestra INT NOT NULL UNIQUE,
 humedadFinal DECIMAL(5,2) NOT NULL CHECK(humedadFinal BETWEEN 0 AND 100),
 porcentajeDefectos DECIMAL(5,2) NOT NULL CHECK(porcentajeDefectos BETWEEN 0 AND 100),
 rendimientoTrilla DECIMAL(5,2) NOT NULL CHECK(rendimientoTrilla BETWEEN 0 AND 100),
 fechaRegistro DATETIME2 NOT NULL CONSTRAINT DF_Fisico_fecha DEFAULT(SYSDATETIME()),
 CONSTRAINT FK_Fisico_Muestras FOREIGN KEY(idMuestra) REFERENCES dbo.Muestras(idMuestra)
);
GO
IF OBJECT_ID('dbo.ResultadoSensorial','U') IS NULL
CREATE TABLE dbo.ResultadoSensorial(
 idResultadoSensorial INT IDENTITY(1,1) PRIMARY KEY,
 idMuestra INT NOT NULL UNIQUE,
 puntajeCatacion DECIMAL(5,2) NOT NULL CHECK(puntajeCatacion BETWEEN 0 AND 100),
 notasPerfilTaza NVARCHAR(2000) NOT NULL,
 observaciones NVARCHAR(2000) NULL,
 fechaRegistro DATETIME2 NOT NULL CONSTRAINT DF_Sensorial_fecha DEFAULT(SYSDATETIME()),
 CONSTRAINT FK_Sensorial_Muestras FOREIGN KEY(idMuestra) REFERENCES dbo.Muestras(idMuestra)
);
GO
/* Crear un usuario inicial desde el formulario RegistrarUsuario.jsp después de configurar la conexión.
   La contraseña se almacena como SHA-256 calculado por SQL Server; nunca se guarda en texto plano. */

/* ---------- Usuario de conexión para la aplicación web (ejecutar una sola vez) ----------
   Contraseña de desarrollo local: TrazaCafe2026* (la misma que usa DB.java).
   Si la cambias aquí, cámbiala también en DB.java. */
USE master;
GO
IF SUSER_ID(N'trazacafe_app') IS NULL
    CREATE LOGIN trazacafe_app WITH PASSWORD = N'TrazaCafe2026*', CHECK_POLICY = OFF;
GO
USE TrazaCafe;
GO
IF USER_ID(N'trazacafe_app') IS NULL
    CREATE USER trazacafe_app FOR LOGIN trazacafe_app;
GO
ALTER ROLE db_datareader ADD MEMBER trazacafe_app;
ALTER ROLE db_datawriter ADD MEMBER trazacafe_app;
GO
