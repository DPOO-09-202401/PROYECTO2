package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PersistenciaPieza implements Persistencia {

	public static HashMap<String, ModeloPiezas> piezas = new HashMap<>();
	public static String cabecera;
	private static String nombreArchivo = "Piezas.csv";
	private static String ruta;

	@Override
	public HashMap<String, ModeloPiezas> cargar(String nuevaRuta) throws FileNotFoundException, IOException {
		ruta = nuevaRuta;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(ruta + nombreArchivo));
		String fila = bufferedReader.readLine(); // cabecera
		cabecera = fila;
		fila = bufferedReader.readLine();
		while (fila != null) {
			String[] partes = fila.split(",");
			String titulo = partes[0].strip();
			String nombreAutor = partes[1].strip();
			String emailDueno = partes[2].strip();
			ModeloTiposPieza tipo = ModeloTiposPieza.valueOf(partes[3].strip());

			String materiales = partes[4].strip();
			ArrayList<String> materialesList = new ArrayList<>(Arrays.asList(materiales.split(" ")));

			Boolean bloqueada = Boolean.parseBoolean(partes[5].strip());
			Double precioMinimo = Double.parseDouble(partes[6].strip());

			ModeloEstado estado = ModeloEstado.valueOf(partes[7].strip());
			String emailOfertante = partes[8].strip();
			if (emailOfertante.equals("null")) {
				emailOfertante = null;
			}
			ModeloTipoDeVenta tipoDeVenta = null;
			try {
				if (!partes[9].strip().equals("null")) {
					tipoDeVenta = ModeloTipoDeVenta.valueOf(partes[9].strip());

				}
			} catch (Exception e) {
				System.out.println("Error interpretando tipo de venta. Debe tomar un valor entre: "
						+ Arrays.asList(ModeloTipoDeVenta.values()));
				tipoDeVenta = null;
				fila = bufferedReader.readLine();
				continue;
			}

			ModeloPiezas pieza;
			try {
				pieza = new ModeloPiezas(titulo, nombreAutor, emailDueno, tipo, materialesList, bloqueada, precioMinimo,
						estado, emailOfertante, tipoDeVenta);
				piezas.put(titulo, pieza);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			fila = bufferedReader.readLine();
		}

		bufferedReader.close();
		return piezas;
	}

	@Override
	public void actualizar() throws FileNotFoundException, IOException {
		FileWriter editor = new FileWriter(ruta + nombreArchivo);

		ArrayList<String[]> filas = new ArrayList<>();

		for (ModeloPiezas p : piezas.values()) {
			String tipoDeVenta;
			if (p.getTipoDeVenta() == null) {
				tipoDeVenta = "null";
			} else {
				tipoDeVenta = p.getTipoDeVenta().toString();
			}
			String[] datos = {
					p.getTitulo(),
					p.getNombreAutor(),
					p.getEmailDueno(),
					p.getTipo().toString(),
					String.join(" ", p.getMateriales()),
					p.getBloqueda().toString(),
					p.getPrecioMinimo().toString(),
					p.getEstado().toString(),
					p.getEmailOfertante(),
					tipoDeVenta };
			filas.add(datos);
		}

		editor.write(cabecera + "\n");

		for (String[] fila : filas) {
			String filaStr = String.join(",", fila);
			editor.append(filaStr + "\n");
		}

		editor.flush();
		editor.close();
	}

}
