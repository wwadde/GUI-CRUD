package org.example;

public class DatosEstudiante {

    String nombre;
    String apellido;
    String correo;
    String codigo;

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public DatosEstudiante(String nombre, String apellido, String correo, String codigo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.codigo = codigo;
    }

}
