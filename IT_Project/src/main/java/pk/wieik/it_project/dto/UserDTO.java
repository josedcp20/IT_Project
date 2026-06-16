package pk.wieik.it_project.dto;

public class UserDTO {
    private int id;
    private String user;
    private String password;
    private int privileges;

    public UserDTO() {
    }

    public UserDTO(String user, String password, int privileges) {
        this.user = user;
        this.password = password;
        this.privileges = privileges;
    }

    public UserDTO(int id, String user, String password, int privileges) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.privileges = privileges;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public int getPrivileges() { return privileges; }
    public void setPrivileges(int privileges) { this.privileges = privileges; }

    @Override
    public String toString() {
        return "UserDTO{id=" + id +
                ", user='" + user + '\'' +
                ", privileges=" + privileges + '}';
        // Nota: no exponemos password en toString por seguridad
    }
}
