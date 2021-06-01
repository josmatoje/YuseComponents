USE master
GO
IF NOT EXISTS (SELECT * FROM master.dbo.SYSDATABASES WHERE NAME = 'YuseComponents') --Si no existe la base de datos
	CREATE DATABASE YuseComponents
ELSE
	DROP DATABASE YuseComponents

GO

CREATE TABLE Usuario(
	ID uniqueidentifier PRIMARY KEY,
	NickUsuario varchar(30),
	Nombre varchar(30),
	Apellido varchar(50)
)
GO

CREATE TABLE Componentes(
	ProductId uniqueidentifier PRIMARY KEY,
	Nombre varchar(50),
	Precio money,
	Cantidad int
)
GO

CREATE TABLE Listas(
	ProductId uniqueidentifier PRIMARY KEY,
	Nombre varchar(50),
	Prtecio money,
	Cantidad int
)
GO