package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenciaUsuario implements Persistencia{
    public static HashMap<String, ModeloUsuario> usuarios = new HashMap<>();
    public static String cabecera;
    private static String nombreArchivo = "Usuarios.csv";
    private static String ruta;
    @Override
    public HashMap<String, ModeloUsuario> cargar(String nuevaRuta) throws IOException, FileNotFoundException {
        ruta = nuevaRuta;
        BufferedReader bufferedReader = new BufferedReader (new FileReader(ruta  + nombreArchivo));
		String fila = bufferedReader.readLine(); //cabecera
		cabecera = fila;
		fila = bufferedReader.readLine();
		while (fila != null) {
		String[] partes = fila.split(",");
		String email = partes[0].strip();
		String nombre = partes[1].strip();
		ModeloRol rol = ModeloRol.valueOf(partes[2].strip());
		String contrasena = partes[3].strip();
		String telefono = partes[4].strip();
		
		ModeloUsuario usuario = new ModeloUsuario(nombre, email, rol, contrasena, telefono);
		if (usuario.getRol().equals(ModeloRol.CLIENTE)){
            Boolean autorizado = Boolean.parseBoolean(partes[5].strip());
            Double limitePago = Double.parseDouble(partes[6].strip());
            usuario.setAutorizado(autorizado);
            usuario.setLimitePago(limitePago);
        }
		
		usuarios.put(email, usuario);
		fila = bufferedReader.readLine();
		
		}

		bufferedReader.close();
		return usuarios;
    }

    @Override
    public void actualizar() throws IOException, FileNotFoundException {
        FileWriter editor = new FileWriter(ruta + nombreArchivo);
        
        ArrayList<String[]> filas = new ArrayList<>();
        
        for (ModeloUsuario u : usuarios.values()) {
            String autorizado = "null";
            if (!(u.getAutorizado() == null)){
                autorizado = u.getAutorizado().toString();
            }
            String limitePago = "null";
            if (!(u.getLimitePago() == null)){
                limitePago = u.getLimitePago().toString();
            }
            String[] fila = {
                u.getCorreo(),
                u.getNombre(),
                u.getRol().toString(),
                u.getContrasena(),
                u.getTelefono(),
                autorizado,
                limitePago,
            };
            
            filas.add(fila);
        }
        
        editor.write(cabecera + "\n");

        for (String[] fila : filas) {
            String row = String.join(",", fila);
            editor.append(row + "\n");
        }

        editor.flush();
		editor.close();
    }
    
}
