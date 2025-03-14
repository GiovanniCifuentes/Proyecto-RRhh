package controlador;

public class Empleado {
    int id_empleado;
    String nombreEmpleado;
    String direEmpleado;

    public Empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public Empleado() {
    }

    public Empleado(String nombreEmpleado, String direEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
        this.direEmpleado = direEmpleado;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getDireEmpleado() {
        return direEmpleado;
    }

    public void setDireEmpleado(String direEmpleado) {
        this.direEmpleado = direEmpleado;
    }

    @Override
    public String toString() {
        return "Empleado{" + "id_empleado=" + id_empleado + ", nombreEmpleado=" + nombreEmpleado + ", direEmpleado=" + direEmpleado + '}';
    }
    

}