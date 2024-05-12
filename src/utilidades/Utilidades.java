package utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controlador.LogicaUsuarios;

public class Utilidades {

	public static void cargarDatos(String ruta) {
		LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
        try {
            Integer usuariosCargados = logicaUsuarios.cargarDatos(ruta);
            System.out.println("Se cargaron " + usuariosCargados + " usuarios.");
        } catch (Exception e) {
            System.out.println("Error cargando los datos");
            System.out.println(e.getMessage());
        }
    }

    public static String lectorConsola(String mensaje){
        try {
			
			System.out.print(mensaje);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			return bufferedReader.readLine().strip();
			
		} catch(IOException e) {
			
			System.out.println("Error leyendo entrada de datos");
			e.printStackTrace();
			
		}
		
		return null;
    }

}