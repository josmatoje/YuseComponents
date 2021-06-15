package modelo;

public class Usuario {

    private String nick;
    private String password;

    public Usuario() {
        this.nick = "null";
        this.password = "null";
    }

    public Usuario(String nick, String password) {
        this.nick = nick;
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }

}
