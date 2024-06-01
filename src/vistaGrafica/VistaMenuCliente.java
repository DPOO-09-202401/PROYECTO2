package vistaGrafica;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class VistaMenuCliente extends JPanel{

    private JLabel titulo;
    private JScrollPane scrollPane;

    public VistaMenuCliente(ActionListener padre) {
        this.setBorder(new javax.swing.border.EmptyBorder(new Insets(10, 50, 50, 50)));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        titulo = new JLabel("Bienvenido CLIENTE", SwingConstants.CENTER);
        titulo.setFont( UIManager.getFont( "h1.font" ) );
        this.add(titulo);
        this.add(Box.createVerticalStrut(20));

        JPanel innerJPanel = new JPanel();
        innerJPanel.setLayout(new GridLayout(0, 1, 0, 10));

        JButton boton20 = new JButton("<<< SALIR");
        boton20.addActionListener(padre);
        boton20.setActionCommand(NombresVistas.LOGIN.getNombre());
        innerJPanel.add(boton20);

        JButton boton1 = new JButton("Consultar Perfil");
        innerJPanel.add(boton1);

        JButton boton2 = new JButton("Consultar Piezas en Venta");
        innerJPanel.add(boton2);

        JButton boton3 = new JButton("Solicitar compra de Pieza");
        innerJPanel.add(boton3);

        JButton boton4 = new JButton("Consultar mis piezas");
        innerJPanel.add(boton4);

        JButton boton5 = new JButton("Consultar piezas bloqueadas por mi");
        innerJPanel.add(boton5);

        JButton boton6 = new JButton("CONSULTAR HISTORIAL DE UNA PIEZA");
        innerJPanel.add(boton6);

        JButton boton7 = new JButton("CONSULTAR PERFIL DE UN ARTISTA");
        innerJPanel.add(boton7);

        scrollPane = new JScrollPane(innerJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, scrollPane.getPreferredSize().height));
        this.add(scrollPane);
    }

}
