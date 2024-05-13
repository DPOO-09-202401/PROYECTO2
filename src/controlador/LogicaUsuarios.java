package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import modelo.ModeloRol;
import modelo.ModeloUsuario;
import modelo.PersistenciaUsuario;
import utilidades.Utilidades;

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
        HashMap<String, ModeloUsuario> usuarios =  PersistenciaUsuario.usuarios;
        return usuarios;
    }
    
    public HashMap<String, ModeloUsuario> consultarClientes(){
        HashMap<String, ModeloUsuario> usuarios =  PersistenciaUsuario.usuarios;
        HashMap<String, ModeloUsuario> filtro = new HashMap<>();
        for (ModeloUsuario u: usuarios.values()){
            if (u.getRol().equals(ModeloRol.CLIENTE)){
                filtro.put(u.getCorreo(), u);
            }
        }
        return filtro;
    }

    @Override
    public ModeloUsuario editarUno(ModeloUsuario usuario) throws Exception {
        if (!PersistenciaUsuario.usuarios.containsKey(usuario.getCorreo())){
            throw new Exception("Usuario no encontrado");
        };
        PersistenciaUsuario.usuarios.put(usuario.getCorreo(), usuario);
        persistenciaUsuario.actualizar();
        return usuario;
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
        if (nuevoUsuario.getTelefono().length() != 10){
            throw new Exception("El telefono debe tener 10 dígitos");
        }
        if (!Utilidades.esNumerico(nuevoUsuario.getTelefono())){
            throw new Exception("El telefono debe ser numérico");
        }
        
        PersistenciaUsuario.usuarios.put(nuevoUsuario.getCorreo(), nuevoUsuario);
        persistenciaUsuario.actualizar();
        return nuevoUsuario;
    }



}