package org.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class GuiMostrarTabla extends JFrame {

    private JPanel panelPrincipal;
    private JPanel panelSuperior;
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

    public GuiMostrarTabla() {
        initComponents();
        setContentPane(panelPrincipal);
        setTitle("Mostrar Estudiantes");
        setBounds(500, 300, 800, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);


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

                if (dato.equals("Ingrese el dato del usuario4 a buscar") || dato.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Ingrese un dato valido.");
                    Inicio.instanciaInicio.revalidate();
                } else {

                    tablaRegistros.setModel(sql.buscarDato(dato));
                    informacionLabel.setText("Se encontraron " + tablaRegistros.getRowCount() + " registros.");
                    Inicio.instanciaInicio.revalidate();
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
                    Inicio.instanciaInicio.revalidate();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado un usuario para eliminar");
                Inicio.instanciaInicio.revalidate();
            }
        });
    }


    public JPanel getPanel() {
        return panelPrincipal;

    }

    private void initComponents(){

        panelPrincipal = new JPanel(new BorderLayout());
        panelSuperior = new JPanel(new BorderLayout());
        panelParametros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buscarPanel = new JPanel(new GridBagLayout());
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        tablaRegistros = new JTable();
        scrollPane = new JScrollPane(tablaRegistros);

        searchInput = new JTextField();
        buscarBtn = new JButton("Buscar");
        informacionLabel = new JLabel();
        MostrarTodoBtn = new JButton("Mostrar todos los registros");
        radioButton1 = new JRadioButton();
        radioButton2 = new JRadioButton();
        comboBox1 = new JComboBox();
        editarBtn = new JButton("Editar");
        eliminarBtn = new JButton("Eliminar");

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        panelSuperior.add(informacionLabel, BorderLayout.NORTH);
        panelSuperior.add(buscarPanel, BorderLayout.CENTER);
        panelSuperior.add(panelParametros, BorderLayout.SOUTH);

        panelBotones.add(editarBtn);
        panelBotones.add(MostrarTodoBtn);
        panelBotones.add(eliminarBtn);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.80;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        buscarPanel.add(searchInput, gbc);
        gbc.weightx = 0.20;
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 20, 0, 0);
        buscarPanel.add(buscarBtn, gbc);

        panelParametros.add(radioButton1);
        panelParametros.add(comboBox1);
        panelParametros.add(radioButton2);



        buscarPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        informacionLabel.setBorder(new EmptyBorder(10, 10, 10, 120));

        buscarBtn.setBackground(azulColor);
        buscarBtn.setForeground(Color.WHITE);
        buscarBtn.setFont(fuenteTitulos);
        buscarBtn.setFocusable(false);

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

        searchInput.setForeground(placeholderColor);
        searchInput.setHorizontalAlignment(SwingConstants.CENTER);
        searchInput.setColumns(2);

        tablaRegistros.setDefaultEditor(Object.class, null); // Para que no se pueda editar la tabla

    }

}






