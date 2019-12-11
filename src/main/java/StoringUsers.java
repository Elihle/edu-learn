import java.sql.PreparedStatement;
import java.util.*;

public class StoringUsers {

    final String ALL_SUBJECTS = "select * from subjects";
    final String SPECIFIC_SUBJECT = "select * from subjects where name = ?";
    final String ADD_USER = "insert into create_users (name)";
    Map<String, Integer> usersLogged = new HashMap<>();
    PreparedStatement subjectsPS;
    PreparedStatement specificSubjectPS;

    public PreparedStatement getAvailableSubjects() {
        return subjectsPS;
    }

    public void storeUser(String name) {
        if (usersLogged.containsKey(name)) {
            usersLogged.put(name, usersLogged.get(name) + 1);
        }
        else {
            usersLogged.put(name, 1);
        }
    }
    public void showStoredUsers() {
        for (Map.Entry<String, Integer> map : usersLogged.entrySet()) {
            System.out.println("User"+ "            " + "No of times logged in");
            System.out.println(map.getKey() + "            " + map.getValue());
        }
    }
}
