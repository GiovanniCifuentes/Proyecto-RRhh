-- 1. Tablas de Catálogos o Maestros
CREATE TABLE Roles (
    IdRol INT PRIMARY KEY IDENTITY,
    NombreRol VARCHAR(50) NOT NULL
);

CREATE TABLE Usuarios (
    IdUsuario INT PRIMARY KEY IDENTITY,
    NombreUsuario VARCHAR(50) NOT NULL,
	ContraseñaUsuario VARCHAR(50) NOT NULL
);

INSERT INTO Roles (NombreRol) VALUES 
('Administrador'),
('Recursos Humanos'),
('Contador'),
('Desarrollador'),
('Gerente'),
('Asistente'),
('Seguridad'),
('Mantenimiento');

select * from roles;

CREATE TABLE TiposNomina (
    IdTipoNomina INT PRIMARY KEY IDENTITY,
    Descripcion VARCHAR(50) NOT NULL
);

CREATE TABLE MetodosPago (
    IdMetodoPago INT PRIMARY KEY IDENTITY,
    Metodo VARCHAR(50) NOT NULL
);

-- 2. Tabla de Empleados
CREATE TABLE Empleados (
    IdEmpleado INT PRIMARY KEY IDENTITY,
    Nombre VARCHAR(100) NOT NULL,
    Apellido VARCHAR(100) NOT NULL,
    DPI VARCHAR(15) UNIQUE NOT NULL,
    FechaIngreso DATE NOT NULL,
    SalarioBase DECIMAL(10, 2) NOT NULL,
    IdRol INT FOREIGN KEY REFERENCES Roles(IdRol),
    Estado VARCHAR(20) DEFAULT 'Activo'
);

-- 3. Tabla de Nómina
CREATE TABLE Nomina (
    IdNomina INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    IdTipoNomina INT FOREIGN KEY REFERENCES TiposNomina(IdTipoNomina),
    FechaPago DATE NOT NULL,
    Salario DECIMAL(10, 2) NOT NULL,
    HorasExtras INT,
    Bonificaciones DECIMAL(10, 2),
    Deducciones DECIMAL(10, 2),
    TotalPagar AS (Salario + ISNULL(Bonificaciones, 0) - ISNULL(Deducciones, 0)) PERSISTED
);

-- 4. Tabla de Pagos
CREATE TABLE Pagos (
    IdPago INT PRIMARY KEY IDENTITY,
    IdNomina INT FOREIGN KEY REFERENCES Nomina(IdNomina),
    IdMetodoPago INT FOREIGN KEY REFERENCES MetodosPago(IdMetodoPago),
    FechaPago DATE NOT NULL,
    Monto DECIMAL(10, 2) NOT NULL
);

-- 5. Tabla de Prestaciones
CREATE TABLE Prestaciones (
    IdPrestacion INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    TipoPrestacion VARCHAR(50),
    Monto DECIMAL(10, 2),
    FechaOtorgada DATE
);

-- 6. Tabla de Evaluaciones
CREATE TABLE Evaluaciones (
    IdEvaluacion INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    FechaEvaluacion DATE NOT NULL,
    Puntuacion INT,
    Comentarios VARCHAR(255)
);

-- 7. Tabla de Documentos
CREATE TABLE Documentos (
    IdDocumento INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    NombreDocumento VARCHAR(100),
    FechaCarga DATE,
    RutaArchivo VARCHAR(255)
);

-- 8. Tabla de Capacitaciones
CREATE TABLE Capacitaciones (
    IdCapacitacion INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    Curso VARCHAR(100),
    FechaInicio DATE,
    FechaFin DATE,
    Estado VARCHAR(50)
);

-- 9. Tabla de Reportes
CREATE TABLE Reportes (
    IdReporte INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    TipoReporte VARCHAR(100),
    FechaGeneracion DATE,
    Descripcion VARCHAR(255)
);

-- 10. Historial Laboral
CREATE TABLE HistorialLaboral (
    IdHistorial INT PRIMARY KEY IDENTITY,
    IdEmpleado INT FOREIGN KEY REFERENCES Empleados(IdEmpleado),
    Cambio VARCHAR(255),
    FechaCambio DATE
);

-- Procedimiento para Alta de Empleados
CREATE PROCEDURE AltaEmpleado 
    @Nombre VARCHAR(100),
    @Apellido VARCHAR(100),
    @DPI VARCHAR(15),
    @FechaIngreso DATE,
    @SalarioBase DECIMAL(10, 2),
    @IdRol INT
AS
BEGIN
    INSERT INTO Empleados (Nombre, Apellido, DPI, FechaIngreso, SalarioBase, IdRol)
    VALUES (@Nombre, @Apellido, @DPI, @FechaIngreso, @SalarioBase, @IdRol);

    DECLARE @IdEmpleado INT = SCOPE_IDENTITY();

    INSERT INTO HistorialLaboral (IdEmpleado, Cambio, FechaCambio)
    VALUES (@IdEmpleado, 'Alta de empleado', GETDATE());
END;

-- Procedimiento para Baja de Empleados
CREATE PROCEDURE BajaEmpleado 
    @IdEmpleado INT
AS
BEGIN
    UPDATE Empleados
    SET Estado = 'Inactivo'
    WHERE IdEmpleado = @IdEmpleado;

    INSERT INTO HistorialLaboral (IdEmpleado, Cambio, FechaCambio)
    VALUES (@IdEmpleado, 'Baja de empleado', GETDATE());
END;

-- Trigger para Registrar Cambios en el Historial Laboral
CREATE TRIGGER trg_ActualizarHistorial
ON Empleados
AFTER UPDATE
AS
BEGIN	
    IF UPDATE(Estado)
    BEGIN
        INSERT INTO HistorialLaboral (IdEmpleado, Cambio, FechaCambio)
        SELECT IdEmpleado, 'Cambio de estado', GETDATE()
        FROM inserted;
    END;
END;

select * from Empleados;