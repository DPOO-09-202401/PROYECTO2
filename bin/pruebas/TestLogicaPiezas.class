package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controlador.LogicaPiezas;
import modelo.ModeloPiezas;
import modelo.ModeloRol;
import modelo.ModeloVenta;

class TestLogicaPiezas {

    private LogicaPiezas logicaPiezas;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        logicaPiezas = new LogicaPiezas();
    }

    @Test
    void testCrearUno() {
        try {
            ModeloPiezas nuevaPieza = new ModeloPiezas("Yet Another Title", "Michael Johnson", "cliente@email.com", "VIDEO", "material7 material8 material9", false, 150.25, ModeloVenta.VENDIDA, null, null);
            ModeloPiezas piezaCreada = logicaPiezas.crearUno(nuevaPieza);
            assertNotNull(piezaCreada, "Se esperaba que se creara la pieza correctamente");
            assertEquals("Yet Another Title", piezaCreada.getTitulo(), "El título de la pieza creada no coincide");
            assertEquals("Michael Johnson", piezaCreada.getNombreAutor(), "El nombre del autor de la pieza creada no coincide");
            assertEquals("cliente@email.com", piezaCreada.getEmailDueno(), "El correo del dueño de la pieza creada no coincide");
            assertEquals("VIDEO", piezaCreada.getTipo(), "El tipo de la pieza creada no coincide");
            assertEquals("material7 material8 material9", piezaCreada.getMateriales(), "Los materiales de la pieza creada no coinciden");
            assertFalse(piezaCreada.getBloqueda(), "Se esperaba que la pieza creada no esté bloqueada");
            assertEquals(150.25, piezaCreada.getPrecioMinimo(), 0.001, "El precio mínimo de la pieza creada no coincide");
            assertEquals(ModeloVenta.VENDIDA, piezaCreada.getEstado(), "El estado de la pieza creada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al crear la pieza: " + e.getMessage());
        }
    }

    @Test
    void testConsultarUno() {
        try {
            ModeloPiezas piezaConsultada = logicaPiezas.consultarUno("Yet Another Title");
            assertNotNull(piezaConsultada, "Se esperaba encontrar una pieza");
            assertEquals("Michael Johnson", piezaConsultada.getNombreAutor(), "El nombre del autor de la pieza consultada no coincide");
            assertEquals("cliente@email.com", piezaConsultada.getEmailDueno(), "El correo del dueño de la pieza consultada no coincide");
            assertEquals("VIDEO", piezaConsultada.getTipo(), "El tipo de la pieza consultada no coincide");
            assertEquals("material7 material8 material9", piezaConsultada.getMateriales(), "Los materiales de la pieza consultada no coinciden");
            assertFalse(piezaConsultada.getBloqueda(), "Se esperaba que la pieza consultada no esté bloqueada");
            assertEquals(150.25, piezaConsultada.getPrecioMinimo(), 0.001, "El precio mínimo de la pieza consultada no coincide");
            assertEquals(ModeloVenta.VENDIDA, piezaConsultada.getEstado(), "El estado de la pieza consultada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar la pieza: " + e.getMessage());
        }
    }

    @Test
    void testConsultarTodos() {
        HashMap<String, ModeloPiezas> piezas = logicaPiezas.consultarTodos();
        assertNotNull(piezas, "La lista de piezas no debería ser nula");
        assertTrue(piezas.size() > 0, "Se esperaba que la lista de piezas no esté vacía");
    }

    @Test
    void testEditarUno() {
        try {
            ModeloPiezas pieza = logicaPiezas.consultarUno("Yet Another Title");
            pieza.setPrecioMinimo(200.0);
            ModeloPiezas piezaEditada = logicaPiezas.editarUno(pieza);
            assertEquals(200.0, piezaEditada.getPrecioMinimo(), 0.001, "El precio mínimo de la pieza editada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al editar la pieza: " + e.getMessage());
        }
    }

    @Test
    void testCargarDatos() {
        try {
            Integer cantidadPiezasCargadas = logicaPiezas.cargarDatos("datos_piezas.csv");
            assertTrue(cantidadPiezasCargadas > 0, "Se esperaba cargar al menos una pieza desde el archivo");
        } catch (FileNotFoundException e) {
            fail("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            fail("Error de lectura/escritura: " + e.getMessage());
        }
    }

    @Test
    void testVerificarDisponible() {
        try {
            ModeloPiezas pieza = new ModeloPiezas("le mondieu'", "mondoso", "pepe@email.com", "FOTOGRAFIA", "belleza pasion", false, 8.8888888E7, ModeloVenta.EXHIBICION, null, null);
            logicaPiezas.verificarDisponible(pieza);
        } catch (Exception e) {
            fail("Se produjo un error al verificar disponibilidad de la pieza: " + e.getMessage());
        }
    }

    @Test
    void testConsultarPorAutor() {
        HashMap<String, ModeloPiezas> piezasPorAutor = logicaPiezas.consultarPorAutor("mondoso");
        assertNotNull(piezasPorAutor, "La lista de piezas por autor no debería ser nula");
        assertTrue(piezasPorAutor.size() > 0, "Se esperaba que la lista de piezas por autor no esté vacía");
    }

}
