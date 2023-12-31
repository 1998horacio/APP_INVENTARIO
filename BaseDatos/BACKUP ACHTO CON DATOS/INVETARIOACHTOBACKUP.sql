USE [master]
GO
/****** Object:  Database [achto]    Script Date: 07/09/2023 03:18:42 p. m. ******/
CREATE DATABASE [achto]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'achto', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\achto.mdf' , SIZE = 4288KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'achto_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\achto_log.ldf' , SIZE = 1072KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [achto] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [achto].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [achto] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [achto] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [achto] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [achto] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [achto] SET ARITHABORT OFF 
GO
ALTER DATABASE [achto] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [achto] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [achto] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [achto] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [achto] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [achto] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [achto] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [achto] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [achto] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [achto] SET  ENABLE_BROKER 
GO
ALTER DATABASE [achto] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [achto] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [achto] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [achto] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [achto] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [achto] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [achto] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [achto] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [achto] SET  MULTI_USER 
GO
ALTER DATABASE [achto] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [achto] SET DB_CHAINING OFF 
GO
ALTER DATABASE [achto] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [achto] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [achto] SET DELAYED_DURABILITY = DISABLED 
GO
USE [achto]
GO
/****** Object:  Table [dbo].[Embarque]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Embarque](
	[ID_embarque] [int] IDENTITY(1,1) NOT NULL,
	[Pedido] [varchar](200) NOT NULL,
	[Clave_Cliente] [int] NULL,
	[Cliente] [varchar](200) NULL,
	[FechaPedido] [datetime] NULL,
	[FechaEmbarque] [datetime] NULL,
	[UsuarioEmbarque] [varchar](200) NULL,
	[Estatus] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_embarque] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EmbarqueDet]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[EmbarqueDet](
	[ID_embarquedet] [int] NOT NULL,
	[Pedido] [nvarchar](20) NULL,
	[Codigo] [int] NULL,
	[Descripcion] [nvarchar](350) NULL,
	[CantidadSolicitada] [int] NULL,
	[CantidadEmbarcar] [int] NULL,
	[Estatus] [int] NULL,
	[Fecha] [datetime] NULL,
	[Observaciones] [nvarchar](200) NULL,
	[CantidadFisica] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_embarquedet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Inventario]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Inventario](
	[Codigo] [int] NULL,
	[Descripcion] [nvarchar](350) NULL,
	[Cantidad] [int] NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Recibo]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Recibo](
	[ID_recibo] [int] NULL,
	[Pedido] [nvarchar](20) NOT NULL,
	[Clave_Proveedor] [int] NULL,
	[Proveedor] [nvarchar](200) NULL,
	[FechaPedido] [datetime] NULL,
	[Departamento] [nvarchar](50) NULL,
	[FechaRecibo] [datetime] NULL,
	[UsuarioRecibo] [varchar](200) NULL,
	[Estatus] [int] NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ReciboDet]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ReciboDet](
	[ID_recibodet] [int] NOT NULL,
	[Pedido] [nvarchar](20) NULL,
	[Codigo] [int] NULL,
	[Descripcion] [nvarchar](350) NULL,
	[CantidadSolicitada] [int] NULL,
	[CantidadFisica] [int] NULL,
	[Estatus] [int] NULL,
	[Fecha] [datetime] NULL,
	[Observaciones] [nvarchar](200) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_recibodet] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Usuarios]    Script Date: 07/09/2023 03:18:42 p. m. ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Usuarios](
	[ID_usuario] [int] NOT NULL,
	[Usuario] [varchar](200) NULL,
	[Password] [nvarchar](200) NULL,
	[Rol] [nvarchar](20) NULL,
	[Email] [nvarchar](30) NULL,
	[Nombre] [nvarchar](50) NULL,
	[Apellidos] [nvarchar](100) NULL,
PRIMARY KEY CLUSTERED 
(
	[ID_usuario] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Embarque] ON 

INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (8, N'AS7988', 2000, N'OXXO S.A DE C.V', CAST(N'2019-12-01 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (10, N'AS8046', 2010, N'HOME DEPOT S.A DE C.V', CAST(N'2020-08-01 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (11, N'AS9961', 19978, N'CASA LEY S.A DE C.V', CAST(N'2023-09-01 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (12, N'AS9963', 19978, N'CASA LEY S.A DE C.V', CAST(N'2021-11-01 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (13, N'AS9965', 19978, N'CASA LEY S.A DE C.V', CAST(N'2021-11-01 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (14, N'AS9962', 221456, N'SORIANA S.A DE C.V', CAST(N'2017-11-21 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (15, N'AS9964', 221456, N'SORIANA S.A DE C.V', CAST(N'2017-11-21 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (16, N'AS9937', 2000, N'OXXO S.A DE C.V', CAST(N'2019-11-21 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (17, N'AS9916', 2010, N'HOME DEPOT S.A DE C.V', CAST(N'2019-11-21 00:00:00.000' AS DateTime), NULL, NULL, 0)
INSERT [dbo].[Embarque] ([ID_embarque], [Pedido], [Clave_Cliente], [Cliente], [FechaPedido], [FechaEmbarque], [UsuarioEmbarque], [Estatus]) VALUES (18, N'AS9918', 2010, N'HOME DEPOT S.A DE C.V', CAST(N'2019-11-21 00:00:00.000' AS DateTime), NULL, NULL, 0)
SET IDENTITY_INSERT [dbo].[Embarque] OFF
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (1, N'AS7988', 408485, N'MC93 SINGLE SLOT USB/CHARGE CRADLE W/SPARE BTRY CHARGER [CRD-MC93-2SUCHG-01]', 2, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (2, N'AS7988', 408397, N'CABLE DC ASSEMBLY POWER 12VDC 4.16A [CBL-DC-388A1-01]', 2, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (3, N'AS7988', 408341, N'FUENTE DE PODER 100-240V [PWR-BGA12V50W0WW] ', 2, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (4, N'AS7988', 408101, N'Cable para corriente alterna US [23844-00-00R]', 2, 0, 0, NULL, NULL, 2)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (5, N'AS8046', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 6, 0, 0, NULL, NULL, 36)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (6, N'AS8046', 408371, N'CABLE STANDARD TYPE - SNAP-ON CABLE - USB - RUGGED CHARGE AND COMMUNICATION [CBL-TC51-USB1-01]', 6, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (7, N'AS9961', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6', 20, 9, 1, NULL, NULL, 36)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (8, N'AS9961', 701246, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA TC52XX [Z1AE-TC52XX-5C00]', 25, 5, 1, NULL, NULL, 85)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (9, N'AS9961', 408602, N'Bluetooth Beacon TC52, TC52x, TC52ax Battery, 4150 mAh [BTRY-TC5X-BTBCN4HC-01]', 15, 0, 0, NULL, NULL, 58)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (10, N'AS9961', 408412, N'Cuna de carga para 4 baterías [SAC-TC51-4SCHG-01]', 1, 0, 0, NULL, NULL, 5)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (11, N'AS9961', 408101, N'Cable para corriente alterna US [23844-00-00R]', 1, 0, 0, NULL, NULL, 2)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (12, N'AS9961', 408445, N'TC5x EXOSKELETON, WITH HAND STRAP [SG-TC5X-EXO1-01]', 15, 0, 0, NULL, NULL, 45)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (13, N'AS9961', 408603, N'TC5X Tempered Glass Screen Protector PIEZA', 15, 0, 0, NULL, NULL, 45)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (14, N'AS9963', 400106, N'SCANNER ZEBRA DS457 Fixed Mount 2D Imager, SE-4500, Standard Optics, IP54 Sealed, Black [DS457-SR20004ZZWW]', 2, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (15, N'AS9963', 408305, N'USB Cable Assembly: 9-Pin Female Straight Scanner Connector, 6ft [CBL-58926-04]', 2, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (16, N'AS9965', 400105, N'SCANNER ZEBRA DS8178 Black USB KIT [DS8178-SR7U2100PFW]', 18, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (17, N'AS9965', 701196, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare Essential DS8178 Comprehensive coverage includes cradle [Z1AE-DS8178-3C00]', 18, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (18, N'AS9962', 400037, N'Scanner Motorola LI4278 [K] incluye cable usb y cuna [LI4278-TRBU0100ZLR]', 7, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (19, N'AS9962', 701228, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare, Essential para LI4278 [Z1AE-LI4278-3C00]', 7, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (20, N'AS9964', 401181, N'CONCIERGE ZEBRA CC6000 10IN, ANDROID OS, 32GB, LANDSCAPE, IMAGER, WORLDWIDE [CC6000-10-3200LCWW]', 4, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (21, N'AS9964', 408008, N'Cable universal entrada US de conexión a 110 VAC [50-16000-182R]', 4, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (22, N'AS9964', 408340, N'CABLE DC PARA FUENTE DE PODER UNIVERSAL [CBL-DC-383A1-01]', 4, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (23, N'AS9964', 408339, N'FUENTE DE PODER P/CABLE DE CARGA [PWR-BUA5V16W0WW]', 4, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (24, N'AS9964', 701267, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA CC6000 [Z1AE-CC6000-5C00]', 4, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (25, N'AS9937', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 45, 45, 2, NULL, NULL, 36)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (26, N'AS9937', 701246, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA TC52XX [Z1AE-TC52XX-5C00]', 45, 0, 0, NULL, NULL, 85)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (27, N'AS9937', 408602, N'Bluetooth Beacon TC52, TC52x, TC52ax Battery, 4150 mAh [BTRY-TC5X-BTBCN4HC-01]', 45, 0, 0, NULL, NULL, 58)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (28, N'AS9937', 408412, N'Cuna de carga para 4 baterías [SAC-TC51-4SCHG-01]', 5, 0, 0, NULL, NULL, 5)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (29, N'AS9937', 408101, N'Cable para corriente alterna US [23844-00-00R]', 5, 0, 0, NULL, NULL, 2)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (30, N'AS9937', 408445, N'TC5x EXOSKELETON, WITH HAND STRAP [SG-TC5X-EXO1-01]', 45, 0, 0, NULL, NULL, 45)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (31, N'AS9937', 408603, N'TC5X Tempered Glass Screen Protector PIEZA', 45, 0, 0, NULL, NULL, 45)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (32, N'AS9916', 400105, N'SCANNER ZEBRA DS8178 Black USB KIT [DS8178-SR7U2100PFW]', 18, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (33, N'AS9916', 701196, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare Essential DS8178 Comprehensive coverage includes cradle [Z1AE-DS8178-3C00]', 18, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (34, N'AS9918', 400037, N'Scanner Motorola LI4278 [K] incluye cable usb y cuna [LI4278-TRBU0100ZLR]', 7, 0, 0, NULL, NULL, 0)
INSERT [dbo].[EmbarqueDet] ([ID_embarquedet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadEmbarcar], [Estatus], [Fecha], [Observaciones], [CantidadFisica]) VALUES (35, N'AS9918', 701228, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare, Essential para LI4278 [Z1AE-LI4278-3C00]', 7, 0, 0, NULL, NULL, 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 36)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (701246, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA TC52XX [Z1AE-TC52XX-5C00]', 85)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408602, N'Bluetooth Beacon TC52, TC52x, TC52ax Battery, 4150 mAh [BTRY-TC5X-BTBCN4HC-01]', 58)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408412, N'Cuna de carga para 4 baterías [SAC-TC51-4SCHG-01]', 5)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408101, N'Cable para corriente alterna US [23844-00-00R]', 2)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408445, N'TC5x EXOSKELETON, WITH HAND STRAP [SG-TC5X-EXO1-01]', 45)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408603, N'TC5X Tempered Glass Screen Protector PIEZA', 45)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (400037, N'Scanner Motorola LI4278 [K] incluye cable usb y cuna [LI4278-TRBU0100ZLR]', 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (701228, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare, Essential para LI4278 [Z1AE-LI4278-3C00]', 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (400105, N'SCANNER ZEBRA DS8178 Black USB KIT [DS8178-SR7U2100PFW]', 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (701196, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare Essential DS8178 Comprehensive coverage includes cradle [Z1AE-DS8178-3C00]', 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (408305, N'USB Cable Assembly: 9-Pin Female Straight Scanner Connector, 6ft [CBL-58926-04]', 0)
INSERT [dbo].[Inventario] ([Codigo], [Descripcion], [Cantidad]) VALUES (400106, N'SCANNER ZEBRA DS457 Fixed Mount 2D Imager, SE-4500, Standard Optics, IP54 Sealed, Black   [DS457-SR20004ZZWW]', 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS7988', 10001, N'ZEBRA S.A DE C.V', CAST(N'2020-08-01 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS8046', 10001, N'ZEBRA S.A DE C.V', CAST(N'2020-08-01 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9916', 11021, N'OTRO PROVEEDOR S.A DE C.V', CAST(N'2020-07-13 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9918', 11021, N'PROVEEDOR S.A DE C.V', CAST(N'2020-07-13 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9937', 11021, N'OTRO PROVEEDOR S.A DE C.V', CAST(N'2020-07-13 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9961', 10001, N'ZEBRA S.A DE C.V', CAST(N'2020-08-10 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9962', 11021, N'OTRO PROVEEDOR S.A DE C.V', CAST(N'2020-08-11 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9963', 10001, N'ZEBRA S.A DE C.V', CAST(N'2020-08-10 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9965', 11021, N'OTRO PROVEEDOR S.A DE C.V', CAST(N'2020-08-11 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[Recibo] ([ID_recibo], [Pedido], [Clave_Proveedor], [Proveedor], [FechaPedido], [Departamento], [FechaRecibo], [UsuarioRecibo], [Estatus]) VALUES (NULL, N'AS9964', 24000, N'PROVEEDOR S.A DE C.V', CAST(N'2009-05-12 00:00:00.000' AS DateTime), NULL, NULL, NULL, 0)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (1, N'AS7988', 408485, N'MC93 SINGLE SLOT USB/CHARGE CRADLE W/SPARE BTRY CHARGER [CRD-MC93-2SUCHG-01]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (2, N'AS7988', 408397, N'CABLE DC ASSEMBLY POWER 12VDC 4.16A [CBL-DC-388A1-01]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (3, N'AS7988', 408341, N'FUENTE DE PODER 100-240V [PWR-BGA12V50W0WW]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (4, N'AS7988', 408101, N'Cable para corriente alterna US [23844-00-00R]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (5, N'AS8046', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 6, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (6, N'AS8046', 408371, N'CABLE STANDARD TYPE - SNAP-ON CABLE - USB - RUGGED CHARGE AND COMMUNICATION [CBL-TC51-USB1-01]', 6, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (7, N'AS9961', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (8, N'AS9961', 701246, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA TC52XX [Z1AE-TC52XX-5C00]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (9, N'AS9961', 408602, N'Bluetooth Beacon TC52, TC52x, TC52ax Battery, 4150 mAh [BTRY-TC5X-BTBCN4HC-01]', 45, 13, 1, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (10, N'AS9961', 408412, N'Cuna de carga para 4 baterías [SAC-TC51-4SCHG-01]', 5, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (11, N'AS9961', 408101, N'Cable para corriente alterna US [23844-00-00R]', 5, 2, 1, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (12, N'AS9961', 408445, N'TC5x EXOSKELETON, WITH HAND STRAP [SG-TC5X-EXO1-01]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (13, N'AS9961', 408603, N'TC5X Tempered Glass Screen Protector PIEZA', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (14, N'AS9963', 400106, N'SCANNER ZEBRA DS457 Fixed Mount 2D Imager, SE-4500, Standard Optics, IP54 Sealed, Black   [DS457-SR20004ZZWW]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (15, N'AS9963', 408305, N'USB Cable Assembly: 9-Pin Female Straight Scanner Connector, 6ft [CBL-58926-04]', 2, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (16, N'AS9965', 400105, N'SCANNER ZEBRA DS8178 Black USB KIT [DS8178-SR7U2100PFW]', 18, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (17, N'AS9965', 701196, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare Essential DS8178 Comprehensive coverage includes cradle [Z1AE-DS8178-3C00]', 18, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (18, N'AS9962', 400037, N'Scanner Motorola LI4278 [K] incluye cable usb y cuna [LI4278-TRBU0100ZLR]', 7, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (19, N'AS9962', 701228, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare, Essential para LI4278 [Z1AE-LI4278-3C00]', 7, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (20, N'AS9964', 401181, N'CONCIERGE ZEBRA CC6000 10IN, ANDROID OS, 32GB, LANDSCAPE, IMAGER, WORLDWIDE [CC6000-10-3200LCWW]', 4, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (21, N'AS9964', 408008, N'Cable universal entrada US de conexión a 110 VAC [50-16000-182R]', 4, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (22, N'AS9964', 408340, N'CABLE DC PARA FUENTE DE PODER UNIVERSAL [CBL-DC-383A1-01]', 4, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (23, N'AS9964', 408339, N'FUENTE DE PODER P/CABLE DE CARGA [PWR-BUA5V16W0WW]', 4, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (24, N'AS9964', 701267, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA CC6000 [Z1AE-CC6000-5C00]', 4, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (25, N'AS9937', 401226, N'TOUCH COMPUTER ZEBRA TC52ax Wi-Fi 6, SE4720 IMAGER, 4/64 GB, GMS, ROW [TC520L-1YFMU7P-A6]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (26, N'AS9937', 701246, N'POLIZA DE SERVICIOS TECNICOS ZEBRA ONECARE ESSENTIAL COMPREHENSIVE COVERAGE POR 5 AÑOS PARA TC52XX [Z1AE-TC52XX-5C00]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (27, N'AS9937', 408602, N'Bluetooth Beacon TC52, TC52x, TC52ax Battery, 4150 mAh [BTRY-TC5X-BTBCN4HC-01]', 45, 45, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (28, N'AS9937', 408412, N'Cuna de carga para 4 baterías [SAC-TC51-4SCHG-01]', 5, 5, 2, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (29, N'AS9937', 408101, N'Cable para corriente alterna US [23844-00-00R]', 5, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (30, N'AS9937', 408445, N'TC5x EXOSKELETON, WITH HAND STRAP [SG-TC5X-EXO1-01]', 45, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (31, N'AS9937', 408603, N'TC5X Tempered Glass Screen Protector PIEZA', 45, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (32, N'AS9916', 400105, N'SCANNER ZEBRA DS8178 Black USB KIT [DS8178-SR7U2100PFW]', 18, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (33, N'AS9916', 701196, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare Essential DS8178 Comprehensive coverage includes cradle [Z1AE-DS8178-3C00]', 18, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (34, N'AS9918', 400037, N'Scanner Motorola LI4278 [K] incluye cable usb y cuna [LI4278-TRBU0100ZLR]', 7, 0, 0, NULL, NULL)
INSERT [dbo].[ReciboDet] ([ID_recibodet], [Pedido], [Codigo], [Descripcion], [CantidadSolicitada], [CantidadFisica], [Estatus], [Fecha], [Observaciones]) VALUES (35, N'AS9918', 701228, N'POLIZA DE SERVICIOS TECNICOS POR 3 AÑOS Zebra OneCare, Essential para LI4278 [Z1AE-LI4278-3C00]', 7, 0, 0, NULL, NULL)
INSERT [dbo].[Usuarios] ([ID_usuario], [Usuario], [Password], [Rol], [Email], [Nombre], [Apellidos]) VALUES (1, N'a', N'a', NULL, NULL, NULL, NULL)
INSERT [dbo].[Usuarios] ([ID_usuario], [Usuario], [Password], [Rol], [Email], [Nombre], [Apellidos]) VALUES (2, N'b', N'b', NULL, NULL, NULL, NULL)
INSERT [dbo].[Usuarios] ([ID_usuario], [Usuario], [Password], [Rol], [Email], [Nombre], [Apellidos]) VALUES (3, N'Horacio', N'achto213', NULL, NULL, N'Horacio', N'Garcia')
USE [master]
GO
ALTER DATABASE [achto] SET  READ_WRITE 
GO
