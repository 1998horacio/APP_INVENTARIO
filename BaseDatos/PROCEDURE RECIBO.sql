   CREATE PROCEDURE ActualizarReciboDet
   (
       @Descripcion NVARCHAR(350),
       @Pedido NVARCHAR(20),
       @Recibir INT,
       @Codigo INT,
       @Fisica INT,
       @Solicitada INT,
       @FechaRecibo NVARCHAR(10), -- Parámetro para la fecha
       @User NVARCHAR(50) -- Parámetro para el usuario
   )
   AS
   BEGIN
       -- Actualiza ReciboDet
       UPDATE ReciboDet
       SET CantidadFisica = CantidadFisica + @Recibir,
           Estatus = CASE WHEN CantidadFisica + @Recibir = @Solicitada THEN 2 ELSE 1 END,
	   fecha = @FechaRecibo -- Actualiza la fecha
       WHERE Codigo = @Codigo AND Pedido = @Pedido;

       -- Actualiza EmbarqueDet (si es necesario)
       UPDATE EmbarqueDet
       SET CantidadFisica = CantidadFisica + @Recibir
       WHERE Codigo = @Codigo;

       -- Actualiza el usuario en Recibo
       UPDATE Recibo
       SET UsuarioRecibo = @User -- Actualiza el usuario
       WHERE Pedido = @Pedido;

       -- Verifica si el artículo existe en Inventario
       IF EXISTS (SELECT * FROM Inventario WHERE Codigo = @Codigo)
       BEGIN
           -- Si existe, actualiza la cantidad
           UPDATE Inventario
           SET Cantidad = Cantidad + @Recibir
           WHERE Codigo = @Codigo;
       END
       ELSE
       BEGIN
           -- Si no existe, inserta un nuevo registro
           INSERT INTO Inventario (Codigo, Descripcion, Cantidad)
           VALUES (@Codigo, @Descripcion, @Recibir);
       END
   END
  