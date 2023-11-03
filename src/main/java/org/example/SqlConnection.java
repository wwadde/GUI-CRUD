package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
//import creds.Mycreds existe la opcion de tener las credenciales en otra clase por seguridad, buenas practicas
public class SqlConnection {



    public static void SelectAzureSQL(String sql, String cnnString){
        //get a result set from azure and display it
        System.out.println("Selecting data...");
        ResultSet resultSet = null;
        try (Connection cnn = DriverManager.getConnection(cnnString); //que hace ese =, le asigna el cnnString a la variable cnn? y cual es la diferencia a cuando uso new?
            Statement statement = cnn.createStatement();) {

            resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                System.out.println(resultSet.getString(1) + ", " + resultSet.getString(2) + ", " + resultSet.getString(3));
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
