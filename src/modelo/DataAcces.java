package modelo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataAcces {

    private static Connection conexion;

    private static void abrirConexion(){
        Properties ficheroConexion = new Properties();
        try {
            ficheroConexion.load(new FileReader("src/properties.properties"));

        conexion = DriverManager.getConnection(ficheroConexion.getProperty("sourceURL"), ficheroConexion.getProperty("usuario"), ficheroConexion.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace(); //todo error interno al inicializar la conexion
        } catch (SQLException e) {
            e.printStackTrace(); //todo error al realizar la conexion
        }
    }
}
