package vistaGrafica;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class VistaLogin extends JPanel {
    private JLabel titulo2;
    private JLabel titulo1;
    private JTextField usuario;
    private JPasswordField contrasena;
    private JButton botonConfirmar;

    public VistaLogin(ActionListener padre, VistaGraficaMain vistaGraficaMain) {
        BoxLayout layoutLogin = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(layoutLogin);
        setBorder(new javax.swing.border.EmptyBorder(new Insets(50, 50, 50, 50)));

        titulo1 = new JLabel("Bienvenido a GALERIANDES ", SwingConstants.CENTER);
        titulo2 = new JLabel("Login", SwingConstants.CENTER);
        titulo1.setFont( UIManager.getFont( "h1.font" ) );
        titulo2.setFont( UIManager.getFont( "h2.font" ) );

        titulo1.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo2.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        Dimension ancho = new Dimension(250, 30);

        usuario = new JTextField();
        contrasena = new JPasswordField();

        usuario.setMaximumSize(ancho);
        contrasena.setMaximumSize(ancho);
        botonConfirmar = new JButton("Confirmar");
        botonConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);

        String[] options = { "ADMIN", "CLIENTE", "EMPLEADO"};
        botonConfirmar.setActionCommand(NombresVistas.ADMIN.getNombre());

        // Create a combo box with the options
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String seleccion = (String) comboBox.getSelectedItem();
                botonConfirmar.setActionCommand(seleccion);
                System.out.println("Seleccion: " + seleccion);
            }
        
        });
        comboBox.setMaximumSize(ancho);
        

        botonConfirmar.addActionListener(padre);
        botonConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                vistaGraficaMain.setEmail(usuario.getText());
                vistaGraficaMain.setContrasena(new String(contrasena.getPassword()));
            }
        });

        add(titulo1);
        add(Box.createVerticalStrut(20));
        add(titulo2);
        add(Box.createVerticalStrut(20));
        add(usuario);
        add(Box.createVerticalStrut(20));
        add(contrasena);
        add(Box.createVerticalStrut(20));
        add(comboBox);
        add(Box.createVerticalStrut(20));
        add(botonConfirmar);

    }}
}
