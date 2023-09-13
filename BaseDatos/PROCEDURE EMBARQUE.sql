CREATE PROCEDURE ActualizarEmbarqueDetalle
    @Codigo INT,
    @Pedido NVARCHAR(20),
    @Recibir INT,
    @Solicitada INT,
    @Fisica INT,
    @FechaRecibo NVARCHAR(10), -- Parámetro para la fecha
    @username NVARCHAR(50) -- Parámetro para el usuario
AS
BEGIN
    -- Verifica que la cantidad recibida sea mayor que cero y no exceda la cantidad solicitada ni la cantidad física
    IF @Recibir > 0 AND @Recibir <= @Solicitada AND @Recibir <= @Fisica
    BEGIN
        BEGIN TRY
            BEGIN TRANSACTION;

            -- Actualiza la cantidad a embarcar en EmbarqueDet
            UPDATE EmbarqueDet
            SET CantidadEmbarcar = CantidadEmbarcar + @Recibir,
                Estatus = CASE WHEN CantidadEmbarcar + @Recibir = CantidadSolicitada THEN 2 ELSE 1 END,
                fecha = @FechaRecibo -- Actualiza la fecha
            WHERE Codigo = @Codigo AND Pedido = @Pedido;

            -- Actualiza la cantidad en Inventario
            UPDATE Inventario
            SET Cantidad = Cantidad - @Recibir
            WHERE Codigo = @Codigo;

            -- Actualiza la cantidad física en EmbarqueDet
            UPDATE EmbarqueDet
            SET CantidadFisica = CantidadFisica - @Recibir
            WHERE Codigo = @Codigo;

            -- Actualiza el usuario en Embarque
            UPDATE Embarque
            SET UsuarioEmbarque = @username -- Actualiza el usuario
            WHERE Pedido = @Pedido;

            COMMIT;
        END TRY
        BEGIN CATCH
            ROLLBACK;
            -- Manejar errores si es necesario
        END CATCH;
    END
END;
