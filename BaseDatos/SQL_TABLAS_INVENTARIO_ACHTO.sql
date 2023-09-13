CREATE DATABASE achto2
GO

CREATE TABLE [Usuarios] (
  [ID_usuario] INT PRIMARY KEY,
  [Usuario] VARCHAR(200),
  [Password] NVARCHAR(200),
  [Rol] NVARCHAR(20),
  [Email] NVARCHAR(30),
  [Nombre] NVARCHAR(50),
  [Apellidos] NVARCHAR(100),
  [Inactivo] SMALLINT
)
GO

CREATE TABLE [Inventario] (
  [Codigo] INT,
  [Descripcion] NVARCHAR(350),
  [Cantidad] INT,

)
GO

CREATE TABLE [Recibo] (
  [ID_recibo] INT,
  [Pedido] NVARCHAR(20),
  [Clave_Proveedor] INT,
  [Proveedor] NVARCHAR(200),
  [FechaPedido] DATETIME,
  [Departamento] NVARCHAR(50),
  [FechaRecibo] DATETIME,
  [UsuarioRecibo] VARCHAR(200),
  [Estatus] INT
)
GO

CREATE TABLE [ReciboDet] (
  [ID_recibodet] int PRIMARY KEY,
  [Pedido] NVARCHAR(20),
  [Codigo] INT,
  [Descripcion] NVARCHAR(350),
  [CantidadSolicitada] INT,
  [CantidadFisica] INT,
  [Estatus] INT,
  [Fecha] DATETIME,
  [Observaciones] NVARCHAR(200)
)
GO

CREATE TABLE [Embarque] (
  [ID_embarque] INT PRIMARY KEY,
  [Pedido] NVARCHAR(20),
  [Clave_Cliente] INT,
  [Cliente] NVARCHAR(200),
  [FechaPedido] DATETIME,
  [FechaEmbarque] DATETIME,
  [UsuarioEmarque] VARCHAR(200),
  [Estatus] INT
)
GO

CREATE TABLE [EmbarqueDet] (
  [ID_embarquedet] int PRIMARY KEY,
  [Pedido] NVARCHAR(20),
  [Codigo] INT,
  [Descripcion] NVARCHAR(350),
  [CantidadSolicitada] INT,
  [CantidadEmbarcar] INT,
  [CantidadFisica] INT,
  [Estatus] INT,
  [Fecha] DATETIME,
  [Observaciones] NVARCHAR(200)
)
GO
