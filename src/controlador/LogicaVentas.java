package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import modelo.ModeloVenta;
import modelo.PersistenciaPieza;
import modelo.PersistenciaVentas;

public class LogicaVentas implements Logica<ModeloVenta> {

    private PersistenciaVentas persistenciaVentas = new PersistenciaVentas();

    @Override
    public ModeloVenta crearUno(ModeloVenta nuevo) throws Exception {

        HashMap<Integer, ModeloVenta> ventas = PersistenciaVentas.ventas;

        if (nuevo.getId() == null) {
            throw new Exception("Se requiere un id para registrar una venta");
        }

        if (ventas.containsKey(nuevo.getId())) {
            throw new Exception("Ya existe una venta con el id " + nuevo.getId());
        }
        PersistenciaVentas.ventas.put(nuevo.getId(), nuevo);
        persistenciaVentas.actualizar();
        return nuevo;
    }

    @Override
    public ModeloVenta consultarUno(String identificador) throws Exception {
        Integer id = Integer.parseInt(identificador);
        ModeloVenta venta = PersistenciaVentas.ventas.get(id);
        if (venta == null) {
            throw new Exception("No se encontró la venta con id " + id);
        }
        return venta;
    }

    @Override
    public HashMap<String, ModeloVenta> consultarTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultarTodos'");
    }

    @Override
    public ModeloVenta editarUno(ModeloVenta venta) throws Exception {
        if (!PersistenciaVentas.ventas.containsKey(venta.getId())) {
            throw new Exception("Venta no encontrada");
        }

        PersistenciaVentas.ventas.put(venta.getId(), venta);
        persistenciaVentas.actualizar();
        return venta;

    }

    @Override
    public void eliminarUno(String identificador) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUno'");
    }

    @Override
    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException {
        return persistenciaVentas.cargar(ruta).size();
    }

    public ModeloVenta consultarPagoMasRecientePorPieza(String titulo){
        HashMap<Integer, ModeloVenta> ventas = consultarPorPieza(titulo);
        ModeloVenta masReciente = null;
        for (ModeloVenta venta : ventas.values()) {
            if (masReciente == null || venta.getFecha().isAfter(masReciente.getFecha())) {
                masReciente = venta;
            }
        }
        return masReciente;
    }

    public HashMap<Integer, ModeloVenta> consultarPorPieza(String titulo) {
        HashMap<Integer, ModeloVenta> filtro = new HashMap<>();
        for (ModeloVenta venta : PersistenciaVentas.ventas.values()) {
            if (venta.getTituloPieza().equals(titulo)) {
                filtro.put(venta.getId(), venta);
            }
        }
        return filtro;
    }

    public ArrayList<ModeloVenta> consultarHistorialDePieza(String tituloPieza){
        HashMap<Integer, ModeloVenta> ventas = consultarPorPieza(tituloPieza);
        ArrayList<ModeloVenta> historial = new ArrayList<>();
        for (ModeloVenta venta : ventas.values()) {
            historial.add(venta);
        }

        return historial;

    }

}
