package modelo;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DataAcces {

    private static Connection conexion = null; //Conexion de la bbdd
    //Cadenas de sentencias
    private static String EXISTE_USUARIO = "SELECT * FROM Usuarios WHERE Nick=?";
    private static String EXC_INSERTA_USUARIO = "EXECUTE InsertarUsuario ?,?,?,?";
    private static String COMPRUEBA_USUARIO = "SELECT dbo.ComprobarUsuario(?,?)";
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


    /**
     * <b>Cabecera</b>: public static boolean existeUsuario(String nick)</br>
     * <b>Descripcion</b>: comprueba si existe un usuario en la bbdd</br>
     * <b>Entradas</b>: el usuario a comprobar</br>
     * <b>Salida</b>: devuelve true si existe y false en cualquier otro caso</br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: </br>
     * @param nick, cadena
     * @return
     */
    public static boolean existeUsuario(String nick){
        boolean existe = false;

        try {
            PreparedStatement exisateUsuario = conexion.prepareStatement(EXISTE_USUARIO);
            exisateUsuario.setString(1, nick);
            ResultSet usuario = exisateUsuario.executeQuery();
            existe=usuario.first();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return existe;
    }

    /**
     * <b>Cabecera</b>: public static boolean usuarioValido (String nick, String password)</br>
     * <b>Descripcion</b>: </br>
     * <b>Entradas</b>: </br>
     * <b>Salida</b>: </br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: </br>
     * @param nick
     * @param password
     * @return
     */
    public static boolean usuarioValido (String nick, String password){
        boolean correcto = false;

        try {
            PreparedStatement comprobarUsuario = conexion.prepareStatement(COMPRUEBA_USUARIO);
            comprobarUsuario.setString(1,nick);
            comprobarUsuario.setString(2,password);
            ResultSet valido = comprobarUsuario.executeQuery();
            correcto=valido.getBoolean(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return correcto;
    }

    /**<b>Cabecera</b>: public static boolean ingresarUsuario(String[] datosUsuario)</br>
     * <b>Descripcion</b>: inserta un usuario en la base de datos </br>
     * <b>Entradas</b>: </br>
     * <b>Salida</b>: </br>
     * <b>Precondiciones</b>: El array es de tamaño 4 y tendrá al menos datos validos en las dos primeras casillas </br>
     * <b>Postcondiciones</b>: </br>
     *
     * @param datosUsuario, array de String
     * @return boolean, usuario insertado
     */
    public static boolean ingresarUsuario(String[] datosUsuario){
        boolean insertado = true;

        try {
            CallableStatement ingresarUsuario = conexion.prepareCall(EXC_INSERTA_USUARIO);
            ingresarUsuario.setString(1,datosUsuario[0]);
            ingresarUsuario.setString(2,datosUsuario[1]);
            ingresarUsuario.setString(3,datosUsuario[2]);
            ingresarUsuario.setString(4,datosUsuario[3]);
            insertado=ingresarUsuario.execute(); //Debe devolver true en caso de ejecutarse(?)
                                                //Si no funciona, insertado=true
                                                //      en catch, insertado = false
        } catch (SQLException throwables) {
            //throwables.printStackTrace();
            insertado = false; //error al insertar
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
