package modelo;

public class ModeloOferta {
    Integer idSubasta;
    String emailOfertante;
    Double cantidad;

    public ModeloOferta(Integer idSubasta, String emailOfertante, Double cantidad) {
        this.idSubasta = idSubasta;
        this.emailOfertante = emailOfertante;
        this.cantidad = cantidad;
    }
    public Integer getIdSubasta() {
        return idSubasta;
    }
    public void setIdSubasta(Integer idSubasta) {
        this.idSubasta = idSubasta;
    }
    public String getEmailOfertante() {
        return emailOfertante;
    }
    public void setEmailOfertante(String emailOfertante) {
        this.emailOfertante = emailOfertante;
    }
    public Double getMonto() {
        return cantidad;
    }
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    
    
}
