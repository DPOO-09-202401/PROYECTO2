package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controlador.LogicaSubastas;
import modelo.ModeloEstadoSubasta;
import modelo.ModeloSubasta;

class TestLogicaSubastas {

    private LogicaSubastas logicaSubastas;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        logicaSubastas = new LogicaSubastas();
    }

    @Test
    void testCrearUno() {
        try {
            ModeloSubasta subasta = new ModeloSubasta(null, LocalDate.now(), "la monda", 3.0, ModeloEstadoSubasta.ABIERTA);
            ModeloSubasta subastaCreada = logicaSubastas.crearUno(subasta);
            assertNotNull(subastaCreada, "Se esperaba que se creara la subasta correctamente");
            assertNotNull(subastaCreada.getId(), "Se esperaba que se asignara un ID a la subasta creada");
            assertEquals(subasta.getTituloPieza(), subastaCreada.getTituloPieza(), "El título de la subasta creada no coincide");
            assertEquals(subasta.getEstado(), subastaCreada.getEstado(), "El estado de la subasta creada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al crear la subasta: " + e.getMessage());
        }
    }

    @Test
    void testConsultarUno() {
        try {
            ModeloSubasta subastaConsultada = logicaSubastas.consultarUno("1");
            assertNotNull(subastaConsultada, "Se esperaba que se encontrara la subasta");
            assertEquals("la monda", subastaConsultada.getTituloPieza(), "El título de la subasta consultada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar la subasta: " + e.getMessage());
        }
    }

    @Test
    void testConsultarAbiertos() {
        try {
            HashMap<Integer, ModeloSubasta> subastasAbiertas = logicaSubastas.consultarAbiertos();
            assertFalse(subastasAbiertas.isEmpty(), "Se esperaba que se encontraran subastas abiertas");
            assertTrue(subastasAbiertas.size() > 0, "Se esperaba que hubiera más de una subasta abierta");
        } catch (Exception e) {
            fail("Se produjo un error al consultar las subastas abiertas: " + e.getMessage());
        }
    }

    @Test
    void testConsultarPorTituloDePieza() {
        try {
            ModeloSubasta subastaConsultada = logicaSubastas.consultarPorTituloDePieza("remonda");
            assertNotNull(subastaConsultada, "Se esperaba que se encontrara la subasta");
            assertEquals("CERRADA", subastaConsultada.getEstado().toString(), "El estado de la subasta consultada no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar la subasta por título de pieza: " + e.getMessage());
        }
    }

    @Test
    void testConsultarEmailGanador() {
        try {
            String emailGanador = logicaSubastas.consultarEmailGanador("la monda");
            assertNotNull(emailGanador, "Se esperaba que se encontrara el email del ganador");
            assertEquals("cliente@email.com", emailGanador, "El email del ganador no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar el email del ganador de la subasta: " + e.getMessage());
        }
    }

    @Test
    void testConsultarMontoGanador() {
        try {
            Double montoGanador = logicaSubastas.consultarMontoGanador("remonda");
            assertNotNull(montoGanador, "Se esperaba que se encontrara el monto del ganador");
            assertEquals(5.0, montoGanador, "El monto del ganador no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al consultar el monto del ganador de la subasta: " + e.getMessage());
        }
    }
}
