package org.GUI;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
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

    public void selectDatos(String query) {
        String cnnString = creds.getProperty("db.url");

        try (Connection cnn = DriverManager.getConnection(cnnString);
             Statement statement = cnn.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String codigo = resultSet.getString("est_codigo");
                String nombre = resultSet.getString("est_nombre");
                String apellido = resultSet.getString("est_apellido");
                String correo = resultSet.getString("est_correo");

                System.out.println("Codigo: " + codigo);
                System.out.println("Nombre: " + nombre);
                System.out.println("Correo: " + correo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}