CREATE PROCEDURE InsertarUsuario
    @NombreUsuario VARCHAR(50),
    @ContraseñaUsuario VARCHAR(50)
AS
BEGIN
    -- Verificar si el nombre de usuario ya existe
    IF EXISTS (SELECT 1 FROM Usuarios WHERE NombreUsuario = @NombreUsuario)
    BEGIN
        PRINT '⚠ Error: El nombre de usuario ya existe.';
        RETURN; -- Salir del procedimiento si el usuario ya existe
    END

    -- Insertar el nuevo usuario
    INSERT INTO Usuarios (NombreUsuario, ContraseñaUsuario)
    VALUES (@NombreUsuario, @ContraseñaUsuario);

    PRINT '✅ Usuario insertado correctamente.';
END;

CREATE PROCEDURE ModificarUsuario
    @IdUsuario INT,
    @NuevoNombreUsuario VARCHAR(50),
    @NuevaContraseñaUsuario VARCHAR(50),
    @ContraseñaActual VARCHAR(50) -- Contraseña actual para verificación
AS
BEGIN
    -- Verificar si la contraseña actual es correcta
    IF NOT EXISTS (SELECT 1 FROM Usuarios WHERE IdUsuario = @IdUsuario AND ContraseñaUsuario = @ContraseñaActual)
    BEGIN
        PRINT '⚠ Error: La contraseña actual es incorrecta.';
        RETURN; -- Salir del procedimiento si la contraseña no coincide
    END

    -- Verificar si el nuevo nombre de usuario ya existe (excepto para el mismo usuario)
    IF EXISTS (SELECT 1 FROM Usuarios WHERE NombreUsuario = @NuevoNombreUsuario AND IdUsuario <> @IdUsuario)
    BEGIN
        PRINT '⚠ Error: El nuevo nombre de usuario ya está en uso.';
        RETURN; -- Salir del procedimiento si el nombre de usuario ya existe
    END

    -- Actualizar el nombre de usuario y la contraseña
    UPDATE Usuarios
    SET NombreUsuario = @NuevoNombreUsuario,
        ContraseñaUsuario = @NuevaContraseñaUsuario
    WHERE IdUsuario = @IdUsuario;

    PRINT '✅ Usuario modificado correctamente.';
END;

CREATE PROCEDURE EliminarUsuario
    @IdUsuario INT,
    @ContraseñaActual VARCHAR(50) -- Contraseña actual para verificación
AS
BEGIN
    -- Verificar si la contraseña actual es correcta
    IF NOT EXISTS (SELECT 1 FROM Usuarios WHERE IdUsuario = @IdUsuario AND ContraseñaUsuario = @ContraseñaActual)
    BEGIN
        PRINT '⚠ Error: La contraseña actual es incorrecta.';
        RETURN; -- Salir del procedimiento si la contraseña no coincide
    END

    -- Eliminar el usuario
    DELETE FROM Usuarios
    WHERE IdUsuario = @IdUsuario;

    PRINT '✅ Usuario eliminado correctamente.';
END;

select * from usuarios;

SELECT * FROM Usuarios WHERE NombreUsuario = ? AND ContraseñaUsuario = ?;