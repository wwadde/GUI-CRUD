package org.GUI;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;
//import creds.Mycreds existe la opcion de tener las credenciales en otra clase por seguridad, buenas practicas
public class SqlConnection {

    private Properties creds = new Properties();
    private String sql = "SELECT * FROM estudiantes;";

    public SqlConnection(){
        try {
            FileInputStream input = new FileInputStream("config.properties");
            creds.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SelectAzureSQL() {
        String cnnString = creds.getProperty("db.url");

        ResultSet resultSet = null; //que hace esta mondá


        try (Connection cnn = DriverManager.getConnection(cnnString);
             Statement statement = cnn.createStatement()) { //y esta otra mondá, try-with-resources

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
