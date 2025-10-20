package gov.peru.escuela.conducir.bean;

import java.util.Objects;

public class Escuela {
    private Integer id_establecimiento;
    private String no_ruc;
    private String departamento;
    private String provincia;
    private String distrito;
    private String email;
    private String phone;
    private String nombre_centro;
    private String direccion_centro;
    private boolean estado_autorizacion;
    private String id_distrito;

    public Escuela() {
    }

    public Escuela( String no_ruc, String nombre_centro, String direccion_centro, boolean estado_autorizacion, String id_distrito) {
        this.no_ruc = no_ruc;
        this.nombre_centro = nombre_centro;
        this.direccion_centro = direccion_centro;
        this.estado_autorizacion = estado_autorizacion;
        this.id_distrito = id_distrito;
    }
    public Escuela(String noRuc, String nombreCentro, String direccionCentro, boolean b, String email, String numero, String idDistrito) {
        this.no_ruc = noRuc;
        this.nombre_centro = nombreCentro;
        this.direccion_centro = direccionCentro;
        this.email = email;
        this.phone = numero;
        this.estado_autorizacion = b;
        this.id_distrito = idDistrito;
    }




    public Integer getId_establecimiento() {
        return id_establecimiento;
    }

    public void setId_establecimiento(Integer id_establecimiento) {
        this.id_establecimiento = id_establecimiento;
    }

    public String getNo_ruc() {
        return no_ruc;
    }

    public void setNo_ruc(String no_ruc) {
        this.no_ruc = no_ruc;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNombre_centro() {
        return nombre_centro;
    }

    public void setNombre_centro(String nombre_centro) {
        this.nombre_centro = nombre_centro;
    }

    public String getDireccion_centro() {
        return direccion_centro;
    }

    public void setDireccion_centro(String direccion_centro) {
        this.direccion_centro = direccion_centro;
    }

    public boolean isEstado_autorizacion() {
        return estado_autorizacion;
    }

    public void setEstado_autorizacion(boolean estado_autorizacion) {
        this.estado_autorizacion = estado_autorizacion;
    }

    public String getId_distrito() {
        return id_distrito;
    }

    public void setId_distrito(String id_distrito) {
        this.id_distrito = id_distrito;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Escuela escuela)) return false;
        return Objects.equals(id_establecimiento, escuela.id_establecimiento) && Objects.equals(no_ruc, escuela.no_ruc) && Objects.equals(email, escuela.email) && Objects.equals(phone, escuela.phone) && Objects.equals(nombre_centro, escuela.nombre_centro) && Objects.equals(direccion_centro, escuela.direccion_centro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_establecimiento, no_ruc, email, phone, nombre_centro, direccion_centro);
    }

    @Override
    public String toString() {
        return "Escuela{" +
                "id_establecimiento='" + id_establecimiento + '\'' +
                ", no_ruc='" + no_ruc + '\'' +
                ", departamento='" + departamento + '\'' +
                ", provincia='" + provincia + '\'' +
                ", distrito='" + distrito + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nombre_centro='" + nombre_centro + '\'' +
                ", direccion_centro='" + direccion_centro + '\'' +
                ", estado_autorizacion=" + estado_autorizacion +
                ", id_distrito='" + id_distrito + '\'' +
                '}';
    }
}

