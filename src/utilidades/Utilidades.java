package utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import controlador.LogicaOfertas;
import controlador.LogicaPiezas;
import controlador.LogicaSubastas;
import controlador.LogicaUsuarios;
import controlador.LogicaVentas;

public class Utilidades {

	public static void cargarDatos(String ruta) {
		LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
		LogicaPiezas logicaPiezas = new LogicaPiezas();
		LogicaVentas logicaVentas = new LogicaVentas();
		LogicaSubastas logicaSubastas = new LogicaSubastas();
		LogicaOfertas logicaOfertas = new LogicaOfertas();
		try {
			Integer usuariosCargados = logicaUsuarios.cargarDatos(ruta);
			System.out.println("Se cargaron " + usuariosCargados + " usuarios.");
			Integer piezasCargadas = logicaPiezas.cargarDatos(ruta);
			System.out.println("Se cargaron " + piezasCargadas + " piezas.");
			Integer ventasCargadas = logicaVentas.cargarDatos(ruta);
			System.out.println("Se cargaron " + ventasCargadas + " ventas.");
			Integer subastasCargadas = logicaSubastas.cargarDatos(ruta);
			System.out.println("Se cargaron " + subastasCargadas + " subastas.");
			Integer ofertasCargadas = logicaOfertas.cargarDatos(ruta);
			System.out.println("Se cargaron " + ofertasCargadas + " ofertas.");

		} catch (Exception e) {
			System.out.println("Error cargando los datos");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static String lectorConsola(String mensaje) {
		try {

			System.out.print(mensaje);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			return bufferedReader.readLine().strip();

		} catch (IOException e) {

			System.out.println("Error leyendo entrada de datos");
			e.printStackTrace();

		}

		return null;
	}

	public static boolean esNumerico(String str) {
		try {
			Long.parseLong(str); // Cambio para soportar Long
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	// public static <T extends Enum<T>> void printEnumValues(Class<T> enumClass) {
	// T[] values = enumClass.getEnumConstants();
	// for (T value : values) {
	// System.out.print(value + " ");
	// }
	// }

}