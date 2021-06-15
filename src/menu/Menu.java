package menu;


import modelo.Usuario;

import java.sql.CallableStatement;
import java.util.Scanner;

import static modelo.DataAcces.*;

public class Menu {

    //Teclado
    private static Scanner teclado  = new Scanner(System.in);

    //CONSTANTES PARA LOS MENSAJES
    private static final String MENSAJE_BIENVENIDA = "Bienvenido a YuseComponents, tienda especializada en componentes electronicos de todo tipos.";

    private static final String MENU_INICIO =
            new StringBuilder("Seleccione una opcion:\n")
                    .append("**********************************************\n")
                    .append("1 - Iniciar sesión\n")
                    .append("2 - Crear cuenta\n")
                    .append("************************************************\n").toString();
    private static final String INGRESAR_NICK= "Ingrese el nick de su usuario";
    private static final String NICK_NO_EXISTE= "El usuario no existe";
    private static final String DESEA_REGISTRARSE= "¿Desea registrar este usuario?";
    private static final String ESCRIBIR_NOMBRE= "¿Desea introducir su nombre?";
    private static final String ESCRIBIR_APELLIDO= "¿Desea introducir su apellido?";
    private static final String DESEA_SALIR= "¿Desea salir?";
    private static final String NICK_USO= "Este nick ya está en uso, introduzca otro";
    private static final String INGRESAR_PASSWORD= "Ingrese una contraseña de más de ocho caracteres";
    private static final String ERROR_PASSWORD= "La contraseña no coincide, pruebe de nuevo";
    private static final String INGRESAR_NOMBRE= "Ingrese su nombre";
    private static final String INGRESAR_APELLIDO= "Ingrese su o sus apellidos";
    private static final String REPETIR_PASWORD= "Vuelva a introducir la contraseña";

    private static final String MENU_PRINCIPAL = "En construccion"; //todo crear menu

    private static final String ERROR_VALOR ="Error valor introducido invalido, ingrese uno de nuevo:";
    private static final String ERRORES = "MENSAJE DE ERROR";

    /**
     * <b>Cabecera:</b> public static boolean leerValidarRespuestaSiNo()
     *   <br>
     * <b>Comentario:</b> Metodo que lee y valida si una respuesta es S o N. En funcion de esta se devolvera un valor boleano u otro.
     * <br>
     * <b>Precondiciones:</b> Ninguna
     * <br>
     * <b>Entrada:</b> Ninguna
     * <br>
     * <b>Salida:</b> Boolean afirmativo
     * <br>
     * <b>Postcondiciones:</b> Este metodo se trata de una funciona ya que devuelve en este caso un boleano(afirmativo) cuyo valor sera:<br>
     * 	&nbsp;&nbsp;&nbsp;&nbsp;-true: si respuesta es 's'.<br>
     *  &nbsp;&nbsp;&nbsp;&nbsp;-false: si respuesta es 'n.<br>
     *   	<br>
     *  @return afirmativo	<br>
     */
    public static boolean leerValidarRespuestaSiNo() {
        char respuesta=' ';
        boolean afirmativo = true;

        System.out.println("Introduzca S (si) / N (no)");
        do {
            try {
                respuesta = teclado.nextLine().toLowerCase().charAt(0);
            }catch(Exception e){
                //Hemos introducido un try catch para evitar que salte una excepcion cuando el usuario no ingrese un caracter y le de directamente a intro
            }
            if (respuesta != 's' && respuesta != 'n') {
                System.out.println(ERROR_VALOR);
            }

        }while(respuesta != 's' && respuesta != 'n');

        if(respuesta == 'n') { //Si la respuesta es No
            afirmativo = false;
        }
        return afirmativo;
    }

    /**
     * <b>Cabecera:</b> public static int leerValidarNumeroEntreRango(int valorInicial, int valorFinal)
     * <br>
     * <b>Comentario:</b> Este metodo se encarga de leer y validar que un numero este entre un rango.
     * <br>
     * <b>Precondiciones:</b> El numero valorInicial tiene que ser menor que valorFinal
     * <br>
     * <b>Entradas:</b> int valorInicial, int valorFinal
     * <br>
     * <b>Salidas:</b> int numero
     * <br>
     * <b>Postcondiciones:</b> Este metodo se trata de un funcion ya que devuelve un tipo de dato, entero(numero) en este caso,
     *   				 el cual estara entre un rango(valorInicial y valorFinal) o en valorInicial-1 en caso de no realizar eleccion.
     *   				 <br>
     * @param valorInicial
     * @param valorFinal
     * <br>
     * @return numero
     */
    public static int leerValidarNumeroEntreRango(int valorInicial, int valorFinal){
        int numero=valorInicial-1;
        String cadena;
        boolean salir=false;

        System.out.println("Ingrese un numero entre "+valorInicial+" y "+valorFinal+":  (Escriba 'X' para retroceder)");
        while(numero < valorInicial || numero > valorFinal || !salir) {
            try {
                cadena=teclado.nextLine().toUpperCase();
                if(!cadena.equals("X")) {
                    numero = Integer.parseInt(cadena);
                    if (numero < valorInicial || numero > valorFinal) {
                        System.out.println("Numero ingresado no valido, vuelva intentarlo o introduzca 'X' para salir: ");
                    }
                }else{
                    salir=true;
                    numero=valorInicial-1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Introduzca un numero");
                numero=valorInicial-1; //Aseguramos que el numero no es correcto para q vuelva a entrar al bucle
            }
        }

        return numero;
    }


    /**
     * <b>Cabecera</b>: public static void mensajeBienvenida()
     * </br>
     * <b>Descripcion</b>: Muestra un mensaje de bienvenida
     * </br>
     * <b>Entradas</b>: --
     * </br>
     * <b>Salida</b>: --
     * </br>
     * <b>Precondiciones</b>: --
     * </br>
     * <b>Postcondiciones</b>: --
     * </br>
     * @return
     */
    public static void mensajeBienvenida(){
        System.out.println(MENSAJE_BIENVENIDA);
    }

    /**
     * <b>Cabecera</b>: public static void menuPrincipal()
     * </br>
     * <b>Descripcion</b>: Muestra el menu principal y devuelve la eleccion
     * </br>
     * <b>Entradas</b>: --
     * </br>
     * <b>Salida</b>: entero eleccion
     * </br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: devuelve 1 o 2 segun las elecciones o 0 en caso de no elegir nada.
     * </br>
     * @return eleccion, entero
     */
    public static int menuInicio(){
        System.out.println(MENU_INICIO);
        return leerValidarNumeroEntreRango(1,2);
    }

    /**
     * <b>Cabecera</b>: public static String leeUsuario()</br>
     * <b>Descripcion</b>: Pide un nombre de usuario y lo devuelve en forma de cadena</br>
     * <b>Entradas</b>: --</br>
     * <b>Salida</b>: cadena de caracteres</br>
     * <b>Precondiciones</b>: --</br>
     * <b>Postcondiciones</b>: --</br>
     * @return usuario, candena
     */
    private static String leeUsuario(){
        System.out.println(INGRESAR_NICK);
        return teclado.nextLine();
    }

    /**
     * <b>Cabecera</b>: public static String leePasswoed()</br>
     * <b>Descripcion</b>: Pide una conraseña y lo devuelve en forma de cadena</br>
     * <b>Entradas</b>: --</br>
     * <b>Salida</b>: cadena de caracteres</br>
     * <b>Precondiciones</b>: --</br>
     * <b>Postcondiciones</b>: --</br>
     * @return password, candena
     */
    private static String leePassword(){
        System.out.println(INGRESAR_PASSWORD);
        return teclado.nextLine();
    }
    private static String leeNombre(){
        System.out.println(INGRESAR_NOMBRE);
        return teclado.nextLine();
    }
    private static String leeApellido(){
        System.out.println(INGRESAR_APELLIDO);
        return teclado.nextLine();
    }

    /**
     * <b>Cabecera</b>: public static Usuario registroUsuario (String nick, String password)</br>
     * <b>Descripcion</b>: Comprueba si un usuario es correcto</br>
     * <b>Entradas</b>: --</br>
     * <b>Salida</b>:Un array de cadenas con cuatro espacios que contiene datos de un nuevoi usuario</br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: Devolverá null en caso de no registrar el usuario</br>
     *
     * @return
     */
    public static Usuario registroUsuario () {
        Usuario registrado = null;
        boolean salir = false;
        String nick, password;

        if(existeUsuario(nick=leeUsuario())){
            while(!usuarioValido(nick, password=leePassword()) || !salir){
                System.out.println(ERROR_PASSWORD);
                System.out.println(DESEA_SALIR);
                salir=leerValidarRespuestaSiNo();
            }
            registrado= new Usuario(nick,password);
        }else{
            System.out.println(NICK_NO_EXISTE);
            registrado= new Usuario(nick,"null");
        }

        return registrado;
    }

    public static void deseaRegistrarse(){
        System.out.println(DESEA_REGISTRARSE);
    }

    public static boolean registrar(String nick){
        boolean correcto = false;
        String password;

        password = leePassword();
        while(!correcto) {
            System.out.println(REPETIR_PASWORD);
            correcto = password.equals(leePassword());
        }


        return ingresarUsuario(new String[]{nick, password, "null", "null"});
    }
    /**
     * <b>Cabecera</b>: public static String[] datosNuevoUsuario ()</br>
     * <b>Descripcion</b>: pide los datos necesarios para realizar un nuevo registro de un usuario de la plataforma</br>
     * <b>Entradas</b>: --</br>
     * <b>Salida</b>:Un array de cadenas con cuatro espacios que contiene datos de un nuevoi usuario</br>
     * <b>Precondiciones</b>: </br>
     * <b>Postcondiciones</b>: Al menos las dos primeras casillas del array tendran datos</br>
     *
     * @return
     */
    public static String[] datosNuevoUsuario () {
        String[] datosUsuario = new String[4];
        boolean existe = false, valido= false;
        
        do{
            datosUsuario[0]= leeUsuario();
            if(existe = existeUsuario(datosUsuario[0])){
                System.out.println(NICK_USO);
            }
        }while (existe);

        do{
            datosUsuario[1]= leePassword();
            valido=validarPasword(datosUsuario[1]);
        }while(valido);

        System.out.println(ESCRIBIR_NOMBRE);
        if(leerValidarRespuestaSiNo()){
            datosUsuario[2]= leeNombre();
        }

        System.out.println(ESCRIBIR_APELLIDO);
        if(leerValidarRespuestaSiNo()){
            datosUsuario[3]= leeApellido();
        }

        return datosUsuario;
    }

    private static boolean validarPasword(String password) {
        return password.length()>8;
    }

    public static void menuPrincipal(){
        System.out.println(MENU_PRINCIPAL);
    }

    public static void cerrarTeclado(){
        teclado.close();
    }

}
