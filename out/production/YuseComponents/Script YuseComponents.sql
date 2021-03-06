USE master
GO
IF EXISTS (SELECT * FROM master.dbo.SYSDATABASES WHERE NAME = 'YuseComponents') --Si no existe la base de datos
BEGIN
	DROP DATABASE YuseComponents
END

CREATE DATABASE YuseComponents
GO

USE YuseComponents
GO

CREATE TABLE Usuarios(
	IDUsuario uniqueidentifier CONSTRAINT PK_Usuarios Primary Key,
	NickUsuario varchar(30) NOT NULL UNIQUE,
	Contrasenha varbinary(MAX) NOT NULL,
	Nombre varchar(30) NULL,
	Apellido varchar(50) NULL,
	Saldo money NULL Default 0,
	Descuento decimal (3,2) NOT NULL DEFAULT 0,
	CONSTRAINT CK_Descuento Check (Descuento BETWEEN 0 AND 1)
)
GO

CREATE TABLE ListasCreadas(
	[N? de lista] int IDENTITY(10000,1) CONSTRAINT PK_Lista Primary Key,
	Nombre varchar(20) NOT NULL,
	IDUsuario uniqueidentifier CONSTRAINT FK_CreaListaUsuaria Foreign Key REFERENCES Usuarios (IDUsuario),
	Publica bit NOT NULL Default 1,
	Privada AS CASE WHEN (Publica=1) THEN 0 ELSE 1 END
)
GO

CREATE TABLE Transacciones(
	IDTransaccion uniqueidentifier CONSTRAINT PK_Transaccion Primary Key,
	IDUsuario uniqueidentifier CONSTRAINT FK_TransaccionUsuario Foreign Key REFERENCES Usuarios (IDUsuario),
	[N? de lista] int NOT NULL CONSTRAINT FK_ListaTransacciones Foreign Key REFERENCES ListasCreadas ([N? de lista]),
	[Fecha y hora] datetime2 NOT NULL Default CURRENT_TIMESTAMP,
	[Total cobrado] money NULL Default NULL
)
GO

CREATE TABLE TiposComponente(
	[Nombre tipo] varchar(20) CONSTRAINT PK_TipoComponente Primary Key
)
GO

CREATE TABLE Propiedades(
	Propiedad varchar(20) CONSTRAINT PK_Propiedad Primary Key
)
GO

CREATE TABLE Propiedades_Tipos(
	Propiedad varchar(20) CONSTRAINT FK_PriopiedadTipo Foreign Key REFERENCES Propiedades (Propiedad),
	Tipo varchar(20) CONSTRAINT FK_TipoPriopieda Foreign Key REFERENCES TiposComponente ([Nombre tipo]),
	CONSTRAINT PK_PropiedadTipo Primary Key (Propiedad, Tipo)
)
GO

CREATE TABLE Componentes(
	IdComponente uniqueidentifier CONSTRAINT PK_Componentes Primary Key,
	TipoComponente varchar(20) CONSTRAINT FK_TipoComponente Foreign Key REFERENCES TiposComponente ([Nombre tipo]),
	Nombre varchar(50) NOT NULL,
	Precio money NOT NULL,
	Cantidad int NULL
)
GO

CREATE TABLE Propiedades_Componentes(
	IdComponente uniqueidentifier CONSTRAINT FK_ComponentesPropiedades Foreign Key REFERENCES Componentes (IdComponente),
	Propiedad varchar(20) CONSTRAINT FK_PropiedadesComponentes Foreign Key REFERENCES Propiedades (Propiedad),
	Valor varchar(15) NULL,
	Unidad varchar(10) NULL,
	CONSTRAINT PK_PropiedadComponente Primary Key (IdComponente, Propiedad)
)
GO

CREATE TABLE ListaComponente(
	[N? de lista] int NOT NULL CONSTRAINT FK_ListaComponente Foreign Key REFERENCES ListasCreadas ([N? de lista]),
	IdComponente uniqueidentifier CONSTRAINT FK_ComponentesLista Foreign Key REFERENCES Componentes (IdComponente),
	CONSTRAINT PK_ListaComponente Primary Key ([N? de lista], IdComponente)
)
GO

IF NOT EXISTS (SELECT name FROM [sys].[server_principals] WHERE name = 'personaje')
BEGIN
--Creacion del usuario de la base de datos
	CREATE LOGIN personaje with password='a', 
	DEFAULT_DATABASE=YuseComponents 
	USE YuseComponents
	CREATE USER personaje FOR LOGIN personaje
	GRANT EXECUTE, SELECT, INSERT, UPDATE, DELETE
	TO personaje
	ALTER SERVER ROLE sysadmin --No deberia tener permisos de administrador pero es la unica forma de que realice la conexion
	ADD MEMBER personaje
END

--Procedimientos y Funciones

GO
--Nombre: InsertarUsuario
--Descripci?n: Inserta un nuevo usuario en la base de datos con su contrase?a ofuscada
--Entradas: nick de usuario y contrase?a, nombre y apellido opcinal
--Salidas: --
CREATE OR ALTER PROCEDURE InsertarUsuario 
				@NickUsuario varchar(30),
				@Contrasenha varchar(32),
				@Nombre varchar(30) = NULL,  -- NULL default value  
				@Apellido varchar(50) = NULL  -- NULL default value  
AS BEGIN
	DECLARE @NewId uniqueidentifier = NEWID() --ID generado que usaremos para el "salteo" de la contrase?a y para el ide del usuario
	DECLARE @Salteo nvarchar(6) = CAST(ABS(CHECKSUM(@NewId)) % 100000 AS nvarchar(6));

	INSERT INTO Usuarios (IDUsuario, NickUsuario, Contrasenha, Nombre, Apellido)
		VALUES (@NewId, @NickUsuario, HASHBYTES('SHA2_512',CONVERT(NVARCHAR(4000),@Contrasenha + @Salteo)), @Nombre, @Apellido)
END
GO

--Nombre: ComprobarUsuario
--Descripci?n: Comprueba si un usuario se encuentra registrado en la bbdd
--Entradas: nick de usuario y contrase?a
--Salidas: bit 1 si existe y 0 si no exisite
CREATE OR ALTER FUNCTION ComprobarUsuario (@NickUsuario varchar(30), @Contrasenha varchar(32)) RETURNS bit
AS BEGIN
	DECLARE @Registrado bit = 0
	SET @Registrado = CASE 
						WHEN (EXISTS (SELECT * FROM Usuarios 
									WHERE NickUsuario=@NickUsuario AND 
										Contrasenha =  HASHBYTES('SHA2_512',CONVERT(NVARCHAR(4000),@Contrasenha + CAST(ABS(CHECKSUM(IDUsuario)) % 100000 AS nvarchar(6))))))
							THEN 1 
							ELSE 0 
						END
	RETURN @Registrado
END
GO



begin transaction
EXECUTE InsertarUsuario josema, muestra
EXECUTE InsertarUsuario cels, ?amblarg, Carlota, 'bota bota'
UPDATE Usuarios 
SET Saldo = 1000, Descuento=0.30
WHERE NickUsuario='cels'
SELECT * FROM Usuarios

SELECT dbo.ComprobarUsuario('cels', '?amblarg')
SELECT dbo.ComprobarUsuario('cels', 'contrase?a')

