package vista;

import java.util.HashMap;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import utilidades.Utilidades;

public class VistaCliente {
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
    private ModeloUsuario usuarioActual;

    public static void main(String[] args){
        VistaCliente vista = new VistaCliente();
        Utilidades.cargarDatos("./src/datos/");
        
        Boolean continuar = true;
        while (continuar) {
            Boolean autenticado = vista.iniciarSesion();
            if (autenticado) {
                vista.ejecutarOpciones();
            }
        }

    }

    private boolean iniciarSesion() {
        System.out.println("--------------------");
        System.out.println("BIENVENIDO A LA APP PARA CLIENTES DE GALERIANDES");
        String correo = Utilidades.lectorConsola("Ingrese su correo: ");
        String contrasena = Utilidades.lectorConsola("Ingrese su contraseña: ");
        System.out.println("--------------------");
        ModeloUsuario autenticado = logicaUsuarios.iniciarSesion(correo, contrasena, ModeloRol.CLIENTE);
        if (autenticado == null) {
            System.out.println("Autenticacion fallida");
            return false;
        }
        System.out.println("Bienvenido");
        this.usuarioActual = autenticado;
        return true;
    }

    private void imprimirOpciones(){
        System.out.println("--------------------");
        System.out.println("1. Consultar perfil");
        System.out.println("2. Consultar piezas en venta");
        System.out.println("3. Solicitar compra de pieza");
        System.out.println("4. Consultar piezas en subasta");
        System.out.println("5. Ofertar en subasta");
        System.out.println("6. Consultar mis piezas");
        System.out.println("7. Consultar piezas bloqueadas por mi");
        
        System.out.println("8. Salir");
        System.out.println("--------------------");
    }

    private void ejecutarOpciones(){
        Boolean continuar  = true ;
        while (continuar) {
            imprimirOpciones();
            Integer opcion = Integer.parseInt(Utilidades.lectorConsola("Ingrese opcion a escoger: "));
            switch (opcion) {
                case 1:
                    this.imprimirPerfil();
                    break;
                case 2:
                    
                    break;
                case 3:
                    
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
                    continuar = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void imprimirPerfil(){
        ModeloUsuario u = this.usuarioActual;
        System.out.println("-> "+u.getNombre()+", "+u.getCorreo()+", "+u.getRol()+", "+u.getTelefono());
    }
}
