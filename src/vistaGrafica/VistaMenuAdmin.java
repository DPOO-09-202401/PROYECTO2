package vistaGrafica;

import java.awt.Component;
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

public class VistaMenuAdmin extends JPanel{

    private JLabel titulo;
    private JScrollPane scrollPane;

    public VistaMenuAdmin(ActionListener padre) {
        this.setBorder(new javax.swing.border.EmptyBorder(new Insets(10, 50, 50, 50)));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        titulo = new JLabel("Bienvenido ADMIN", SwingConstants.CENTER);
        titulo.setFont( UIManager.getFont( "h1.font" ) );
        this.add(titulo);
        this.add(Box.createVerticalStrut(10));
        
        JButton boton21 = new JButton("<<< SALIR");
        boton21.addActionListener(padre);
        boton21.setActionCommand(NombresVistas.LOGIN.getNombre());
        boton21.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(boton21);

        this.add(Box.createVerticalStrut(10));

        JPanel innerJPanel = new JPanel();
        innerJPanel.setLayout(new GridLayout(0, 1,0,10));
        
        JButton boton1 = new JButton("Consultar Usuarios");
        innerJPanel.add(boton1);
        boton1.setActionCommand("ADMIN1");
        boton1.addActionListener(padre);
                
        
        JButton boton2 = new JButton("Consultar Clientes");
        innerJPanel.add(boton2);
        
        JButton boton3 = new JButton("Crear usuario");
        innerJPanel.add(boton3);
        
        
        JButton boton4 = new JButton("Eliminar Usuario");
        innerJPanel.add(boton4);

        JButton boton5 = new JButton("Autorizar Comprador");
        innerJPanel.add(boton5);

        JButton boton6 = new JButton("Cambiar límite de compra de usuario");
        innerJPanel.add(boton6);
        
        JButton boton7 = new JButton("Consultar piezas");
        innerJPanel.add(boton7);
        
        JButton boton8 = new JButton("Registrar pieza");
        innerJPanel.add(boton8);
        
        JButton boton9 = new JButton("Devolver Pieza");
        innerJPanel.add(boton9);
        
        JButton boton10 = new JButton("Mover Pieza");
        innerJPanel.add(boton10);
        
        JButton boton11 = new JButton("Consultar Subastas");
        innerJPanel.add(boton11);
        
        JButton boton12 = new JButton("Iniciar Subasta");
        innerJPanel.add(boton12);
        
        JButton boton13 = new JButton("Abortar Subasta");
        innerJPanel.add(boton13);
        
        JButton boton14 = new JButton("Consultar Ofertas de Compra");
        innerJPanel.add(boton14);
        
        JButton boton15 = new JButton("Aprobar Compra");
        innerJPanel.add(boton15);
        
        JButton boton16 = new JButton("Confirmar Venta");
        innerJPanel.add(boton16);
        
        JButton boton17 = new JButton("Denegar Compra");
        innerJPanel.add(boton17);
        
        JButton boton18 = new JButton("HISTORIAL DE UNA PIEZA");
        innerJPanel.add(boton18);
        
        JButton boton19 = new JButton("PERFIL DE UN ARTISTA");
        innerJPanel.add(boton19);
        
        JButton boton20 = new JButton("PERFIL DE UN CLIENTE");
        innerJPanel.add(boton20);
        
        

        scrollPane = new JScrollPane(innerJPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(new EmptyBorder(0, 20, 0, 20));
        scrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, scrollPane.getPreferredSize().height));
        this.add(scrollPane);
    }
}
