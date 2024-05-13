package vista;

import java.util.HashMap;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import utilidades.Utilidades;

public class VistaEmpleado {
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
    private VistasPiezas vistasPiezas = new VistasPiezas();
    private ModeloUsuario usuarioActual;

    public static void main(String[] args) {
        Utilidades.cargarDatos("./src/datos/");
        VistaEmpleado vista = new VistaEmpleado();
        Boolean continuar = true;
        while (continuar) {
            Boolean autenticado = vista.iniciarSesion();
            if (autenticado) {
                vista.ejecutarOpciones();
            }
        }
    }

    private Boolean iniciarSesion() {
        System.out.println("--------------------");
        System.out.println("BIENVENIDO A LA APP PARA EMPLEADOS DE GALERIANDES");
        String correo = Utilidades.lectorConsola("Ingrese su correo: ");
        String contrasena = Utilidades.lectorConsola("Ingrese su contraseña: ");
        System.out.println("--------------------");
        ModeloUsuario autenticado = logicaUsuarios.iniciarSesion(correo, contrasena, ModeloRol.EMPLEADO);
        if (autenticado == null) {
            System.out.println("Autenticacion fallida");
            return false;
        }
        System.out.println("Bienvenido");
        this.usuarioActual = autenticado;
        return true;
    }

    private void imprimirOpciones() {
        System.out.println("--------------------");
        System.out.println("1. Consultar perfil");
        System.out.println("2. Consultar clientes");
        System.out.println("3. Consultar piezas en check-out");
        System.out.println("4. Recibir pago por pieza");
        System.out.println("5. Consultar piezas en subasta");
        System.out.println("6. Consultar procesos de subasta");
        System.out.println("7. Registrar oferta en subasta");
        System.out.println("8. Finalizar subasta");
        System.out.println("9. NUEVO Consultar historial de una pieza");

        System.out.println("10. Salir");
        System.out.println("--------------------");
    }

    private void ejecutarOpciones() {
        Boolean continuar = true;
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
                    // Imprimir Perfil
                    this.imprimirPerfil();
                    break;
                case 2:
                    //CONSULTAR CLIENTES
                    HashMap<String, ModeloUsuario> clientes = logicaUsuarios.consultarClientes();
                    this.imprimirUsuarios(clientes);
                    break;
                case 3:
                    // CONSULTAR PIEZAS EN CHECKOUT
                    this.vistasPiezas.consultarPiezasEnCheckout();
                    break;
                case 4:
                    // RECIBIR PAGO POR PIEZA
                    this.vistasPiezas.recibirPagoPorPieza();
                    break;
                case 5:
                    // CONSULTAR PIEZAS EN SUBASTA
                    this.vistasPiezas.consultarPiezasEnSubasta();
                    break;
                case 6:
                    // CONSULTAR PROCESOS DE SUBASTA
                    this.vistasPiezas.consultarProcesosDeSubasta();
                    break;
                case 7:
                    // REGISTRAR OFERTA EN SUBASTA
                    this.vistasPiezas.registrarOfertaEnSubasta();
                    break;
                case 8:
                    // FINALIZAR SUBASTA
                    this.vistasPiezas.finalizarSubasta();
                    break;
                case 9:
                    //HISTORIAL DE UNA PIEZA
                    this.vistasPiezas.consultarHisorialPieza();
                    break;
                
                case 10:
                    continuar = false;
                    break;

                default:
                    break;
            }
        }
    }

    private void imprimirPerfil() {
        ModeloUsuario u = this.usuarioActual;
        System.out.println("-> " + u.getNombre() + ", " + u.getCorreo() + ", " + u.getRol() + ", " + u.getTelefono());
    }

    private void imprimirUsuarios(HashMap<String, ModeloUsuario> usuarios) {
        for (ModeloUsuario u : usuarios.values()) {
            System.out
                    .println("-> " + u.getNombre() + ", " + u.getCorreo() + ", " + u.getRol() + ", " + u.getTelefono());
            if (u.getRol().equals(ModeloRol.CLIENTE)){
                System.out.println("    -> Autorizado: "+u.getAutorizado()+", límite de pagos: "+u.getLimitePago());
            }
        }

    }
}
