package org.GUI;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;


public class Inicio extends JFrame{

    private JPanel PanelPrincipal;
    private JButton VerRegistros;
    private JButton Registrar;
    private JLabel BienvenidaLabel;



    public Inicio(){

        setContentPane(PanelPrincipal);
        PanelPrincipal.setBackground(new Color(60,63,65));
        setTitle("Registro de Estudiantes");
        setBounds(500,300,500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        BienvenidaLabel.setForeground(Color.WHITE);





        Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                GuiCreate RegistroFrame = new GuiCreate(); //crea el frame de registro Y lo superpone al frame de inicio
                RegistroFrame.setVisible(true);
                RegistroFrame.requestFocusInWindow();

            }
        });


        VerRegistros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GuiMostrar MostrarFrame = new GuiMostrar();

                MostrarFrame.setVisible(true);
                MostrarFrame.requestFocusInWindow();


            }
        });


    }

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
            try{
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }


        Inicio frame = new Inicio();
        frame.setVisible(true);

    }
}