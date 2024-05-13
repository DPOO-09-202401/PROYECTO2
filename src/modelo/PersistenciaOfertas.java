package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenciaOfertas implements Persistencia {

    public static ArrayList<ModeloOferta> ofertas = new ArrayList<>();
    public static String cabecera;
    private static String nombreArchivo = "Ofertas.csv";
    private static String ruta;

    @Override
    public HashMap<Integer, ModeloOferta> cargar(String nuevaRuta) throws FileNotFoundException, IOException {
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    public ArrayList<ModeloOferta> cargarOfertas(String nuevaRuta) throws FileNotFoundException, IOException {
        ruta = nuevaRuta;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ruta + nombreArchivo));
        String fila = bufferedReader.readLine(); // cabecera
        cabecera = fila;
        fila = bufferedReader.readLine();
        while (fila != null) {
            String[] partes = fila.split(",");
            // TODO
            Integer idSubasta = Integer.parseInt(partes[0].strip());
            String emailOfertante = partes[1].strip();
            Double cantidad = Double.parseDouble(partes[2].strip());

            ModeloOferta oferta = new ModeloOferta(idSubasta, emailOfertante, cantidad);

            ofertas.add(oferta);
            fila = bufferedReader.readLine();

        }

        bufferedReader.close();
        return ofertas;
    }

    @Override
    public void actualizar() throws IOException {
        FileWriter editor = new FileWriter(ruta + nombreArchivo);

        ArrayList<String[]> filas = new ArrayList<>();

        for (ModeloOferta o : ofertas) {

            String[] datos = {
                    o.getIdSubasta().toString(),
                    o.getEmailOfertante(),
                    o.getMonto().toString(),
            };
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
