package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import modelo.ModeloRol;
import modelo.ModeloUsuario;
import modelo.PersistenciaUsuario;

public class LogicaUsuarios implements Logica<ModeloUsuario>{

    private PersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();

    public ModeloUsuario iniciarSesion(String correo, String contrasena, ModeloRol rol) {
        ModeloUsuario modeloUsuario = PersistenciaUsuario.usuarios.get(correo);
        if (modeloUsuario != null && modeloUsuario.getRol().equals(rol)) {
            String thisContrasena = modeloUsuario.getContrasena();
            if (thisContrasena.equals(contrasena)) {
                return modeloUsuario;
            }
        }
        return null;
    }

    @Override
    public ModeloUsuario consultarUno(String identificador) throws Exception {
        ModeloUsuario usuario =  PersistenciaUsuario.usuarios.get(identificador);
        if (usuario == null){
            throw new Exception ("Usuario no encontrado");
        }
        return usuario;
    }

    @Override
    public HashMap<String, ModeloUsuario> consultarTodos() {
        HashMap<String, ModeloUsuario> usuarios =  persistenciaUsuario.usuarios;
        return usuarios;
    }

    @Override
    public ModeloUsuario editarUno() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarUno'");
    }

    @Override
    public void eliminarUno(String identificador) throws Exception {
        if (! PersistenciaUsuario.usuarios.containsKey(identificador)){
            throw new Exception("Usuario no encontrado");
        }
        PersistenciaUsuario.usuarios.remove(identificador);
        persistenciaUsuario.actualizar();
    }

    @Override
    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException {
        persistenciaUsuario.cargar(ruta);
        HashMap<String, ModeloUsuario> usuarios =  PersistenciaUsuario.usuarios;
        return usuarios.size();

    }

    @Override
    public ModeloUsuario crearUno(ModeloUsuario nuevoUsuario) throws Exception {
        if (PersistenciaUsuario.usuarios.containsKey(nuevoUsuario.getContrasena())){
            throw new Exception("Correo duplicado");
        };
        PersistenciaUsuario.usuarios.put(nuevoUsuario.getCorreo(), nuevoUsuario);
        persistenciaUsuario.actualizar();
        return nuevoUsuario;
    }



}