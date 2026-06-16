package pk.wieik.it_project.dto;

public class SettingsDTO {
    private int id;
    private int userId;
    private String name;
    private String surname;
    private int age;

    public SettingsDTO() {
    }

    public SettingsDTO(int userId, String name, String surname, int age) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public SettingsDTO(int id, int userId, String name, String surname, int age) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "SettingsDTO{id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age + '}';
    }
}
