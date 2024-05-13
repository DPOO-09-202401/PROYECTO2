package vista;

import java.util.HashMap;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import utilidades.Utilidades;

public class VistaAdmin {
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
    private VistasPiezas vistasPiezas = new VistasPiezas();
    private static String ruta = "./src/datos/";

    public static void main(String[] args) {
        VistaAdmin vista = new VistaAdmin();
        Boolean continuar = true;

        Utilidades.cargarDatos(ruta);
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
        System.out.println("2. Consultar Clientes");
        System.out.println("3. Registrar Usuario");
        System.out.println("4. Eliminar Usuario");
        System.out.println("5. Autorizar comprador");
        System.out.println("6. Cambiar límite de compra de usuario");
        System.out.println("7. Consultar piezas");
        System.out.println("8. Registrar pieza");
        System.out.println("9. Devolver pieza");
        System.out.println("10. Mover pieza");
        System.out.println("11. Consultar subastas");
        System.out.println("12. Iniciar subasta");
        System.out.println("13. Abortar subasta");
        System.out.println("14. Consultar ofertas de compra");
        System.out.println("15. Aprobar compra");
        System.out.println("16. Confirmar venta");
        System.out.println("17. Denegar compra");
        System.out.println("18. NUEVO Consultar historial de una pieza");
        System.out.println("19. Salir");
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
                    //CONSULTAR USUARIOS
                    HashMap<String, ModeloUsuario> usuarios = logicaUsuarios.consultarTodos();
                    this.imprimirUsuarios(usuarios);
                    break;
                case 2:
                    //CONSULTAR CLIENTES
                    HashMap<String, ModeloUsuario> clientes = logicaUsuarios.consultarClientes();
                    this.imprimirUsuarios(clientes);
                    break;
                case 3:
                    //REGISTRAR USUARIO
                    try {
                        registrarUsuario();
                    } catch (Exception e) {
                        System.out.println("Error creando usuario");
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    //ELIMINAR USUARIO
                    this.eliminarUsuario();
                    break;
                case 5:
                    //AUTORIZAR CLIENTE
                    this.cambiarAutorizacionCliente();
                    break;
                case 6:
                    //CAMBIAR LÍMITE DE PAGO DE CLIENTE
                    this.cambiarLimiteCliente();
                    break;
                case 7:
                    // CONSULTAR PIEZAS
                    this.vistasPiezas.imprimirTodasLasPiezas();
                    break;
                case 8:
                    // REGISTRAR NUEVA PIEZA
                    this.vistasPiezas.registrarPieza();
                    break;
                case 9:
                    //DEVOLVER PIEZA
                    this.vistasPiezas.devolverPieza();
                    break;
                case 10:
                    // MOVER UNA PIEZA
                    this.vistasPiezas.moverPieza();
                    break;
                case 11:
                    // CONSULTAR PIEZAS EN SUBASTA
                    this.vistasPiezas.consultarPiezasEnSubasta();
                    break;
                case 12:
                    // INICIAR UNA SUBASTA
                    this.vistasPiezas.iniciarSubasta();
                    break;
                case 13:
                    // ABORTAR UNA SUBASTA
                    this.vistasPiezas.abortarSubasta();
                    break;
                case 14:
                    //CONSULTAR OFERTAS DE COMPRA (PIEZAS BLOQUEADAS QUE NO ESTAN VENDIDAS, NI EN CHECKOUT NI EN SUBASTA)
                    this.vistasPiezas.consultarSolicitudesDeCompra();
                    break;
                case 15:
                    // APROBAR UNA COMPRA
                    this.vistasPiezas.aprobarCompra();
                    break;
                case 16:
                    // CONFIRMAR UNA VENTA
                    this.vistasPiezas.confirmarVenta();
                    break;
                case 17:
                    //DENEGAR COMPRA (DESBLOQUEAR UNA PIEZA)
                    this.vistasPiezas.desbloquearPieza();
                    break;
                case 18:
                    //CONSULTAR HISTORIAL DE UNA PIEZA
                    this.vistasPiezas.consultarHisorialPieza();
                    break;
                case 19:
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
            if (u.getRol().equals(ModeloRol.CLIENTE)){
                System.out.println("    -> Autorizado: "+u.getAutorizado()+", límite de pagos: "+u.getLimitePago());
            }
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
            String telefono = null;
            while (telefono == null){
                try {
                    telefono = Utilidades.lectorConsola("Ingrese Telefono (10 dígitos): ");
                    if (!(telefono.length() == 10)){
                        throw new Exception("El telefono debe tener 10 digitos");
                    }
                    if (!Utilidades.esNumerico(telefono)){
                        throw new Exception("El telefono solo puede contener caracteres numéricos");
                    }
                } catch (Exception e) {
                    telefono = null;
                    System.out.println(e.getMessage());
                }
            };
            ModeloUsuario nuevoUsuario = new ModeloUsuario(nombre, correo, rol, contrasena, telefono);
            if (nuevoUsuario.getRol().equals(ModeloRol.CLIENTE)){
                nuevoUsuario.setAutorizado(false);
                nuevoUsuario.setLimitePago(0.0);
            }
            ModeloUsuario creado =  logicaUsuarios.crearUno(nuevoUsuario);
            System.out.println("Usuario creado exitosamente");
            return creado;
    } 

    private void cambiarAutorizacionCliente(){
        try {
            String correo = Utilidades.lectorConsola("Ingrese el correo del cliente: ");
            ModeloUsuario usuario = logicaUsuarios.consultarUno(correo);
            if (!usuario.getRol().equals(ModeloRol.CLIENTE)){
                throw new Exception("El usuario debe ser un cliente");
            }
            Boolean estadoPrevio = usuario.getAutorizado();
            usuario.setAutorizado(!estadoPrevio);
            Boolean estadoNuevo = usuario.getAutorizado();
            logicaUsuarios.editarUno(usuario);
            System.out.println("Autorización modificada de "+estadoPrevio+" a -> "+estadoNuevo);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    private void cambiarLimiteCliente(){
        try {
            String correo = Utilidades.lectorConsola("Ingrese el correo del cliente: ");
            ModeloUsuario usuario = logicaUsuarios.consultarUno(correo);
            if (!usuario.getRol().equals(ModeloRol.CLIENTE)){
                throw new Exception("El usuario debe ser un cliente");
            }
            Double estadoPrevio = usuario.getLimitePago();
            System.out.println("Estado actual -> "+estadoPrevio);
            Double nuevo = Double.parseDouble(Utilidades.lectorConsola("Ingrese el nuevo monto máximo (con punto decimal): "));
            usuario.setLimitePago(nuevo);
            logicaUsuarios.editarUno(usuario);
            Double estadoNuevo = usuario.getLimitePago();
            System.out.println("Autorización modificada de "+estadoPrevio+" a -> "+estadoNuevo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
