package vista;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import utilidades.Utilidades;

public class VistaCliente {
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
    private ModeloUsuario usuarioActual;
    private VistasPiezas vistasPiezas = new VistasPiezas();

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
        System.out.println("4. Consultar mis piezas");
        System.out.println("5. Consultar piezas bloqueadas por mi");
        System.out.println("6. NUEVO Consultar historial de una pieza");
        System.out.println("7. NUEVO Consultar perfil de un artista");
        
        System.out.println("8. Salir");
        System.out.println("--------------------");
    }

    private void ejecutarOpciones(){
        Boolean continuar  = true ;
        while (continuar) {
            Utilidades.lectorConsola("Enter para continuar ->");
            imprimirOpciones();
            Integer opcion = 0;
            try {
                opcion = Integer.parseInt(Utilidades.lectorConsola("Ingrese opcion a escoger: "));
            } catch (Exception e) {
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    // VER PERFIL
                    this.imprimirPerfil();
                    break;
                case 2:
                    // VER PIEZAS EN VENTA
                    this.vistasPiezas.consultarPiezasEnVenta();
                    break;
                case 3:
                    // SOLICITAR COMPRA DE PIEZA EN VENTA
                    this.vistasPiezas.solicitarCompraDePieza(usuarioActual);
                    
                    break;
                case 4:
                    // CONSULTAR MIS PIEZAS
                    this.vistasPiezas.consultarMisPiezas(usuarioActual);
                    break;
                case 5:
                    // CONSULTAR LAS PIEZAS BLOQUEADAS POR EL CLIENTE ACTUAL
                    this.vistasPiezas.consultarPiezasBloqueadasPorCliente(usuarioActual);
                    break;
                case 6:
                    // HISTORIAL DE UNA PIEZA
                    this.vistasPiezas.consultarHistorialPieza();
                    break;
                case 7:
                    // CONSULTAR PERFIL DE UN ARTISTA
                    this.vistasPiezas.consultarPerfilArtista();
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
        System.out.println("    -> Autorizado: "+u.getAutorizado());
        System.out.println("    -> Limite de compras: "+u.getLimitePago());
    }
}
