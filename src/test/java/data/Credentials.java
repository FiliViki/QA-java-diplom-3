package data;

public class Credentials {
    public String email;
    public String password;

    public Credentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public Credentials() {}

    public static Credentials from (User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
}
