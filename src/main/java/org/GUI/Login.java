package org.GUI;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame{
    private JPanel panelMadre;
    private JPanel prueba;
    private JPanel prueba2;

    public Login(){
        setContentPane(panelMadre);
        setTitle("Login");
        setBounds(500,300,500,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.4; //ocupa el 40% del ancho
        c.weighty = 1; //ocupa el 100% del alto
        c.fill = GridBagConstraints.BOTH;

        c.gridx = 0; //columna 0
        c.gridy = 0; //fila 0
        panelMadre.add(prueba, c);

        c.gridx = 1;
        c.weightx = 0.6;
        panelMadre.add(prueba2, c);

    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);

    }
}
