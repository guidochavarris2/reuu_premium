package com.example.reeu_premium;

public class URLs {
    private static final String ROOT_URL = "https://polar-cove-80223.herokuapp.com/registrationapi.php?apicall=";
    private static final String ROOT_URL_AZURE = "https://reunionguido.azurewebsites.net/crear_evento.php?apicall=";
    public static final String URL_REGISTER = ROOT_URL + "signup";
    public static final String URL_LOGIN= ROOT_URL + "login";
    public static final String URL_LISTADO= ROOT_URL + "login_id";
    public static final String URL_PUBLICO= ROOT_URL + "publico";
    public static final String URL_USUARIOS_INVITADOS = ROOT_URL + "listaUsuarios";
    public static final String URL_MISEVENTOS=ROOT_URL + "miseventos";
    public static final String URL_INVITADO= ROOT_URL + "invitado";
    private static final String ROOT_URL2 = "https://polar-cove-80223.herokuapp.com/crear_evento.php?apicall=";
    private static final String ROOT_EDITAR = "https://polar-cove-80223.herokuapp.com/editar_perfil.php";
    public static final String URL_INGRESAR = "https://polar-cove-80223.herokuapp.com/ingresar_Evento.php?apicall=ingresar";
    public static final String URL_INGRESAR2 = "https://polar-cove-80223.herokuapp.com/ingresar.php";
    public static final String URL_EDITAR = ROOT_EDITAR;
    public static final String URL_CREAR = ROOT_URL_AZURE + "crear";
    public static final String URL_VALIDACION = ROOT_URL + "validacion";


}
