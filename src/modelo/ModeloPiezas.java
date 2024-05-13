package modelo;

import java.util.ArrayList;

public class ModeloPiezas {

    private String titulo;
    private String nombreAutor;
    private String emailDueno;
    private ModeloTiposPieza tipo;
    private ArrayList<String> materiales;
    private Boolean bloqueda;
    private Double precioMinimo;
    private ModeloEstado estado;
    private String emailOfertante;
    private ModeloTipoDeVenta tipoDeVenta;

    public ModeloPiezas(String titulo, String nombreAutor, String emailDueno, ModeloTiposPieza tipo,
            ArrayList<String> materiales, Boolean bloqueada, Double precioMinimo, ModeloEstado estado,
            String emailOfertante, ModeloTipoDeVenta tipoDeVenta) throws Exception {
        ModeloUsuario usuarioDueno = PersistenciaUsuario.usuarios.get(emailDueno);
        if (usuarioDueno == null) {
            throw new Exception("No se encontró un usuario con el email: " + emailDueno);
        } else if (!usuarioDueno.getRol().equals(ModeloRol.CLIENTE)) {
            throw new Exception("El usuario no es un cliente");
        }
        if (emailOfertante != null && bloqueada) {
            ModeloUsuario usuarioOfertante = PersistenciaUsuario.usuarios.get(emailOfertante);
            if (usuarioOfertante == null) {
                throw new Exception("No se encontró un usuario con el email: " + emailOfertante);
            } else if (!usuarioOfertante.getRol().equals(ModeloRol.CLIENTE)) {
                throw new Exception("El usuario no es un cliente");
            }
        }
        if (tipoDeVenta != null && (estado.equals(ModeloEstado.DEVUELTA) || estado.equals(ModeloEstado.VENDIDA))) {
            throw new Exception ("Si la pieza ya ha sido devuelta o vendida, no puede tener un tipo de venta permitido.");
        }

        this.titulo = titulo;
        this.nombreAutor = nombreAutor;
        this.emailDueno = emailDueno;
        this.tipo = tipo;
        this.materiales = materiales;
        this.bloqueda = bloqueada;
        this.precioMinimo = precioMinimo;
        this.estado = estado;
        this.emailOfertante = emailOfertante;
        this.tipoDeVenta = tipoDeVenta;
    }

    public ModeloEstado getEstado() {
        return estado;
    }

    public void setEstado(ModeloEstado estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getEmailDueno() {
        return emailDueno;
    }

    public void setEmailDueno(String emailDueno) throws Exception {
        if (PersistenciaUsuario.usuarios.get(emailOfertante) == null) {
            throw new Exception("Cliente dueño no existe");
        }
        this.emailDueno = emailDueno;
    }

    public ModeloTiposPieza getTipo() {
        return tipo;
    }

    public void setTipo(ModeloTiposPieza tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getMateriales() {
        return materiales;
    }

    public void setMateriales(ArrayList<String> materiales) {
        this.materiales = materiales;
    }

    public Boolean getBloqueda() {
        return bloqueda;
    }

    public void setBloqueda(Boolean bloqueda) {
        this.bloqueda = bloqueda;
    }

    public Double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public String getEmailOfertante() {
        return emailOfertante;
    }

    public void setEmailOfertante(String emailOfertante) throws Exception {
        if (emailOfertante != null && PersistenciaUsuario.usuarios.get(emailOfertante) == null) {
            throw new Exception("Cliente ofertante no existe");
        }
        this.emailOfertante = emailOfertante;
    }

    public ModeloTipoDeVenta getTipoDeVenta() {
        return tipoDeVenta;
    }

    public void setTipoDeVenta(ModeloTipoDeVenta tipoDeVenta) {
        this.tipoDeVenta = tipoDeVenta;
    }

}
