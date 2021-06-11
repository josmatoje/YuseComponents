package modelo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataAcces {

    private static Connection conexion = null;

    private static String EXC_INSERTA_USUARIO = "EXECUTE InsertarUsuario ?,?,?,?";
    /**
     * <b>Cabecera</b>: </br>
     * <b>Descripcion</b>: </br>
     * <b>Entradas</b>: </br>
     * <b>Salida</b>: </br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: </br>
     * @param
     * @return
     */
    /**
     * <b>Cabecera</b>: public static void abrirConexion()</br>
     * <b>Descripcion</b>: </br>
     * <b>Entradas</b>: </br>
     * <b>Salida</b>: </br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: </br>
     *
     * @return
     */
    public static void abrirConexion(){
        Properties ficheroConexion = new Properties();
        try {
            ficheroConexion.load(new FileReader("src/properties.properties"));

        conexion = DriverManager.getConnection( ficheroConexion.getProperty("sourceURL"),
                                                ficheroConexion.getProperty("usuario"),
                                                ficheroConexion.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace(); //todo error interno al inicializar la conexion
        } catch (SQLException f) {
            f.printStackTrace();
        }
    }

    //Busca si existe un usuario en la bbdd
    public static boolean existeUsuario( String nick){

        return
    }

    /**inserta un usuario (llama al metodo datosNuevoUsuario)
     *
     * @return
     */
    public static boolean ingresarUsuario(){
        boolean insertado = false;

        try {
            CallableStatement ingresarUsuario = conexion.prepareCall(EXC_INSERTA_USUARIO);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return insertado;
    }




    public static void cerrarConexion (Connection conexionSQL){
        try {
            if (conexionSQL!=null)
                conexionSQL.close();
        } catch (SQLException e) {
            e.printStackTrace(); //todo error al cerrar la conexion
        }
    }



}
