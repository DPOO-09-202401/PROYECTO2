package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PersistenciaSubastas implements Persistencia {

    public static HashMap<Integer, ModeloSubasta> subastas = new HashMap<>();
    public static String cabecera;
    private static String nombreArchivo = "Subastas.csv";
    private static String ruta;

    @Override
    public HashMap<Integer, ModeloSubasta> cargar(String nuevaRuta) throws FileNotFoundException, IOException {
        ruta = nuevaRuta;
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ruta + nombreArchivo));
        String fila = bufferedReader.readLine(); // cabecera
        cabecera = fila;
        fila = bufferedReader.readLine();
        while (fila != null) {
            String[] partes = fila.split(",");
            // TODO
            Integer id = Integer.parseInt(partes[0].strip());
            LocalDate fecha = LocalDate.parse(partes[1].strip());
            String tituloPieza = partes[2].strip();
            Double minimo = Double.parseDouble(partes[3].strip());
            ModeloEstadoSubasta estado = ModeloEstadoSubasta.valueOf(partes[4].strip());
            
            ModeloSubasta venta = new ModeloSubasta(id, fecha, tituloPieza, estado, minimo);
            
            subastas.put(id, venta);
            fila = bufferedReader.readLine();

        }

        bufferedReader.close();
        return subastas;
    }

    @Override
    public void actualizar() throws IOException {
        FileWriter editor = new FileWriter(ruta + nombreArchivo);

        ArrayList<String[]> filas = new ArrayList<>();

        
        for (ModeloSubasta s : subastas.values()) {
            String[] datos = {
                s.getId().toString(),
                s.getFechaInicio().toString(),
                s.getTituloPieza(),
                s.getMontoInicial().toString(),
                s.getEstado().toString()
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
