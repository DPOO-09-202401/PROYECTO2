package modelo;

import java.time.LocalDate;
import java.util.HashMap;

public class    ModeloSubasta {
    Integer id;
    LocalDate fechaInicio;
    String tituloPieza;
    Double minimo;
    ModeloEstadoSubasta estado;

    public ModeloSubasta(LocalDate fechaInicio, String tituloPieza, ModeloEstadoSubasta estado, Double minimo) {
        this.fechaInicio = fechaInicio;
        this.tituloPieza = tituloPieza;
        this.estado = estado;
        this.minimo = minimo;
        this.asignarId();
    }

    public ModeloSubasta(Integer id, LocalDate fechaInicio, String tituloPieza, ModeloEstadoSubasta estado, Double minimo) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.tituloPieza = tituloPieza;
        this.estado = estado;
        this.minimo = minimo;
    }

    public void asignarId() {
        HashMap<Integer, ModeloSubasta> subastas = PersistenciaSubastas.subastas;
        Integer max = 0;
        for (ModeloSubasta s : subastas.values()) {
            if (s.getId() > max) {
                max = s.getId();
            }
        }
        this.id = max + 1;
    }

    

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getTituloPieza() {
        return tituloPieza;
    }
    public void setTituloPieza(String tituloPieza) {
        this.tituloPieza = tituloPieza;
    }
    public ModeloEstadoSubasta getEstado() {
        return estado;
    }
    public void setEstado(ModeloEstadoSubasta estado) {
        this.estado = estado;
    }

    public Double getMontoInicial() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    
}
