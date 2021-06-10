package modelo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataAcces {


    public static Connection abrirConexion(){
        Properties ficheroConexion = new Properties();
        Connection conexion = null;
        try {
            ficheroConexion.load(new FileReader("src/properties.properties"));

        conexion = DriverManager.getConnection( ficheroConexion.getProperty("sourceURL"),
                                                ficheroConexion.getProperty("usuario"),
                                                ficheroConexion.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace(); //todo error interno al inicializar la conexion
        } catch (SQLException f) {
            f.getSQLState(); //todo error al realizar la conexion
        }
        return conexion;
    }

    private static void cerrarConexion (Connection conexionSQL){
        try {
            conexionSQL.close();
        } catch (SQLException throwables) {

        }
    }
}
