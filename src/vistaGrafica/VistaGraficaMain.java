package vistaGrafica;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import controlador.LogicaUsuarios;
import modelo.ModeloRol;
import utilidades.Utilidades;
import vistaGrafica.vistasAdmin.ConsultarUsuarios;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VistaGraficaMain extends JFrame implements ActionListener{

    private static String ruta = "./src/datos/";
    private JPanel vistas;
    private String vistaActual = NombresVistas.LOGIN.getNombre();
    private String email;
    private String contrasena;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

			try {
				UIManager.setLookAndFeel(new FlatLightLaf());
				FlatLightLaf.setPreferredSemiboldFontFamily("Segoe UI Semibold");
			} catch (Exception ex) {
				System.err.println("Failed to initialize LaF");
			}
            VistaGraficaMain vista = new VistaGraficaMain();
            vista.comenzar();
		});
        
    }

    public void setEmail(String email) {
        this.email = email;
        System.out.println("Email: " + email);
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
        System.out.println("Contrasena: " + contrasena);
    }

    private  void comenzar() {
        Utilidades.cargarDatos(ruta);
        vistas = new JPanel();
        vistas.setLayout(new CardLayout());

        //AGREGAR VISTAS
        VistaLogin vistaLogin = new VistaLogin(this, this);
        vistas.add(vistaLogin, NombresVistas.LOGIN.getNombre());
        VistaMenuAdmin vistaMenuAdmin = new VistaMenuAdmin(this);
        vistas.add(vistaMenuAdmin, NombresVistas.ADMIN.getNombre());
        VistaMenuCliente vistaMenuCliente = new VistaMenuCliente(this);
        vistas.add(vistaMenuCliente, NombresVistas.USUARIO.getNombre());
        VistaMenuEmpleado vistaMenuEmpleado = new VistaMenuEmpleado(this);
        vistas.add(vistaMenuEmpleado, NombresVistas.EMPLEADO.getNombre());
        ConsultarUsuarios consultarUsuarios = new ConsultarUsuarios(this);
        vistas.add(consultarUsuarios, "ADMIN1");
        //FIJAR 
        CardLayout cl = (CardLayout) (vistas.getLayout());
		cl.show(vistas, vistaActual);
        this.add(vistas);

        setSize(500, 500);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        LogicaUsuarios logicaUsuarios = new LogicaUsuarios();

        String comandoRol = e.getActionCommand();
        ModeloRol rol = ModeloRol.ADMINISTRADOR;
        switch (comandoRol) {
            case "ADMIN":
                rol = ModeloRol.ADMINISTRADOR;
                break;
            case "CLIENTE":
                rol = ModeloRol.CLIENTE;
                break;
            case "EMPLEADO":
                rol = ModeloRol.EMPLEADO;
                break;
            default:
                rol = ModeloRol.ADMINISTRADOR;
                break;
        }
        if (logicaUsuarios.iniciarSesion(email, contrasena, rol) == null) {
            System.out.println("Autenticacion fallida");
            JOptionPane.showMessageDialog(null, "Error en la autenticación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        vistaActual = comandoRol;
        CardLayout cl = (CardLayout) (vistas.getLayout());
        cl.show(vistas, vistaActual);
        
    }}

}
