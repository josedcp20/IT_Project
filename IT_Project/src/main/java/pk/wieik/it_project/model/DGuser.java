package pk.wieik.it_project.model;

public class DGuser {

    public DGuser(){

    }
    public DGuser(String login, String password, int privileges){
        this.login = login;
        this.password = password;
        this.privileges = privileges;
    }
    private String login = "";
    private int privileges = -1;

    private String name = "";
    private String surname = "";
    private String password = "";
    private Integer age = null;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getPrivileges() {
        return privileges;
    }

    public void setPrivileges(int privileges) {
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = filter(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = filter(surname);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAgeS(){
        if (age == null) return "";
        if (age >= 0) {
            return "" + age;
        } else return "";
    }

    @Override
    public String toString() {
        return "DGuser{" +
                "login='" + login + '\'' +
                ", privileges=" + privileges +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }

    public String filter(String input) {

        StringBuffer filtered = new StringBuffer();
        char c;

        for (int i=0; i<input.length(); i++)
        {
            c = input.charAt(i);
            switch(c)
            {
                case '<': filtered.append("&lt;"); break;
                case '>': filtered.append("&gt;"); break;
                case '"': filtered.append("&quot;"); break;
                case '&': filtered.append("&amp;"); break;
                default: filtered.append(c);
            }
        }
        return filtered.toString();
    }

}
