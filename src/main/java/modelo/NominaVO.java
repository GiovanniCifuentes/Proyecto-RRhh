package modelo;

import java.util.Date;

public class NominaVO {
    // Atributos
    private int idNomina;
    private int idEmpleado;
    private Date fechaPago;
    private double salario;
    private Integer horasExtras;
    private Double comisiones;
    private Double bonificaciones;
    private Double valorHorasExtras;
    private Double totalDevengado;
    private Double igss;
    private Double isr;
    private Double anticipos;
    private Double judiciales;
    private Double prestamos;
    private Double deducciones;
    private Double totalPagar;

    // Constructor vacío
    public NominaVO() {}

    // Constructor con parámetros
    public NominaVO(int idNomina, int idEmpleado, Date fechaPago, double salario, Integer horasExtras, Double comisiones,
                    Double bonificaciones, Double valorHorasExtras, Double totalDevengado, Double igss, Double isr,
                    Double anticipos, Double judiciales, Double prestamos, Double deducciones, Double totalPagar) {
        this.idNomina = idNomina;
        this.idEmpleado = idEmpleado;
        this.fechaPago = fechaPago;
        this.salario = salario;
        this.horasExtras = horasExtras;
        this.comisiones = comisiones;
        this.bonificaciones = bonificaciones;
        this.valorHorasExtras = valorHorasExtras;
        this.totalDevengado = totalDevengado;
        this.igss = igss;
        this.isr = isr;
        this.anticipos = anticipos;
        this.judiciales = judiciales;
        this.prestamos = prestamos;
        this.deducciones = deducciones;
        this.totalPagar = totalPagar;
    }

    // Métodos get y set
    public int getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(int idNomina) {
        this.idNomina = idNomina;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Integer getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Integer horasExtras) {
        this.horasExtras = horasExtras;
    }

    public Double getComisiones() {
        return comisiones;
    }

    public void setComisiones(Double comisiones) {
        this.comisiones = comisiones;
    }

    public Double getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(Double bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    public Double getValorHorasExtras() {
        return valorHorasExtras;
    }

    public void setValorHorasExtras(Double valorHorasExtras) {
        this.valorHorasExtras = valorHorasExtras;
    }

    public Double getTotalDevengado() {
        return totalDevengado;
    }

    public void setTotalDevengado(Double totalDevengado) {
        this.totalDevengado = totalDevengado;
    }

    public Double getIgss() {
        return igss;
    }

    public void setIgss(Double igss) {
        this.igss = igss;
    }

    public Double getIsr() {
        return isr;
    }

    public void setIsr(Double isr) {
        this.isr = isr;
    }

    public Double getAnticipos() {
        return anticipos;
    }

    public void setAnticipos(Double anticipos) {
        this.anticipos = anticipos;
    }

    public Double getJudiciales() {
        return judiciales;
    }

    public void setJudiciales(Double judiciales) {
        this.judiciales = judiciales;
    }

    public Double getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Double prestamos) {
        this.prestamos = prestamos;
    }

    public Double getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(Double deducciones) {
        this.deducciones = deducciones;
    }

    public Double getTotalPagar() {
        return totalPagar;
    }

    public void setTotalPagar(Double totalPagar) {
        this.totalPagar = totalPagar;
    }
}
