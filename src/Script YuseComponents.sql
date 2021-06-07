USE master
GO
IF NOT EXISTS (SELECT * FROM master.dbo.SYSDATABASES WHERE NAME = 'YuseComponents') --Si no existe la base de datos
	CREATE DATABASE YuseComponents
ELSE
	DROP DATABASE YuseComponents
GO

USE YuseComponents
GO

CREATE TABLE Usuarios(
	IDUsuario uniqueidentifier CONSTRAINT PK_Usuarios Primary Key,
	NickUsuario varchar(30) NOT NULL,
	Nombre varchar(30) NOT NULL,
	Apellido varchar(50) NULL,
	Saldo money NOT NULL Default 0,
	Descuento decimal (3,2) NULL CONSTRAINT CK_Descuento Check (Descuento BETWEEN -1 AND 1)
)
GO

CREATE TABLE ListasCreadas(
	[Nº de lista] int IDENTITY(10000,1) CONSTRAINT PK_Usuarios Primary Key,
	Nombre varchar(20) NOT NULL,
	IDUsuario uniqueidentifier CONSTRAINT FK_CreaListaUsuaria Foreign Key REFERENCES Usuarios (IDUsuario),
	Publica char(2) NOT NULL Default 'SI',--Igual BIT es mejor
	CONSTRAINT CK_PublicaSi Check (Publica LIKE 'SI'),
	CONSTRAINT CK_PublicaNo Check (Publica LIKE 'NO'),
	Privada AS CASE WHEN (Publica='SI') THEN 'NO' WHEN (Publica='NO') THEN 'SI' END
)
GO

CREATE TABLE Transacciones(
	IDTransaccion uniqueidentifier CONSTRAINT PK_Transaccion Primary Key,
	IDUsuario uniqueidentifier CONSTRAINT FK_TransaccionUsuario Foreign Key REFERENCES Usuarios (IDUsuario),
	[Fecha y hora] datetime2 NOT NULL Default CURRENT_TIMESTAMP,
	[Total cobrado] money NULL Default NULL
)
GO

CREATE TABLE TipoComponente(
	Nombre varchar(20) CONSTRAINT PK_TipoComponente Primary Key
)
GO

CREATE TABLE Componentes(
	ProductId uniqueidentifier CONSTRAINT PK_Componentes Primary Key,
	TipoComponente varchar(20) CONSTRAINT FK_TipoComponente Foreign Key REFERENCES TipoComponente (Nombre),
	Nombre varchar(50) NOT NULL,
	Precio money NOT NULL,
	Cantidad int NULL
)
GO

CREATE TABLE Propiedades(
	Propiedad varchar(20) CONSTRAINT PK_TipoComponente Primary Key,
	Valor varchar(30) NULL
)
GO

CREATE TABLE TransaccionListaComponente(
	[Nº de lista] int IDENTITY(10000,1) CONSTRAINT PK_Usuarios Primary Key,
	Nombre varchar(20) NOT NULL,
	Creador uniqueidentifier CONSTRAINT FK_CreaListaUsuaria Foreign Key REFERENCES Usuarios

)
GO


--Creacion del usuario de la base de datos
CREATE LOGIN pedrito with password='megustan LAS bases de datos', 
DEFAULT_DATABASE=YuseComponents 

CREATE USER pedrito FOR LOGIN pedrito 
GRANT EXECUTE, SELECT --Solo se realizan procedimientos para todas las acciones que suceden en el programa
--ON ListasCreadas.YuseComponents --Si GRANT INSERT, UPDATE, DELETE
TO pedrito
