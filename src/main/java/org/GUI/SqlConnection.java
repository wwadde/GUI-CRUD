package org.GUI;


import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Properties;
import java.util.Vector;

public class SqlConnection {

    private Properties creds = new Properties();
    private String connectionString;



    public SqlConnection() {
        try {
            // Crea un objeto FileInputStream para leer el archivo config.properties
            FileInputStream input = new FileInputStream("config.properties");
            // Carga las credenciales mediante la lectura que hace clase FileInputStream del archivo config.properties
            creds.load(input);
            // Inicializa la variable con la propiedad db.url que contiene la cadena de conexión
            connectionString = creds.getProperty("db.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertarDatos(Usuario usuario) {


        String sqlInsert = "INSERT INTO dbo.credenciales (correo, password ) VALUES (?,?);";
        String sqlInsert2 = "INSERT INTO dbo.usuarios (usuarios_id, nombre, apellido, codigo) VALUES (?,?,?,?);";

        // Crear la conexión y el preparedStatement con un segundo parametro para obtener las llaves generadas
        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement2 = cnn.prepareStatement(sqlInsert2)) {

            preparedStatement.setString(1, usuario.getCorreo());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.executeUpdate();

            ResultSet llaveGenerada = preparedStatement.getGeneratedKeys();
            // Obtener la llave primaria generada, el metodo next mueve el cursor inicialmente al primer registro
            if(llaveGenerada.next()){
                int id = llaveGenerada.getInt(1);

                preparedStatement2.setInt(1, id);
                preparedStatement2.setString(2, usuario.getNombre());
                preparedStatement2.setString(3, usuario.getApellido());
                preparedStatement2.setString(4, usuario.getCodigo());
                preparedStatement2.executeUpdate();

            }
            else {
                throw new SQLException("No se generó la llave");
            }



            JOptionPane.showMessageDialog(null,"Registrado con exito");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean usuarioExistente(String usuario) {

        String query = "SELECT * FROM dbo.credenciales WHERE correo = ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {

            preparedStatement.setString(1, usuario);

            try(ResultSet resultSet = preparedStatement.executeQuery()){

                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public TableModel selectDatos() {

        String query = "SELECT est_nombre, est_apellido, est_codigo, est_correo FROM dbo.estudiantes;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             Statement statement = cnn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {


            // Crear un modelo de tabla para almacenar los datos
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Codigo");
            modelo.addColumn("Correo");


            while (resultSet.next()) {
                Vector fila = new Vector(); //probar ejemplos de vectores
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

    public TableModel buscarDato(String dato) {

        String query = "SELECT * FROM dbo.estudiantes WHERE est_nombre LIKE ? OR est_apellido LIKE ? OR est_codigo LIKE ? OR est_correo LIKE ?;"; //define la consulta utilizando el operador LIKE para buscar coincidencias

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {


            ResultSetMetaData metaData = preparedStatement.getMetaData(); //obtiene los metadatos de la consulta mediante el preparedStatement pero también se podría hacer con el ResultSet

            for (int i = 1; i <= metaData.getColumnCount(); i++) {

                preparedStatement.setString(i, "%" + dato + "%"); //el % es para que busque cualquier coincidencia
            }

            try(ResultSet resultSet = preparedStatement.executeQuery()){

                DefaultTableModel modelo = new DefaultTableModel();


                modelo.addColumn("Nombre");
                modelo.addColumn("Apellido");
                modelo.addColumn("Codigo");
                modelo.addColumn("Correo");

                // Agregar las filas al modelo
                while (resultSet.next()) {
                    Object[] row = new Object[metaData.getColumnCount()];
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        row[i - 1] = resultSet.getObject(i); //indices de columnas resultset empiezan en 1 y los de array en 0
                    }
                    modelo.addRow(row);
                }
                return modelo;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean login(String usuario, String password) {

        String query = "SELECT * FROM dbo.credenciales WHERE correo = ? AND password = ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {

            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);

            try(ResultSet resultSet = preparedStatement.executeQuery()){

                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Bienvenido");
                    return true;
                } else {
                    return false;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
