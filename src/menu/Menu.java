package menu;

import modelo.DataAcces;

import java.sql.Connection;
import java.sql.SQLException;

public class Menu {

    public static void main(String[] args) {
        Connection conexion = DataAcces.abrirConexion();
        try {
            if (conexion.isClosed())
                System.out.println("cerrado");
            else
                System.out.println("abierto");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
