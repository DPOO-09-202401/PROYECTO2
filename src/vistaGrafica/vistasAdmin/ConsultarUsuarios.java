package vistaGrafica.vistasAdmin;

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import controlador.LogicaUsuarios;
import modelo.ModeloUsuario;

public class ConsultarUsuarios extends JPanel{

    public ConsultarUsuarios(ActionListener padre) {
        this.setBorder(new javax.swing.border.EmptyBorder(new Insets(10, 50, 50, 50)));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        JLabel titulo = new JLabel("Consultar Usuarios",SwingConstants.CENTER);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont( UIManager.getFont( "h1.font" ) );
        add(titulo);

        JButton atras = new JButton("<<< ATRAS");
        atras.addActionListener(padre);
        atras.setActionCommand("ADMIN");
        add(atras);
        atras.setAlignmentX(Component.CENTER_ALIGNMENT);

        LogicaUsuarios logicaUsuarios = new LogicaUsuarios();
        HashMap<String, ModeloUsuario> usuarios = logicaUsuarios.consultarTodos();
        ArrayList<String> labels = new ArrayList<String>();
        for (String key : usuarios.keySet()) {
            ModeloUsuario usuario = usuarios.get(key);
            String usuarioLabel = usuario.getNombre() + ", " + usuario.getCorreo() + ", " + usuario.getRol();
            labels.add(usuarioLabel);
        }
        String[] arr = new String[labels.size()];
		arr = labels.toArray(arr);

        JList<String> list = new JList<>(arr);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane);


    }

}
