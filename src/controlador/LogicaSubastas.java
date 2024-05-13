package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.ModeloEstadoSubasta;
import modelo.ModeloOferta;
import modelo.ModeloPiezas;
import modelo.ModeloSubasta;
import modelo.PersistenciaOfertas;
import modelo.PersistenciaPieza;
import modelo.PersistenciaSubastas;

public class LogicaSubastas implements Logica<ModeloSubasta> {

    private PersistenciaSubastas persistenciaSubastas = new PersistenciaSubastas();
    private LogicaPiezas logicaPiezas = new LogicaPiezas();
    private LogicaOfertas logicaOfertas = new LogicaOfertas();

    @Override
    public ModeloSubasta crearUno(ModeloSubasta nuevo) throws Exception {
        if (nuevo.getId() == null) {
            nuevo.asignarId();
        } else {
            if (PersistenciaSubastas.subastas.containsKey(nuevo.getId())) {
                throw new Exception("Ya existe una subasta con ese ID");
            }
        }

        ModeloPiezas pieza = logicaPiezas.consultarUno(nuevo.getTituloPieza());
        // EL MONTO INICIAL DE LA SUBASTA DEBE SER MENOR A EL PRECIO MINIMO DE LA PIEZA
        if (!(nuevo.getMontoInicial() < pieza.getPrecioMinimo())) {
            throw new Exception("El monto mínimo de la subasta no puede ser menor al precio mínimo de la pieza");
        }

        PersistenciaSubastas.subastas.put(nuevo.getId(), nuevo);
        persistenciaSubastas.actualizar();
        return nuevo;
    }

    @Override
    public ModeloSubasta consultarUno(String identificador) throws Exception {
        ModeloSubasta subasta = PersistenciaSubastas.subastas.get(Integer.parseInt(identificador));
        if (subasta == null) {
            throw new Exception("Subasta no encontrada");
        }
        return subasta;
    }

    @Override
    public HashMap<Integer, ModeloSubasta> consultarTodos() {
        HashMap<Integer, ModeloSubasta> filtro = new HashMap<>();
        return filtro;
    }

    public HashMap<Integer, ModeloSubasta> consultarAbiertos() {
        HashMap<Integer, ModeloSubasta> filtro = new HashMap<>();
        for (ModeloSubasta subasta : PersistenciaSubastas.subastas.values()) {
            if (subasta.getEstado().equals(ModeloEstadoSubasta.ABIERTA)) {
                filtro.put(subasta.getId(), subasta);
            }
        }
        return filtro;
    }

    @Override
    public ModeloSubasta editarUno(ModeloSubasta model) throws Exception {
        if (!PersistenciaSubastas.subastas.containsKey(model.getId())) {
            throw new Exception("Subasta no encontrada");
        }

        PersistenciaSubastas.subastas.put(model.getId(), model);
        persistenciaSubastas.actualizar();
        return model;
    }

    @Override
    public void eliminarUno(String identificador) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUno'");
    }

    @Override
    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException {
        HashMap<Integer, ModeloSubasta> subastas = persistenciaSubastas.cargar(ruta);
        return subastas.size();
    }

    public ModeloSubasta consultarPorTituloDePieza(String titulo) throws Exception {
        HashMap<Integer, ModeloSubasta> subastas = PersistenciaSubastas.subastas;
        ModeloSubasta subasta = null;
        for (ModeloSubasta s : subastas.values()) {
            if (s.getTituloPieza().equals(titulo) && s.getEstado().equals(ModeloEstadoSubasta.ABIERTA)) {
                subasta = s;
            }
        }
        if (subasta == null) {
            throw new Exception("No se encontró ninguna subasta abierta con ese título");
        }
        return subasta;

    }

    public String consultarEmailGanador(String tituloPieza) throws Exception {
        ModeloSubasta subasta = this.consultarPorTituloDePieza(tituloPieza);
        Integer idSubasta = subasta.getId();
        ModeloOferta ofertaMayor  = logicaOfertas.consultarOfertaMayor(idSubasta);
        if (ofertaMayor == null) {
            throw new Exception("No se encontró ninguna oferta ganadora");
        }
        return ofertaMayor.getEmailOfertante();

    }

    public Double consultarMontoGanador(String titulo) throws Exception {
        ModeloSubasta subasta = this.consultarPorTituloDePieza(titulo);
        Integer idSubasta = subasta.getId();
        ModeloOferta ofertaMayor  = logicaOfertas.consultarOfertaMayor(idSubasta);
        if (ofertaMayor == null) {
            throw new Exception("No se encontró ninguna oferta ganadora");
        }
        return ofertaMayor.getMonto();

    }

}
