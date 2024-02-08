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

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.Log.GuardarErrores;


public class SqlConnection {

    private final Properties creds = new Properties();
    private String connectionString;
    private GuardarErrores logger;


    public SqlConnection() {

        logger = new GuardarErrores(SqlConnection.class);

        try {
            // Crea un objeto FileInputStream para leer el archivo config.properties
            FileInputStream input = new FileInputStream("config.properties");
            // Carga las credenciales mediante la lectura que hace clase FileInputStream del archivo config.properties
            creds.load(input);
            // Inicializa la variable con la propiedad db.url que contiene la cadena de conexión
            connectionString = creds.getProperty("db.url");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al leer el archivo de propiedades", e);
        }
    }

    // Metodo para insertar datos en la base de datos que se divide en 4 partes
    public boolean insertarDatos(Usuario usuario) {
        try (Connection cnn = DriverManager.getConnection(connectionString)) {
            if (usuarioExiste(cnn, usuario.getCorreo())) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe");
                return true;
            }
            // Se inserta primero credenciales y luego usuario con la llave generada
            int id = insertarCredenciales(cnn, usuario);
            insertarUsuario(cnn, usuario, id);

            JOptionPane.showMessageDialog(null, "Registrado con exito");
            return false;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar datos", e);
            return true;
        }
    }

    private boolean usuarioExiste(Connection cnn, String correo) throws SQLException {
        String query = "SELECT * FROM dbo.credenciales WHERE correo = ?;";
        try (PreparedStatement preparedQuery = cnn.prepareStatement(query)) {
            preparedQuery.setString(1, correo);
            try (ResultSet resultSet = preparedQuery.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    // Metodo que inserta las credenciales del usuario en la tabla credenciales y devuelve el id generado
    private int insertarCredenciales(Connection cnn, Usuario usuario) throws SQLException {
        String sqlInsert = "INSERT INTO dbo.credenciales (correo, password ) VALUES (?,?);";
        try (PreparedStatement preparedStatement = cnn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, usuario.getCorreo().toUpperCase());
            preparedStatement.setString(2, usuario.getPassword());
            preparedStatement.executeUpdate();

            try (ResultSet llaveGenerada = preparedStatement.getGeneratedKeys()) {
                if (llaveGenerada.next()) {
                    return llaveGenerada.getInt(1);
                } else {
                    throw new SQLException("No se generó la llave");
                }
            }
        }
    }

    private void insertarUsuario(Connection cnn, Usuario usuario, int id) throws SQLException {
        String sqlInsert2 = "INSERT INTO dbo.usuarios (usuarios_id, nombre, apellido, codigo, roles_id) VALUES (?,?,?,?,?);";
        try (PreparedStatement preparedStatement2 = cnn.prepareStatement(sqlInsert2)) {
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2, usuario.getNombre().toUpperCase());
            preparedStatement2.setString(3, usuario.getApellido().toUpperCase());
            preparedStatement2.setString(4, usuario.getCodigo().toUpperCase());
            preparedStatement2.setInt(5, usuario.getRol());
            preparedStatement2.executeUpdate();
        }
    }


    // Selecciona todos los datos y los almacena en un modelo de tabla
    public TableModel selectDatos() {

        String query = "SELECT * FROM dbo.usuarios AS usu JOIN dbo.roles AS rol ON usu.roles_id = rol.roles_id JOIN dbo.credenciales AS cred ON usu.usuarios_id = cred.credenciales_id;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             Statement statement = cnn.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {


            // Crea las columnas de la tabla
            DefaultTableModel modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Codigo");
            modelo.addColumn("Correo");
            modelo.addColumn("Rol");

            // Se recorre el resultset y se almacenan las filas en el modelo
            while (resultSet.next()) {
                ArrayList<Object> fila = new ArrayList<>();
                fila.add(resultSet.getString("nombre"));
                fila.add(resultSet.getString("apellido"));
                fila.add(resultSet.getString("codigo"));
                fila.add(resultSet.getString("correo"));
                fila.add(resultSet.getString("rol"));
                // Convierto el arraylist a un array de objetos porque el metodo addRow solo recibe un array de objetos
                modelo.addRow(fila.toArray());
            }

            return modelo;


        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al seleccionar datos", e);
            return null;
        }
    }

    public TableModel buscarDato(String dato) {

        // Define la consulta utilizando el operador LIKE para buscar coincidencias
        String query = "SELECT usu.nombre, usu.apellido, usu.codigo, cred.correo, rol.rol FROM dbo.usuarios AS usu JOIN dbo.credenciales AS cred ON usu.usuarios_id = cred.credenciales_id JOIN dbo.roles AS rol on usu.roles_id = rol.roles_id WHERE nombre LIKE ? OR apellido LIKE ? OR codigo LIKE ? OR correo LIKE ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {


            for (int i = 1; i <= 4; i++) {

                preparedStatement.setString(i, "%" + dato + "%"); //el % es para que busque cualquier coincidencia
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                DefaultTableModel modelo = new DefaultTableModel();
                // getMetaData retorna un objeto ResultSetMetaData que contiene informacion sobre la consulta
                ResultSetMetaData metaData = resultSet.getMetaData();

                modelo.addColumn("Nombre");
                modelo.addColumn("Apellido");
                modelo.addColumn("Codigo");
                modelo.addColumn("Correo");
                modelo.addColumn("Rol");

                // Recorre las filas del resultset
                while (resultSet.next()) {
                    // Se crea un array de objetos para almacenar los datos de cada fila
                    Object[] row = new Object[metaData.getColumnCount()];
                    // Se recorre cada columna de la fila y se almacena en el array
                    for (int i = 1; i <= metaData.getColumnCount(); i++) {
                        // Indices de columnas resultset empiezan en 1 y los de array en 0
                        row[i - 1] = resultSet.getObject(i);
                    }
                    modelo.addRow(row);
                }
                return modelo;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar datos", e);
            return null;
        }
    }

    public Usuario login(String usuario, String password) {

        String query = "SELECT * FROM dbo.usuarios AS usu JOIN dbo.credenciales AS cred ON usu.usuarios_id = cred.credenciales_id JOIN dbo.roles AS rol ON usu.roles_id = rol.roles_id WHERE cred.correo = ? AND cred.password = ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {

            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    // Se crea un objeto usuario para conservar los datos del usuario logueado
                    Usuario user = new Usuario();
                    user.setId(resultSet.getInt("usuarios_id"));
                    user.setNombre(resultSet.getString("nombre"));
                    user.setApellido(resultSet.getString("apellido"));
                    user.setCorreo(resultSet.getString("codigo"));
                    user.setCodigo(resultSet.getString("correo"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRol(resultSet.getInt("roles_id"));
                    return user;

                } else {
                    // Si no hay resultados se retorna null
                    return null;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al loguear", e);
            return null;
        }
    }

    public Usuario obtenerUsuario(String correo) {

        String query = "SELECT * FROM dbo.usuarios AS usu JOIN dbo.credenciales AS cred ON usu.usuarios_id = cred.credenciales_id JOIN dbo.roles AS rol ON usu.roles_id = rol.roles_id WHERE cred.correo = ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(query)) {

            preparedStatement.setString(1, correo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    // Se crea un objeto del usuario seleccionado en la tabla para posteriormente editar o eliminar
                    Usuario user = new Usuario();
                    user.setId(resultSet.getInt("usuarios_id"));
                    user.setNombre(resultSet.getString("nombre"));
                    user.setApellido(resultSet.getString("apellido"));
                    user.setCorreo(resultSet.getString("correo"));
                    user.setCodigo(resultSet.getString("codigo"));
                    user.setPassword(resultSet.getString("password"));
                    user.setRol(resultSet.getInt("roles_id"));
                    return user;

                } else {
                    return null;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener usuario", e);
            return null;
        }
    }

    public void editarUsuario(Usuario usuario) {
        String updateUsuarios = "UPDATE dbo.usuarios SET nombre = ?, apellido = ?, codigo = ?, roles_id = ? WHERE usuarios_id = ?;";
        String updateCredenciales = "UPDATE dbo.credenciales SET correo = ?, password = ? WHERE credenciales_id = ?;";
        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(updateUsuarios);
             PreparedStatement preparedStatement2 = cnn.prepareStatement(updateCredenciales)) {


            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApellido());
            preparedStatement.setString(3, usuario.getCodigo());
            preparedStatement.setInt(4, usuario.getRol());
            preparedStatement.setInt(5, usuario.getId());
            preparedStatement.executeUpdate();

            preparedStatement2.setString(1, usuario.getCorreo());
            preparedStatement2.setString(2, usuario.getPassword());
            preparedStatement2.setInt(3, usuario.getId());

            JOptionPane.showMessageDialog(null, "Usuario editado con exito");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al editar usuario", e);
        }
    }

    public void eliminarUsuario(String usuario) {

        Usuario user = obtenerUsuario(usuario);

        // Se elimina primero a usuario y luego a credenciales porque la tabla usuarios tiene una llave foranea de credenciales
        String eliminarUsuarios = "DELETE FROM dbo.usuarios WHERE usuarios_id = ?;";
        String eliminarCredenciales = "DELETE FROM dbo.credenciales WHERE credenciales_id = ?;";

        try (Connection cnn = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = cnn.prepareStatement(eliminarUsuarios);
             PreparedStatement preparedStatement2 = cnn.prepareStatement(eliminarCredenciales)) {

            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement2.setInt(1, user.getId());
            preparedStatement2.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario eliminado con exito");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar usuario", e);
        }
    }
}
