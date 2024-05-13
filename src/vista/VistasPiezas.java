package vista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import controlador.LogicaOfertas;
import controlador.LogicaPiezas;
import controlador.LogicaSubastas;
import controlador.LogicaVentas;
import modelo.ModeloEstado;
import modelo.ModeloEstadoSubasta;
import modelo.ModeloOferta;
import modelo.ModeloPiezas;
import modelo.ModeloSubasta;
import modelo.ModeloTipoDeVenta;
import modelo.ModeloTiposPago;
import modelo.ModeloTiposPieza;
import modelo.ModeloUsuario;
import modelo.ModeloVenta;
import modelo.PersistenciaPieza;
import utilidades.Utilidades;

public class VistasPiezas {

    private LogicaPiezas logicaPiezas = new LogicaPiezas();
    private LogicaVentas logicaVentas = new LogicaVentas();
    private LogicaSubastas logicaSubastas = new LogicaSubastas();
    private LogicaOfertas logicaOfertas = new LogicaOfertas();

    // GENERAL

    public void imprimirTodasLasPiezas() {

        HashMap<String, ModeloPiezas> piezas = logicaPiezas.consultarTodos();

        imprimirPiezas(piezas);

    }

    private void imprimirPiezas(HashMap<String, ModeloPiezas> piezas) {
        if (piezas.isEmpty()) {
            System.out.println("Resultado vacío");
        } else {
            for (ModeloPiezas p : piezas.values()) {
                imprimirPieza(p);
            }
        }

    }

    public void imprimirPieza(ModeloPiezas p) {
        System.out.println("-> " + p.getTitulo() + ", " + p.getTipo() + ", Autor: " + p.getNombreAutor() + ", Dueño: "
                + p.getEmailDueno());
        System.out.println("    ->  Materiales: " + p.getMateriales());
        System.out.println("    ->  Bloqueada: " + p.getBloqueda());
        System.out.println("    ->  Estado: " + p.getEstado());
        System.out.println("    ->  Precio mínimo: " + p.getPrecioMinimo());
        System.out.println("    ->  Tipo de venta permitido: " + p.getTipoDeVenta());
        if (p.getBloqueda()) {
            System.out.println("    -> Bloqueada por: " + p.getEmailOfertante());
        }
    }

    // ADMINISTRADOR

    public void registrarPieza() {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese nombre -> ");
            String nombreAutor = Utilidades.lectorConsola("Ingrese el nombre del autor -> ");
            String emailDueno = Utilidades.lectorConsola("Ingrese el email del dueño -> ");

            System.out.print("Tipos de pieza: ");

            for (ModeloTiposPieza p : ModeloTiposPieza.values()) {
                System.out.print(" " + p);
            }
            System.out.print("\n");

            ModeloTiposPieza tipo = ModeloTiposPieza.valueOf(Utilidades.lectorConsola("Ingrese el tipo de pieza -> "));
            ArrayList<String> materiales = new ArrayList<>(Arrays
                    .asList(Utilidades.lectorConsola("Ingrese los materiales separados por espacios -> ").split(" ")));
            Boolean bloqueada = false;
            Double precioMinimo = Double
                    .parseDouble(Utilidades.lectorConsola("Ingrese el precio mínimo con punto decimal -> "));
            System.out.println("Estados posibles: BODEGA, EXHIBICION");
            ModeloEstado estado = ModeloEstado
                    .valueOf(Utilidades.lectorConsola("Ingrese el estado inicial de la pieza -> "));
            System.out.println("Tipos de venta posibles : " + Arrays.asList(ModeloTipoDeVenta.values()));
            ModeloTipoDeVenta tipoDeVenta = ModeloTipoDeVenta
                    .valueOf(Utilidades.lectorConsola("Ingrese el tipo de venta de la pieza -> "));
            ModeloPiezas pieza = new ModeloPiezas(titulo, nombreAutor, emailDueno, tipo, materiales, bloqueada,
                    precioMinimo, estado, null, tipoDeVenta);
            logicaPiezas.crearUno(pieza);
            System.out.println("Pieza creada correctamente");
            imprimirPieza(pieza);
        } catch (Exception e) {
            System.out.println("Error registrando una pieza");
            e.printStackTrace();
        }
    }

    public void devolverPieza() {
        try {
            System.out.println("Devolver una pieza: Seleccione una pieza que no esté bloqueada ni en subasta");
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza que desea devolver -> ");

            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);

            this.logicaPiezas.verificarDisponible(pieza);

            pieza.setEstado(ModeloEstado.DEVUELTA);
            logicaPiezas.editarUno(pieza);
            System.out.println("La pieza ha sido devuelta a su dueño exitosamente");

        } catch (Exception e) {
            System.out.println("Error al editar la información de una pieza");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void moverPieza() {
        ArrayList<ModeloEstado> estadosValidos = new ArrayList<>(
                Arrays.asList(ModeloEstado.BODEGA, ModeloEstado.EXHIBICION));

        try {

            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza que desea mover -> ");
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);

            this.logicaPiezas.verificarDisponible(pieza);

            System.out.println("Mover una Pieza. Los posibles estados son: " + ModeloEstado.BODEGA + ", "
                    + ModeloEstado.EXHIBICION);

            ModeloEstado estado = null;
            while (estado == null) {
                try {
                    estado = ModeloEstado.valueOf(Utilidades.lectorConsola("Ingrese el estado ->  "));
                    if (!estadosValidos.contains(estado)) {
                        throw new Exception("Estado invalido");
                    }
                } catch (Exception e) {
                    System.out.println("Estado invalido");
                    estado = null;
                }
            }
            pieza.setEstado(estado);
            logicaPiezas.editarUno(pieza);
            System.out.println("La pieza fue movida exitosamente a -> " + estado);
        } catch (Exception e) {
            System.out.println("Error moviendo la pieza");
            System.out.println(e.getMessage());
        }
    }

    public void desbloquearPieza() {
        String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza a desbloquear -> ");
        try {
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            pieza.setBloqueda(false);
            pieza.setEstado(ModeloEstado.BODEGA);
            pieza.setEmailOfertante(null);
            logicaPiezas.editarUno(pieza);
            System.out.println("Pieza desbloqueada exitosamente");
            System.out.println("La pieza fue regresada a la bodega");
        } catch (Exception e) {
            System.out.println("Error desbloqueando la pieza");
            System.out.println(e.getMessage());
        }

    }

    public void consularPiezasEnSubasta() {
        HashMap<String, ModeloPiezas> filtro = new HashMap<>();
        for (ModeloPiezas p : PersistenciaPieza.piezas.values()) {
            boolean condicion = p.getEstado().equals(ModeloEstado.EN_SUBASTA)
                    && (p.getTipoDeVenta().equals(ModeloTipoDeVenta.SUBASTA)
                            || p.getTipoDeVenta().equals(ModeloTipoDeVenta.TODO));

            if (condicion) {
                filtro.put(p.getTitulo(), p);
            }
        }
        this.imprimirPiezas(filtro);
    }

    public void iniciarSubasta() {
        String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza a subastar -> ");
        try {
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            if (!(pieza.getTipoDeVenta().equals(ModeloTipoDeVenta.SUBASTA)
                    || pieza.getTipoDeVenta().equals(ModeloTipoDeVenta.TODO))) {
                throw new Exception("Esta pieza no se puede subastar.");
            }
            this.logicaPiezas.verificarDisponible(pieza);
            pieza.setEstado(ModeloEstado.EN_SUBASTA);
            logicaPiezas.editarUno(pieza);

            Double inicial = Double.parseDouble(Utilidades.lectorConsola("Ingrese el valor inicial de la subasta con punto decimal -> "));

            ModeloSubasta subasta = new ModeloSubasta(LocalDate.now(), pieza.getTitulo(), ModeloEstadoSubasta.ABIERTA, inicial);
            logicaSubastas.crearUno(subasta);
            
            System.out.println("La subasta ha sido iniciada exitosamente");
        } catch (Exception e) {
            System.out.println("Error iniciando la subasta");
            System.out.println(e.getMessage());
        }

    }

    public void abortarSubasta() {
        String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza en subasta -> ");
        try {
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            if (!pieza.getEstado().equals(ModeloEstado.EN_SUBASTA)) {
                throw new Exception("Esta pieza no está en subasta");
            }
            pieza.setEstado(ModeloEstado.BODEGA);

            ModeloSubasta subasta = logicaSubastas.consultarPorTituloDePieza(titulo);
            subasta.setEstado(ModeloEstadoSubasta.CERRADA);

            logicaSubastas.editarUno(subasta);
            System.out.println("La subasta ha sido CERRADA exitosamente");
            logicaPiezas.editarUno(pieza);
            System.out.println("La pieza ha sido regresada a la bodega");

        } catch (Exception e) {
            System.out.println("Error cancelando la subasta");

        }

    }

    public void consultarSolicitudesDeCompra() {
        HashMap<String, ModeloPiezas> piezas = PersistenciaPieza.piezas;

        for (ModeloPiezas p : piezas.values()) {
            Boolean condition = p.getBloqueda()
                    && (p.getEstado().equals(ModeloEstado.BODEGA) || p.getEstado().equals(ModeloEstado.EXHIBICION));
            if (condition) {
                imprimirPieza(p);
            }
        }
    }

    public void aprobarCompra() {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza en oferta de compra -> ");

            ModeloPiezas p = logicaPiezas.consultarUno(titulo);

            Boolean condition = p.getBloqueda()
                    && (p.getEstado().equals(ModeloEstado.BODEGA) || p.getEstado().equals(ModeloEstado.EXHIBICION));

            if (!condition) {
                throw new Exception(
                        "La pieza no cuenta con las condiciones. Debe estar bloqueada y en la bodega o en exhibición.");
            }

            p.setEstado(ModeloEstado.EN_CHECKOUT);
            logicaPiezas.editarUno(p);

            System.out.println("El estado de la pieza se ha cambiado a : " + ModeloEstado.EN_CHECKOUT);
            System.out.println("Espere la recepción del pago por parte del empleado");

        } catch (Exception e) {
            System.out.println("Error aprobando la compra");
            System.out.println(e.getMessage());
        }
    }

    public void confirmarVenta() {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza que ya ha sido pagada -> ");
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            if (!pieza.getEstado().equals(ModeloEstado.PAGADA)) {
                throw new Exception("Esta pieza aún no ha sido pagada.");
            }
            // ModeloVenta modeloVenta = new ModeloVenta(titulo, pieza.getEmailDueno(),
            // pieza.getEmailOfertante(),
            // pieza.getPrecioMinimo(), LocalDate.now(), ModeloTiposPago.EFECTIVO,
            // pieza.getTipoDeVenta(), true);

            ModeloVenta venta = logicaVentas.consultarPagoMasRecientePorPieza(titulo);

            pieza.setEstado(ModeloEstado.VENDIDA);
            pieza.setBloqueda(false);
            pieza.setEmailDueno(pieza.getEmailOfertante());
            pieza.setEmailOfertante(null);
            pieza.setTipoDeVenta(null);
            venta.setConfirmadoAdmin(true);
            logicaVentas.editarUno(venta);
            logicaPiezas.editarUno(pieza);

            System.out.println("El estado de la pieza ha sido cambiado a -> " + pieza.getEstado());
            System.out.println("El nuevo dueño de la pieza es -> " + pieza.getEmailDueno());
            System.out.println("Pieza vendida exitosamente por -> " + pieza.getPrecioMinimo());

        } catch (Exception e) {
            System.out.println("Error confirmando la venta");
            System.out.println(e.getMessage());
        }
    }

    // CLIENTE

    public void consultarPiezasEnVenta() {
        HashMap<String, ModeloPiezas> piezas = PersistenciaPieza.piezas;
        for (ModeloPiezas p : piezas.values()) {
            boolean condicion = !p.getBloqueda()
                    && (p.getEstado().equals(ModeloEstado.BODEGA) || p.getEstado().equals(ModeloEstado.EXHIBICION))
                    && (p.getTipoDeVenta().equals(ModeloTipoDeVenta.VENTA_DIRECTA)
                            || p.getTipoDeVenta().equals(ModeloTipoDeVenta.TODO));
            if (condicion) {
                imprimirPieza(p);
            }
        }
    }

    public void solicitarCompraDePieza(ModeloUsuario usuario) {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza que desea comprar -> ");
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            logicaPiezas.verificarDisponible(pieza);
            if (!(pieza.getTipoDeVenta().equals(ModeloTipoDeVenta.VENTA_DIRECTA)
                    || pieza.getTipoDeVenta().equals(ModeloTipoDeVenta.TODO))) {
                throw new Exception("Esta pieza no se puede comprar directamente.");
            }

            if (!usuario.getAutorizado()) {
                throw new Exception("El usuario " + usuario.getCorreo() + " no está autorizado para realizar compras");
            }
            if (usuario.getLimitePago() < pieza.getPrecioMinimo()) {
                throw new Exception("El usuario " + usuario.getCorreo()
                        + " no está autorizado para realizar compras mayores a " + pieza.getPrecioMinimo() + "$");
            }
            pieza.setBloqueda(true);
            pieza.setEmailOfertante(usuario.getCorreo());
            logicaPiezas.editarUno(pieza);
            System.out.println(
                    "La pieza fue bloqueada exitosamente, espere confirmación de la administración para proceder con el pago");

        } catch (Exception e) {
            System.out.println("Error solicitando la compra de una pieza");
            System.out.println(e.getMessage());
        }
    }

    public void consultarPiezasBloqueadasPorCliente(ModeloUsuario clienteSolicitante) {
        try {
            HashMap<String, ModeloPiezas> filtro = new HashMap<>();
            for (ModeloPiezas p : PersistenciaPieza.piezas.values()) {
                if (p.getBloqueda() && p.getEmailOfertante() != null
                        && p.getEmailOfertante().equals(clienteSolicitante.getCorreo())) {
                    filtro.put(p.getTitulo(), p);
                }
            }
            this.imprimirPiezas(filtro);
        } catch (Exception e) {
            System.out.println("Error consultando las piezas");
            System.out.println(e.getMessage());
        }
    }

    // EMPLEADO
    public void consultarPiezasEnCheckout() {
        HashMap<String, ModeloPiezas> filtro = new HashMap<>();
        for (ModeloPiezas p : PersistenciaPieza.piezas.values()) {
            if (p.getEstado().equals(ModeloEstado.EN_CHECKOUT)) {
                filtro.put(p.getTitulo(), p);
            }
        }
        this.imprimirPiezas(filtro);
    }

    public void recibirPagoPorPieza() {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el título de la pieza que se va a pagar -> ");
            System.out.println("Tipos de pago posibles: " + Arrays.toString(ModeloTiposPago.values()));
            ModeloTiposPago tipoPago;
            try {
                tipoPago = ModeloTiposPago
                        .valueOf(Utilidades.lectorConsola("Ingrese el tipo de pago -> "));
            } catch (Exception e) {
                throw new Exception("Tipo de pago invalido");
            }
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            if (!pieza.getEstado().equals(ModeloEstado.EN_CHECKOUT)) {
                throw new Exception("Esta pieza no está en checkout");
            }
            pieza.setEstado(ModeloEstado.PAGADA);
            logicaPiezas.editarUno(pieza);
            // FIX THIS
            ModeloTipoDeVenta tipoVenta = pieza.getTipoDeVenta();
            ModeloVenta venta = new ModeloVenta(pieza.getTitulo(), pieza.getEmailDueno(), pieza.getEmailOfertante(),
                    pieza.getPrecioMinimo(), LocalDate.now(), tipoPago, tipoVenta, false);
            logicaVentas.crearUno(venta);
            System.out.println("Se registro el pago en el log de ventas");
            System.out.println("El estado de la pieza cambió a -> " + pieza.getEstado());
            System.out.println("Espere a la confirmación del administrador para finalizar la transacción");

        } catch (Exception e) {
            System.out.println("Error recibiendo el pago");
            System.out.println(e.getMessage());

        }

    }

    public void consultarPiezasEnSubasta() {
        HashMap<String, ModeloPiezas> filtro = new HashMap<>();
        for (ModeloPiezas p : PersistenciaPieza.piezas.values()) {
            if (p.getEstado().equals(ModeloEstado.EN_SUBASTA)) {
                filtro.put(p.getTitulo(), p);
            }
        }

        this.imprimirPiezas(filtro);
    }

    public void registrarOfertaEnSubasta() {
        try {
            String titulo =  Utilidades.lectorConsola("Ingrese el titulo de la pieza en subasta -> ");
            String emailOfertante = Utilidades.lectorConsola("Ingrese el correo del ofertante -> ");
            Double monto = Double.parseDouble(Utilidades.lectorConsola("Ingrese el monto de la oferta -> "));
            Integer idSubasta = logicaSubastas.consultarPorTituloDePieza(titulo).getId();
            ModeloOferta oferta = new ModeloOferta(idSubasta, emailOfertante, monto);
            logicaOfertas.crearOferta(oferta);
            System.out.println("Oferta registrada exitosamente");
        } catch (Exception e) {
            System.out.println("Error registrando la oferta: " + e.getMessage());
        }
        
    }

    public void consultarProcesosDeSubasta() {
        System.out.println("Procesos de subasta abiertos:");  
        HashMap<Integer, ModeloSubasta> subastasActivas = logicaSubastas.consultarAbiertos();
        for (ModeloSubasta s : subastasActivas.values()) {
            System.out.println("Subasta # " + s.getId() + " - " + s.getTituloPieza() + " - " + s.getEstado() + " - " + "Monto inicial: "+s.getMontoInicial());
        }
    }

    public void finalizarSubasta() {
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza en subasta -> ");
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            ModeloSubasta subasta =  logicaSubastas.consultarPorTituloDePieza(titulo);
            String emailGanador = logicaSubastas.consultarEmailGanador(titulo);
            Double montoGanador = logicaSubastas.consultarMontoGanador(titulo);
            pieza.setPrecioMinimo(montoGanador);
            subasta.setEstado(ModeloEstadoSubasta.CERRADA);
            pieza.setBloqueda(true);
            pieza.setEmailOfertante(emailGanador);
            pieza.setEstado(ModeloEstado.EN_CHECKOUT);
            logicaSubastas.editarUno(subasta);
            logicaPiezas.editarUno(pieza);
            System.out.println("La pieza ha sido bloqueada para el usuario con email: " + emailGanador);
            System.out.println("El estado de la pieza ha sido cambiado a " + pieza.getEstado());
            System.out.println("El estado de la subasta ha sido cambiado a " + subasta.getEstado());
            System.out.println("La subasta ha sido finalizada exitosamente. Finalice la transaccion recibiendo el pago por la pieza.");
        } catch (Exception e) {
            
            System.out.println("Error finalizando la subasta: " + e.getMessage());
        }
    }

    public void consultarHistorialPieza(){
        try {
            String titulo = Utilidades.lectorConsola("Ingrese el titulo de la pieza -> ");
            ModeloPiezas pieza = logicaPiezas.consultarUno(titulo);
            this.imprimirPieza(pieza);
            ArrayList<ModeloVenta> ventas = logicaVentas.consultarHistorialDePieza(titulo);
            if (ventas.isEmpty()) {
                System.out.println("No hay ventas registradas para esta pieza");
            }
            for (ModeloVenta v : ventas) {
                System.out.println("Venta # " + v.getId() + " - " + v.getFecha() + " - " + v.getPrecio() + " - " + v.getTipoPago());
                System.out.println("    -> Dueño anterior: " + v.getEmailDuenoAnterior());
                System.out.println("    -> Nuevo Dueño: " + v.getEmailNuevoDueno());
            }
        } catch (Exception e) {
            System.out.println("Error consultando el historial de la pieza: " + e.getMessage());
        }
    }

    public void consultarPerfilArtista() {
        try {
            String nombreAutor = Utilidades.lectorConsola("Ingrese el nombre del autor -> ");
            HashMap<String, ModeloPiezas> piezas = logicaPiezas.consultarPorAutor(nombreAutor);
            imprimirPiezas(piezas);
            HashMap<Integer, ModeloVenta> ventas = logicaVentas.consultarTodasLasVentas();
            HashMap<Integer, ModeloVenta> filtro = new HashMap<>();
            for (ModeloVenta v : ventas.values()) {
                if (piezas.containsKey(v.getTituloPieza())) {
                    filtro.put(v.getId(), v);
                }
            }
            for (ModeloVenta v : filtro.values()) {
                System.out.println("Venta # " + v.getId() + " - " + v.getFecha() + " - " + v.getPrecio() + " - " + v.getTipoPago());
                System.out.println("    -> Dueño anterior: " + v.getEmailDuenoAnterior());
                System.out.println("    -> Nuevo Dueño: " + v.getEmailNuevoDueno());
            }

        } catch (Exception e) {
            System.out.println("Error consultando el perfil del artista: " + e.getMessage());
        }
    }

    public void consultarMisPiezas(ModeloUsuario usuarioActual) {
        // TODO Auto-generated method stub
        try {
            throw new UnsupportedOperationException("Unimplemented method 'consultarMisPiezas'");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
        }
    }

}
