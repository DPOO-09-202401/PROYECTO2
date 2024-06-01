package vistaGrafica;

public enum NombresVistas {
    LOGIN("LOGIN"),
    ADMIN("ADMIN"),
    USUARIO("CLIENTE"),
    EMPLEADO("EMPLEADO");

    private String nombre;

    private NombresVistas(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }}
}
