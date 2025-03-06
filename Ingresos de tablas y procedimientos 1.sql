select * from Empleados;
EXEC AltaEmpleado 
    @Nombre = 'Juan',
    @Apellido = 'Perez',
    @DPI = '1234567890101',
    @FechaIngreso = '2025-03-06',
    @SalarioBase = 5500.00,
    @IdRol = 4;
