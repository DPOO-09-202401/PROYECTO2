package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import modelo.ModeloPiezas;
import modelo.ModeloRol;
import modelo.ModeloUsuario;
import modelo.PersistenciaPieza;

public class LogicaPiezas implements Logica<ModeloPiezas> {

    private PersistenciaPieza persistenciaPieza = new PersistenciaPieza();
    private LogicaUsuarios logicaUsuarios = new LogicaUsuarios();

    @Override
    public ModeloPiezas crearUno(ModeloPiezas nuevo) throws Exception {
        if (PersistenciaPieza.piezas.containsKey(nuevo.getTitulo())) {
            throw new Exception("Ya existe una pieza con ese nombre");
        }
        ModeloUsuario usuario = logicaUsuarios.consultarUno(nuevo.getEmailDueno());
        if (usuario == null) {
            throw new Exception("No se encontró un cliente con el email asociado");
        } else if (!usuario.getRol().equals(ModeloRol.CLIENTE)) {
            throw new Exception("El usuario asociado al correo no es un usuario");
        }

        PersistenciaPieza.piezas.put(nuevo.getTitulo(), nuevo);
        persistenciaPieza.actualizar();

        return nuevo;
    }

    @Override
    public ModeloPiezas consultarUno(String identificador) throws Exception {
        ModeloPiezas pieza = PersistenciaPieza.piezas.get(identificador);
        if (pieza == null) {
            throw new Exception("Pieza no encontrada");
        }
        return pieza;

    }

    @Override
    public HashMap<String, ModeloPiezas> consultarTodos() {
        return PersistenciaPieza.piezas;
    }

    @Override
    public ModeloPiezas editarUno(ModeloPiezas pieza) throws Exception {
        if (!PersistenciaPieza.piezas.containsKey(pieza.getTitulo())) {
            throw new Exception("Pieza no encontrada");
        }

        PersistenciaPieza.piezas.put(pieza.getTitulo(), pieza);
        persistenciaPieza.actualizar();
        return pieza;
    }

    @Override
    public void eliminarUno(String identificador) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUno'");
    }

    @Override
    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException {
        persistenciaPieza.cargar(ruta);
        return PersistenciaPieza.piezas.size();
    }

    public void verificarDisponible(ModeloPiezas pieza) throws Exception {
        if (pieza.getBloqueda()) {
            throw new Exception("La pieza está bloqueada");
        }
        switch (pieza.getEstado()) {
            case DEVUELTA:
                throw new Exception("La pieza ya ha sido devuelta con anterioridad");
            case EN_SUBASTA:
                throw new Exception("La pieza está en un proceso de subasta");
            case VENDIDA:
                throw new Exception("La pieza ya ha sido vendida");
            case EN_CHECKOUT:
                throw new Exception("La pieza está en checkout esperando el pago de compra");
            case PAGADA:
                throw new Exception(
                        "La pieza está ya ha sido pagada y está esperando confirmación del administrador para ser retirada.");

            default:
                break;
        }
    }

}
