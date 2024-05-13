package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import modelo.ModeloUsuario;

class TestLogicaUsuarios {

    private LogicaUsuarios logicaUsuarios;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        logicaUsuarios = new LogicaUsuarios();
    }

    @Test
    void testIniciarSesion() {
        ModeloUsuario usuario = logicaUsuarios.iniciarSesion("admin@email.com", "123", ModeloRol.ADMINISTRADOR);
        assertNotNull(usuario, "Se esperaba que el usuario iniciara sesión correctamente");
        assertEquals("admin@email.com", usuario.getCorreo(), "El correo del usuario no coincide");
        assertEquals(ModeloRol.ADMINISTRADOR, usuario.getRol(), "El rol del usuario no coincide");
    }

    @Test
    void testConsultarUno() {
        try {
            ModeloUsuario usuario = logicaUsuarios.consultarUno("admin@email.com");
            assertNotNull(usuario, "Se esperaba encontrar un usuario");
            assertEquals("admin@email.com", usuario.getCorreo(), "El correo del usuario no coincide");
            assertEquals("John Admin", usuario.getNombre(), "El nombre del usuario no coincide");
            assertEquals("ADMINISTRADOR", usuario.getRol().name(), "El rol del usuario no coincide");
            assertEquals("1234567890", usuario.getTelefono(), "El teléfono del usuario no coincide");
            assertNull(usuario.getAutorizado(), "El usuario no debería tener campo 'autorizado'");
            assertNull(usuario.getLimitePago(), "El usuario no debería tener campo 'limitePago'");
        } catch (Exception e) {
            fail("Se produjo un error al consultar el usuario: " + e.getMessage());
        }
    }

    @Test
    void testConsultarTodos() {
        HashMap<String, ModeloUsuario> usuarios = logicaUsuarios.consultarTodos();
        assertNotNull(usuarios, "La lista de usuarios no debería ser nula");
        assertTrue(usuarios.size() > 0, "Se esperaba que la lista de usuarios no esté vacía");
    }

    @Test
    void testConsultarClientes() {
        HashMap<String, ModeloUsuario> clientes = logicaUsuarios.consultarClientes();
        assertNotNull(clientes, "La lista de clientes no debería ser nula");
        assertTrue(clientes.size() > 0, "Se esperaba que la lista de clientes no esté vacía");
    }

    @Test
    void testEditarUno() {
        try {
            ModeloUsuario usuario = logicaUsuarios.consultarUno("admin@email.com");
            usuario.setNombre("Nuevo Nombre");
            ModeloUsuario usuarioEditado = logicaUsuarios.editarUno(usuario);
            assertEquals("Nuevo Nombre", usuarioEditado.getNombre(), "El nombre del usuario editado no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al editar el usuario: " + e.getMessage());
        }
    }

    @Test
    void testEliminarUno() {
        try {
            logicaUsuarios.eliminarUno("pepe@email.com");
            assertNull(logicaUsuarios.consultarUno("pepe@email.com"), "Se esperaba que el usuario se eliminara correctamente");
        } catch (Exception e) {
            fail("Se produjo un error al eliminar el usuario: " + e.getMessage());
        }
    }

    @Test
    void testCargarDatos() {
        try {
            Integer cantidadUsuariosCargados = logicaUsuarios.cargarDatos("datos_usuarios.csv");
            assertTrue(cantidadUsuariosCargados > 0, "Se esperaba cargar al menos un usuario desde el archivo");
        } catch (FileNotFoundException e) {
            fail("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            fail("Error de lectura/escritura: " + e.getMessage());
        }
    }

    @Test
    void testCrearUno() {
        try {
            ModeloUsuario nuevoUsuario = new ModeloUsuario("nuevo@email.com", "Nuevo Usuario", ModeloRol.CLIENTE, "123", "1234567890", true, 999999999999999.0);
            ModeloUsuario usuarioCreado = logicaUsuarios.crearUno(nuevoUsuario);
            assertNotNull(usuarioCreado, "Se esperaba que se creara el usuario correctamente");
            assertEquals("nuevo@email.com", usuarioCreado.getCorreo(), "El correo del usuario creado no coincide");
            assertEquals("Nuevo Usuario", usuarioCreado.getNombre(), "El nombre del usuario creado no coincide");
            assertEquals(ModeloRol.CLIENTE, usuarioCreado.getRol(), "El rol del usuario creado no coincide");
        } catch (Exception e) {
            fail("Se produjo un error al crear el usuario: " + e.getMessage());
        }
    }

}
