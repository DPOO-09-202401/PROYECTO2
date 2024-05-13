package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controlador.LogicaVentas;
import modelo.ModeloVenta;

class TestLogicaVentas {

    private LogicaVentas logicaVentas;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        logicaVentas = new LogicaVentas();
    }

    @Test
    void testCrearUno() {
        try {
            ModeloVenta venta = new ModeloVenta(6, "Sample Title", "cliente@email.com", "cliente@email.com", 100.5, LocalDate.now(), "EFECTIVO", "VENTA_DIRECTA", true);
            ModeloVenta ventaCreada = logicaVentas.crearUno(venta);
            assertNotNull(ventaCreada, "Se esperaba que se creara la venta correctamente");
            assertNotNull(ventaCreada.getId(), "Se esperaba que se asignara un ID a la venta creada");
            assertEquals(venta.getTituloPieza(), ventaCreada.getTituloPieza(), "El título de la venta creada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al crear la venta: " + e.getMessage());
        }
    }

    @Test
    void testConsultarUno() {
        try {
            ModeloVenta ventaConsultada = logicaVentas.consultarUno("1");
            assertNotNull(ventaConsultada, "Se esperaba que se encontrara la venta");
            assertEquals("Sample Title", ventaConsultada.getTituloPieza(), "El título de la venta consultada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar la venta: " + e.getMessage());
        }
    }

}
