package org.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GuiMostrarTabla extends JFrame {

    private JPanel panelPrincipal;
    public JTable tablaRegistros;
    private JScrollPane scrollPane;
    private JTextField searchInput;
    private JButton buscarBtn;
    private JLabel informacionLabel;
    private JButton MostrarTodoBtn;
    private JPanel buscarPanel;
    private JPanel panelParametros;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JComboBox comboBox1;
    private JPanel panelBotones;
    private JButton editarBtn;
    private JButton eliminarBtn;
    // Variable estatica que pertenece a la clase y no a la instancia
    public static String usuarioSeleccionado;

    Color azulColor = new Color(12, 106, 225, 186);
    Font fuenteTitulos = new Font("Arial", Font.BOLD, 14);

    private final Color placeholderColor = Color.gray; // Color del placeholder
    private final Color textColor = Color.BLACK; // Color del texto

    public GuiMostrarTabla(Inicio instancia){

    }
    public GuiMostrarTabla() {
        setContentPane(panelPrincipal);
        setTitle("Mostrar Estudiantes");
        setBounds(500, 300, 800, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        buscarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        informacionLabel.setBorder(new EmptyBorder(10, 10, 10, 120));

        buscarBtn.setBackground(azulColor);
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(fuenteTitulos);
        editarBtn.setBackground(azulColor);
        editarBtn.setForeground(Color.WHITE);
        editarBtn.setFont(fuenteTitulos);
        eliminarBtn.setBackground(azulColor);
        eliminarBtn.setForeground(Color.WHITE);
        eliminarBtn.setFont(fuenteTitulos);
        MostrarTodoBtn.setBackground(azulColor);
        MostrarTodoBtn.setForeground(Color.WHITE);
        MostrarTodoBtn.setFont(fuenteTitulos);

        comboBox1.addItem("Todos");
        comboBox1.addItem("Usuario");
        comboBox1.addItem("Administrador");

        informacionLabel.setBounds(0, 0, 2, 1);
        buscarBtn.setText("Buscar");
        searchInput.setForeground(placeholderColor);
        searchInput.setHorizontalAlignment(SwingConstants.CENTER);
        searchInput.setColumns(2);
        searchInput.setText("Ingrese el dato del estudiante a buscar");

        MostrarTodoBtn.setText("Mostrar todos los registros");
        tablaRegistros.setDefaultEditor(Object.class, null); // Para que no se pueda editar la tabla

        SqlConnection sql = new SqlConnection();


        searchInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchInput.getText().equals("Ingrese el dato del estudiante a buscar")) {
                    searchInput.setText("");
                    searchInput.setForeground(textColor);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchInput.getText().isEmpty()) {
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


        buscarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dato = searchInput.getText();

                if (dato.equals("Ingrese el dato del estudiante a buscar") || dato.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un dato valido.");
                } else {

                    tablaRegistros.setModel(sql.buscarDato(dato));
                    informacionLabel.setText("Se encontraron " + tablaRegistros.getRowCount() + " registros.");
                }


            }
        });

        MostrarTodoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                revalidate();
                repaint();
                tablaRegistros.setModel(sql.selectDatos());
                informacionLabel.setText("Se encontraron " + tablaRegistros.getRowCount() + " registros.");
            }
        });

        tablaRegistros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int fila = tablaRegistros.getSelectedRow();
                // Obtengo el valor de la columna 3 que es el correo y lo guardo en la variable estatica
                usuarioSeleccionado = (String) tablaRegistros.getValueAt(fila, 3);


            }
        });

        editarBtn.addActionListener(e -> {

            if (usuarioSeleccionado != null) {
                Inicio.panelContenido.remove(Inicio.panelCentralCambiante);
                Inicio.instanciaInicio.revalidate();
                Inicio.instanciaInicio.repaint();

                GuiRegistrar panel = new GuiRegistrar();

                Inicio.panelCentralCambiante = panel.getPanel(usuarioSeleccionado);
                Inicio.panelContenido.add(Inicio.panelCentralCambiante, BorderLayout.CENTER);
                Inicio.instanciaInicio.revalidate();
                Inicio.instanciaInicio.repaint();


            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado un usuario para editar");
            }

        });

        eliminarBtn.addActionListener(e -> {
            if (usuarioSeleccionado != null) {
                Object[] opciones = {"Si", "No"}; // Opciones para el showConfirmDialog
                int opcion = JOptionPane.showOptionDialog(null, "¿Está seguro que desea eliminar el usuario?", "Eliminar Usuario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);
                if (opcion == 0) {
                    sql.eliminarUsuario(usuarioSeleccionado);
                    tablaRegistros.setModel(sql.selectDatos());
                    informacionLabel.setText("Se encontraron " + tablaRegistros.getRowCount() + " registros.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado un usuario para eliminar");
            }
        });
    }


    public JPanel getPanel() {
        return panelPrincipal;

    }
}






