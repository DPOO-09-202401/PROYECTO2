package vista;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import utilidades.Utilidades;

public class VistaAdmin {
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();

    public static void main(String[] args) {
        VistaAdmin vista = new VistaAdmin();
        Boolean continuar = true;

        Utilidades.cargarDatos("./src/datos/");
        while (continuar) {
            Boolean autenticado = vista.iniciarSesion();
            if (autenticado) {
                vista.ejecutarOpciones();
            }
        }
    }

    private boolean iniciarSesion() {
        System.out.println("--------------------");
        System.out.println("BIENVENIDO A LA APP PARA ADMINISTRADORES DE GALERIANDES");
        String correo = Utilidades.lectorConsola("Ingrese su correo: ");
        String contrasena = Utilidades.lectorConsola("Ingrese su contraseña: ");
        System.out.println("--------------------");
        ModeloUsuario autenticado = logicaUsuarios.iniciarSesion(correo, contrasena, ModeloRol.ADMINISTRADOR);
        if (autenticado == null) {
            System.out.println("Autenticacion fallida");
            return false;
        }
        System.out.println("Bienvenido");
        return true;
    }

    private void imprimirOpciones() {
        System.out.println("--------------------");
        System.out.println("1. Consultar Usuarios");
        System.out.println("2. Registrar Usuario");
        System.out.println("3. Eliminar Usuario");
        System.out.println("4. Verificar comprador");
        System.out.println("5. Aumentar límite de compra de usuario");
        System.out.println("6. Consultar piezas");
        System.out.println("7. Registrar pieza");
        System.out.println("8. Devolver pieza");
        System.out.println("9. Consultar subastas");
        System.out.println("10. Consultar ofertas de compra");
        System.out.println("11. Aprobar compra");
        System.out.println("12. Denegar compra");
        System.out.println("13. Salir");
        System.out.println("--------------------");

    }

    private void ejecutarOpciones() {
        Boolean continuar = true;
        while (continuar) {
            imprimirOpciones();
            Integer opcion = Integer.parseInt(Utilidades.lectorConsola("Ingrese opcion a escoger: "));
            switch (opcion) {
                case 1:
                    //CONSULTAR USUARIOS
                    HashMap<String, ModeloUsuario> usuarios = logicaUsuarios.consultarTodos();
                    this.imprimirUsuarios(usuarios);
                    break;
                case 2:
                    //REGISTRAR USUARIO
                    try {
                        registrarUsuario();
                    } catch (Exception e) {
                        System.out.println("Error creando usuario");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    //ELIMINAR USUARIO
                    this.eliminarUsuario();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    break;
                case 12:
                    break;
                case 13:
                    continuar = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void imprimirUsuarios(HashMap<String, ModeloUsuario> usuarios) {
        for (ModeloUsuario u : usuarios.values()) {
            System.out
                    .println("-> " + u.getNombre() + ", " + u.getCorreo() + ", " + u.getRol() + ", " + u.getTelefono());
        }

    }

    private void eliminarUsuario(){
        String correo = Utilidades.lectorConsola("Inserte el correo del usuario: ");
        try {
            logicaUsuarios.eliminarUno(correo);
            System.out.println("Usuario eliminado exitosamente");
        } catch (Exception e) {
            System.out.println("Error eliminando el usuario");
            System.out.println(e.getMessage());
        }
    }

    private ModeloUsuario registrarUsuario() throws Exception{

            String nombre = Utilidades.lectorConsola("Ingrese nombre: ");
            String correo = Utilidades.lectorConsola("Ingrese correo: ");
            String contrasena = Utilidades.lectorConsola("Ingrese contraseña: ");
            System.out.println("Roles disponibles: ADMINISTRADOR, CLIENTE, EMPLEADO");
            ModeloRol rol = null;
            while (rol == null){
                try {
                    rol = ModeloRol.valueOf(Utilidades.lectorConsola("Ingrese rol: "));
                } catch (Exception e) {
                    rol = null;
                    System.out.println("Rol incorrecto");
                }
            }
            String telefono = Utilidades.lectorConsola("Ingrese su telefono: ");
            ModeloUsuario nuevoUsuario = new ModeloUsuario(nombre, correo, rol, contrasena, telefono);
            ModeloUsuario creado =  logicaUsuarios.crearUno(nuevoUsuario);
            return creado;   
    } 
}
