


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static java.awt.Font.*;
public class form extends JFrame{


        private JPanel PanelDatos = new JPanel(new BorderLayout());
        private JScrollPane PanelTabla = new JScrollPane();
        private JLabel InformacionFrame;
        private JPanel PanelBotones;
        private JTable Tabla;
        private JButton DatosBtn = new JButton("Datos");
        private JButton ReporteBtn = new JButton("Reporte");
        private JPanel PanelVacioInferior = new JPanel();


        public form() {




            setTitle("Tabla");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(600, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            //creo un objeto de dimension y se lo paso al metodo que asigna el tamaño al panel, luego agrego al frame
            PanelVacioInferior.setPreferredSize(new Dimension(0, 20));
            add(PanelVacioInferior, BorderLayout.SOUTH);

            PanelBotones = new JPanel();
            //asigno un nuevo diseño al panel mediante el constructor con parametro estatico LEFT de la clase FlowLayout
            PanelBotones.setLayout(new FlowLayout(FlowLayout.LEFT));
            //agrego los botones al panel
            PanelBotones.add(DatosBtn);
            PanelBotones.add(ReporteBtn);
            //agrego el panel al frame junto con su distribución
            add(PanelBotones, BorderLayout.NORTH);

            InformacionFrame = new JLabel("PARA VISUALIZAR O REGISTRAR LOS DATOS, PRESIONE EL RESPECTIVO BOTÓN");
            InformacionFrame.setHorizontalAlignment(SwingConstants.CENTER);
            InformacionFrame.setForeground(Color.GRAY);
            add(InformacionFrame, BorderLayout.CENTER);

            //llamo al metodo propio para cambiar las propiedades de los componentes
            formatearBoton(DatosBtn);
            formatearBoton(ReporteBtn);


            //agrego las columnas al modelo
            DefaultTableModel modelo = new DefaultTableModel();

            modelo.addColumn("Cédula");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Género");
            modelo.addColumn("Fecha nacimiento");
            modelo.addColumn("Fecha Ingreso");
            modelo.addColumn("Salario");


            DatosBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    //desactiva y activa los botones
                    ReporteBtn.setEnabled(true);
                    DatosBtn.setEnabled(false);

                    //quito el otro panel situado en el centro del frame
                    remove(InformacionFrame);
                    remove(PanelTabla);

                    setSize(600, 500);

                    //centro los paneles que contienen un boton mediante el FlowLayout
                    JPanel PanelBotonSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JPanel PanelBotonInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));

                    //agrego paneles vacios como forma de padding horizontal
                    JPanel EspacioVacioDerecha = new JPanel();
                    EspacioVacioDerecha.setPreferredSize(new Dimension(45, 0));
                    JPanel EspacioVacioIzquierda = new JPanel();
                    EspacioVacioIzquierda.setPreferredSize(new Dimension(45, 0));

                    //8 filas, 2 columnas, y 10 de separación vertical
                    JPanel PanelEtiquetas = new JPanel(new GridLayout(8, 2, 0, 10));

                    //Creacion del boton y su formato que servira como letrero
                    JTextField InformacionBtn = new JTextField("LOGO DE LA EMPRESA");
                    InformacionBtn.setPreferredSize(new Dimension(500, 100));
                    formatearBoton(InformacionBtn);
                    InformacionBtn.setEnabled(false);
                    InformacionBtn.setDisabledTextColor(Color.WHITE);
                    InformacionBtn.setFont(new Font("Arial", BOLD, 20)); //crea una nueva fuente, le asigna negrita y tamaño 20
                    InformacionBtn.setHorizontalAlignment(SwingConstants.CENTER);


                    //Creacion de los componentes del formulario
                    JLabel Cedula = new JLabel("Cédula");
                    JTextField CedulaTxt = new JTextField();
                    bordeColorAzul(CedulaTxt);

                    JLabel Nombre = new JLabel("Nombre");
                    JTextField NombreTxt = new JTextField();
                    bordeColorAzul(NombreTxt);

                    JLabel Apellido = new JLabel("Apellido");
                    JTextField ApellidoTxt = new JTextField();
                    bordeColorAzul(ApellidoTxt);


                    String[] opciones = {"", "Masculino", "Femenino"};
                    JComboBox<String> ListaDesplegable = new JComboBox<>(opciones);
                    JLabel Genero = new JLabel("Seleccione el Género");
                    bordeColorAzul(ListaDesplegable);


                    JLabel FechaNacimiento = new JLabel("Fecha Nacimiento");
                    JTextField FechaNacimientoTxt = new JTextField();
                    bordeColorAzul(FechaNacimientoTxt);


                    JLabel FechaIngreso = new JLabel("Fecha Ingreso");
                    JTextField FechaIngresoTxt = new JTextField();
                    bordeColorAzul(FechaIngresoTxt);


                    JLabel Salario = new JLabel("Salario");
                    JTextField SalarioTxt = new JTextField();
                    bordeColorAzul(SalarioTxt);


                    JButton GuardarBtn = new JButton("Guardar Datos");
                    formatearBoton(GuardarBtn);

                    //Agregar los componentes al panel con el GridLayout
                    PanelEtiquetas.add(Cedula);
                    PanelEtiquetas.add(CedulaTxt);
                    PanelEtiquetas.add(Nombre);
                    PanelEtiquetas.add(NombreTxt);
                    PanelEtiquetas.add(Apellido);
                    PanelEtiquetas.add(ApellidoTxt);
                    PanelEtiquetas.add(Genero);
                    PanelEtiquetas.add(ListaDesplegable);
                    PanelEtiquetas.add(FechaNacimiento);
                    PanelEtiquetas.add(FechaNacimientoTxt);
                    PanelEtiquetas.add(FechaIngreso);
                    PanelEtiquetas.add(FechaIngresoTxt);
                    PanelEtiquetas.add(Salario);
                    PanelEtiquetas.add(SalarioTxt);

                    //Agregar los componentes a los paneles con el FlowLayout
                    PanelBotonSuperior.add(InformacionBtn);
                    PanelBotonInferior.add(GuardarBtn);

                    //Agregar los sub-paneles al panel madre
                    PanelDatos.add(PanelBotonSuperior, BorderLayout.NORTH);
                    PanelDatos.add(PanelEtiquetas, BorderLayout.CENTER);
                    PanelDatos.add(PanelBotonInferior, BorderLayout.SOUTH);
                    PanelDatos.add(EspacioVacioDerecha, BorderLayout.EAST);
                    PanelDatos.add(EspacioVacioIzquierda, BorderLayout.WEST);

                    //Agregar el panel madre al frame
                    add(PanelDatos, BorderLayout.CENTER);
                    revalidate(); //se usa para actualizar la GUI


                    GuardarBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


                            if (CedulaTxt.getText().isEmpty() || NombreTxt.getText().isEmpty() || ApellidoTxt.getText().isEmpty() || ListaDesplegable.getSelectedIndex() == 0 || FechaNacimientoTxt.getText().isEmpty() || FechaIngresoTxt.getText().isEmpty() || SalarioTxt.getText().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Por favor llene todos los campos");

                            } else {

                                // Crear un array de objetos y le asigno valores
                                Object[] datos = new Object[7];


                                datos[0] = CedulaTxt.getText();
                                datos[1] = NombreTxt.getText();
                                datos[2] = ApellidoTxt.getText();
                                datos[3] = ListaDesplegable.getSelectedItem();
                                datos[4] = FechaNacimientoTxt.getText();
                                datos[5] = FechaIngresoTxt.getText();
                                datos[6] = SalarioTxt.getText();

                                // Agregar el array de objetos como una nueva fila en el modelo
                                modelo.addRow(datos);

                                //limpio los campos de texto
                                CedulaTxt.setText("");
                                NombreTxt.setText("");
                                ApellidoTxt.setText("");
                                ListaDesplegable.setSelectedIndex(0);
                                FechaNacimientoTxt.setText("");
                                FechaIngresoTxt.setText("");
                                SalarioTxt.setText("");

                                JOptionPane.showMessageDialog(null, "Datos guardados correctamente");
                            }


                        }
                    });

                }

            });


            ReporteBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    ReporteBtn.setEnabled(false);
                    DatosBtn.setEnabled(true);
                    remove(InformacionFrame);
                    remove(PanelDatos);
                    setSize(800, 400);


                    Tabla = new JTable(modelo);

                    PanelTabla = new JScrollPane(Tabla);
                    add(PanelTabla, BorderLayout.CENTER);
                    revalidate();


                }
            });


        }


        //Creación de un objeto de la clase Color con parametros personalizados
        private Color colorAzul = new Color(9, 162, 213);

        //Métodos propios para modificar los componentes
        public void formatearBoton(JComponent componente) {
            componente.setBackground(colorAzul);
            componente.setForeground(Color.WHITE);
        }

        public void bordeColorAzul(JComponent componente) {
            componente.setBorder(BorderFactory.createLineBorder(colorAzul, 2));
        }


        public static void main(String[] args) {

            form frame = new form();
            frame.setVisible(true);
            frame.setResizable(false);

        }
    }

