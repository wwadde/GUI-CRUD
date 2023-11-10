package org.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Vector;

//import creds.Mycreds existe la opcion de tener las credenciales en otra clase por seguridad, buenas practicas
public class SqlConnection {

    private Properties creds = new Properties();


    public SqlConnection(){
        try {
            FileInputStream input = new FileInputStream("config.properties"); //que hace exactamente el new
            creds.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertarDatos(String codigo, String nombre, String apellido, String correo) {
        String cnnString = creds.getProperty("db.url");
        String sqlInsert = "INSERT INTO dbo.estudiantes (est_codigo,est_nombre, est_apellido, est_correo) VALUES (?,?,?,?);";

        try (Connection cnn = DriverManager.getConnection(cnnString);
             PreparedStatement preparedStatement = cnn.prepareStatement(sqlInsert)) { //al instanciar la clase ese = es de asignacion?

            preparedStatement.setString(1, codigo);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, apellido);
            preparedStatement.setString(4, correo);

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Datos insertados correctamente.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TableModel selectDatos() {
        String cnnString = creds.getProperty("db.url");
        String query = "SELECT est_nombre, est_apellido, est_codigo, est_correo FROM dbo.estudiantes;";

        try (Connection cnn = DriverManager.getConnection(cnnString);
             Statement statement = cnn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {



            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Codigo");
            modelo.addColumn("Correo");


            while (resultSet.next()) {
                Vector fila = new Vector();
                fila.add(resultSet.getString("est_nombre"));
                fila.add(resultSet.getString("est_apellido"));
                fila.add(resultSet.getString("est_codigo"));
                fila.add(resultSet.getString("est_correo"));
                modelo.addRow(fila);
            }

            return modelo;



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}