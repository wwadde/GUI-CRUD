package org.GUI;




import javax.swing.border.EmptyBorder;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class Inicio extends JFrame {
    public static Inicio instanciaInicio; // Singleton para que solo haya una instancia de la ventana de inicio
    private JPanel panelMadre;
    public static JPanel panelContenido;
    private JPanel panelBotones;
    private JButton verBtn;
    private JLabel bienvenidaLabel;
    private final Usuario usuario;
    public static JPanel panelCentralCambiante;

    Color blancoColor = new Color(255, 255, 255, 202);
    Color grisColor = new Color(255, 255, 255, 89);
    Color azulColor = new Color(12, 106, 225, 186);

    Font fuenteTitulos = new Font("Arial", Font.BOLD, 14);


    public Inicio(Usuario user) {

        this.usuario = user;

        initComponentes();

        setContentPane(panelMadre);
        setTitle("Login");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        configurarDesign();
        configurarEventos();


    }


    private void initComponentes() {

        panelMadre = new JPanel(new BorderLayout());
        panelContenido = new JPanel(new BorderLayout());
        panelBotones = new JPanel(new BorderLayout());
        panelCentralCambiante = new JPanel();

        verBtn = new JButton("Ver");
        bienvenidaLabel = new JLabel("Bienvenido");
        JPanel panelSuperiorIzquierdo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelSuperiorDerecho = new JPanel(new BorderLayout());

        panelSuperiorDerecho.setBackground(grisColor);
        panelSuperiorIzquierdo.setBackground(grisColor);

        panelMadre.add(panelBotones, BorderLayout.NORTH);
        panelMadre.add(panelContenido, BorderLayout.CENTER);
        panelMadre.setBorder(new EmptyBorder(0, 60, 60, 60));

        verBtn.setPreferredSize(new Dimension(100, 40));

        panelSuperiorIzquierdo.add(verBtn);

        panelSuperiorDerecho.add(bienvenidaLabel, BorderLayout.CENTER);

        panelBotones.add(panelSuperiorIzquierdo, BorderLayout.WEST);
        panelBotones.add(panelSuperiorDerecho, BorderLayout.EAST);


    }

    private void configurarDesign() {

        panelMadre.setBackground(grisColor);
        panelContenido.setBackground(blancoColor);
        panelBotones.setBackground(grisColor);
        panelBotones.setBorder(new EmptyBorder(15, 6, 15, 6));


        // Quitamos el focus de los botones
        verBtn.setFocusable(false);
        verBtn.setBackground(azulColor);
        verBtn.setForeground(Color.WHITE);
        verBtn.setFont(fuenteTitulos);

        bienvenidaLabel.setFont(fuenteTitulos);
        bienvenidaLabel.setHorizontalAlignment(JLabel.CENTER);
        bienvenidaLabel.setVerticalAlignment(JLabel.CENTER);
        bienvenidaLabel.setText("Bienvenido, " + usuario.getNombre());


    }

    private void configurarEventos() {

        verBtn.addActionListener(e -> {
            SqlConnection sql = new SqlConnection();
            verBtn.setFocusable(false);
            panelContenido.remove(panelCentralCambiante);
            revalidate();
            repaint();
            GuiMostrarTabla panel = new GuiMostrarTabla();
            panelCentralCambiante = panel.getPanel();
            panel.tablaRegistros.setModel(sql.selectDatos());
            panelContenido.add(panelCentralCambiante, BorderLayout.CENTER);
            revalidate();
            repaint();


        });




    }



}
