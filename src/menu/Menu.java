package menu;


import java.util.Scanner;

public class Menu {

    //Teclado
    private static Scanner teclado  = new Scanner(System.in);

    //CONSTANTES PARA LOS MENSAJES
    private static final String MENU_PRINCIPAL =
            new StringBuilder( "**********************************************\n")
                    .append("¿Qué desea realizar?\n")
                    .append("1 - Crear un test nuevo\n")
                    .append("2 - Realizar un test\n")
                    .append("************************************************\n").toString();
    private static final String PREGUNTA_OPCION1 =
            new StringBuilder("**********************************************\n")
                    .append("¿Cómo se llama el test?").toString();
    private static final String TEST_CREADO=
            new StringBuilder("Test creado satisfactoriamente en el archivo \n")
                    .append("************************************************\n").toString();

    private static final String ERROR_VALOR ="Error valor introducido invalido, ingrese uno de nuevo:";

    private static final String ERRORES = "MENSAJE DE ERROR";


    /**
     * <b>Cabecera</b>: public static String[] datosNuevoUsuario ()</br>
     * <b>Descripcion</b>: </br>
     * <b>Entradas</b>: --</br>
     * <b>Salida</b>:Un array de cadenas con cuatro espacios que contiene datos de un nuevoi usuario</br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: Al menos las dos primeras casillas del array tendran datos</br>
     *
     * @return
     */
    public static String[] datosNuevoUsuario () {
        String[] datosUsuario = new String[4];

        System.out.println();//Ingrese el nick de su usuario
        do{
            datosUsuario[0] = teclado.nextLine();
        }while ()


        return datosUsuario;
    }

    public static void cerrarTeclado(){
        teclado.close();
    }

}
