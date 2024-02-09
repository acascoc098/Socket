import java.util.List;

public class Listado {
    private List<Usuarios> users;

    public Listado(List<Usuarios> users) {
        this.users = users;
    }

    public List<Usuarios> getUsers() {
        return users;
    }

    public void setUsers(List<Usuarios> users) {
        this.users = users;
    }
}
