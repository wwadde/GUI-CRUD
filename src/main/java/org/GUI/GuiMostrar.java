package org.GUI;

import javax.swing.*;

public class GuiMostrar extends JFrame {
    private JPanel panelPrincipal;
    private JTable tablaEstudiantes;
    private JScrollPane scrollPane;
    private JTextField searchInput;
    private JButton buscarBtn;
    private JLabel informacionLabel;

    public GuiMostrar() {
        setContentPane(panelPrincipal);
        setTitle("Mostrar Estudiantes");
        setBounds(500, 300, 500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        SqlConnection sql = new SqlConnection();
        tablaEstudiantes.setModel(sql.selectDatos());
        informacionLabel.setText("Se encontraron " + tablaEstudiantes.getRowCount() + " estudiantes registrados.");

        buscarBtn.setText("Buscar");
        searchInput.setText("Ingrese el dato del estudiante a buscar");

        pack();


    }
}







