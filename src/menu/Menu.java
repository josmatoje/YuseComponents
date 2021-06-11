package menu;


public class Menu {

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


    public static String[] datosNuevoUsuario () {
        String[] datosUsuario = new String[4];



        return datosUsuario;
    }

}
