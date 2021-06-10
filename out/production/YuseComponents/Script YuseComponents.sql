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
	NickUsuario varchar(30) NOT NULL,
	Contrasenha varbinary(MAX) NOT NULL,
	Nombre varchar(30) NULL,
	Apellido varchar(50) NULL,
	Saldo money NULL Default 0,
	Descuento decimal (3,2) NOT NULL DEFAULT 0,
	CONSTRAINT CK_Descuento Check (Descuento BETWEEN 0 AND 1)
)
GO

CREATE TABLE ListasCreadas(
	[Nº de lista] int IDENTITY(10000,1) CONSTRAINT PK_Lista Primary Key,
	Nombre varchar(20) NOT NULL,
	IDUsuario uniqueidentifier CONSTRAINT FK_CreaListaUsuaria Foreign Key REFERENCES Usuarios (IDUsuario),
	Publica bit NOT NULL Default 1,
	Privada AS CASE WHEN (Publica=1) THEN 0 ELSE 1 END
)
GO

CREATE TABLE Transacciones(
	IDTransaccion uniqueidentifier CONSTRAINT PK_Transaccion Primary Key,
	IDUsuario uniqueidentifier CONSTRAINT FK_TransaccionUsuario Foreign Key REFERENCES Usuarios (IDUsuario),
	[Nº de lista] int NOT NULL CONSTRAINT FK_ListaTransacciones Foreign Key REFERENCES ListasCreadas ([Nº de lista]),
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
	[Nº de lista] int NOT NULL CONSTRAINT FK_ListaComponente Foreign Key REFERENCES ListasCreadas ([Nº de lista]),
	IdComponente uniqueidentifier CONSTRAINT FK_ComponentesLista Foreign Key REFERENCES Componentes (IdComponente),
	CONSTRAINT PK_ListaComponente Primary Key ([Nº de lista], IdComponente)
)
GO

IF NOT EXISTS (SELECT name FROM [sys].[server_principals] WHERE name = 'pedrito')
BEGIN
--Creacion del usuario de la base de datos
	CREATE LOGIN pedrito with password='megustan LAS bases de datos', 
	DEFAULT_DATABASE=YuseComponents 
	
	CREATE USER pedrito FOR LOGIN pedrito 
	GRANT EXECUTE, SELECT --Solo se realizan procedimientos para todas las acciones que suceden en el programa
	--ON ListasCreadas.YuseComponents --Si GRANT INSERT, UPDATE, DELETE
	TO pedrito
END

--Procedimientos y Funciones

GO
--Nombre: InsertarUsuario
--Descripción: Inserta un nuevo usuario en la base de datos con su contraseña ofuscada
--Entradas: nick de usuario y contraseña, nombre y apellido opcinal
--Salidas:
CREATE OR ALTER PROCEDURE InsertarUsuario 
				@NickUsuario varchar(30),
				@Contrasenha varchar(32),
				@Nombre varchar(30) = NULL,  -- NULL default value  
				@Apellido varchar(50) = NULL  -- NULL default value  
AS BEGIN
	DECLARE @Salteo nvarchar(6) = CAST(ABS(CHECKSUM(NEWID())) % 100000 AS nvarchar(6));

	INSERT INTO Usuarios (IDUsuario, NickUsuario, Contrasenha, Nombre, Apellido)



END
GO
--Datos