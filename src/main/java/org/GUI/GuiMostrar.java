package org.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GuiMostrar extends JFrame {
    private JPanel panelPrincipal;
    private JTable tablaEstudiantes;
    private JScrollPane scrollPane;
    private JTextField searchInput;
    private JButton buscarBtn;
    private JLabel informacionLabel;
    private JButton MostrarTodoBtn;

    private final Color placeholderColor = Color.gray; // Color del placeholder
    private final Color textColor = Color.BLACK; // Color del texto

    public GuiMostrar() {
        setContentPane(panelPrincipal);
        setTitle("Mostrar Estudiantes");
        setBounds(500, 300, 800, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


        informacionLabel.setBounds(0,0,2,1);
        buscarBtn.setText("Buscar");
        searchInput.setForeground(placeholderColor);
        searchInput.setHorizontalAlignment(SwingConstants.CENTER);
        searchInput.setColumns(2);
        searchInput.setText("Ingrese el dato del estudiante a buscar");

        MostrarTodoBtn.setText("Mostrar todos los registros de la base de datos");
        tablaEstudiantes.setDefaultEditor(Object.class, null);


        searchInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(searchInput.getText().equals("Ingrese el dato del estudiante a buscar")){
                    searchInput.setText("");
                    searchInput.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(searchInput.getText().isEmpty()){
                    searchInput.setForeground(placeholderColor);
                    searchInput.setText("Ingrese el dato del estudiante a buscar");
                }
            }
        });

        panelPrincipal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panelPrincipal.requestFocusInWindow();
            }

        });

        SqlConnection sql = new SqlConnection();
        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dato = searchInput.getText();

                if(dato.equals("Ingrese el dato del estudiante a buscar") || dato.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Ingrese un dato valido.");
                }
                else {

                    tablaEstudiantes.setModel(sql.buscarDato(dato));
                    informacionLabel.setText("Se encontraron " + tablaEstudiantes.getRowCount() + " estudiantes registrados.");
                }


            }
        });

        MostrarTodoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tablaEstudiantes.setModel(sql.selectDatos());
                informacionLabel.setText("Se encontraron " + tablaEstudiantes.getRowCount() + " estudiantes registrados.");
            }
        });
    }
}







