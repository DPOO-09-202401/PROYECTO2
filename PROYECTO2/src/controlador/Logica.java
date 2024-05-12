package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public interface Logica<T> {
    public T crearUno(T nuevo) throws Exception;

    public T consultarUno(String identificador) throws Exception;

    public HashMap<String, T> consultarTodos();

    public T editarUno();

    public void eliminarUno(String identificador) throws Exception;

    public Integer cargarDatos(String ruta) throws FileNotFoundException, IOException;


}