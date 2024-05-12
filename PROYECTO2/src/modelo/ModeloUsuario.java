package modelo;

import java.util.HashMap;

public class ModeloUsuario {

    private String nombre;
    private String correo;
    private ModeloRol rol;
    private String contrasena;
    private String telefono;

    public ModeloUsuario(String nombre, String correo, ModeloRol rol, String contrasena, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.contrasena = contrasena;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ModeloRol getRol() {
        return rol;
    }

    public void setRol(ModeloRol rol) {
        this.rol = rol;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }



    public String getTelefono() {
        return telefono;
    }



    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
