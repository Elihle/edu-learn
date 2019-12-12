import java.sql.*;
import java.util.*;

public class StoringUsers {
    public StoringUsers() {
        try {
            //create connection to DB
            allSubjectsPS = connection.prepareStatement(ALL_SUBJECTS);
            findSubjectPS = connection.prepareStatement(FIND_SUBJECT);
            allUsersPS = connection.prepareStatement(ALL_USERS);
            findUserPS = connection.prepareStatement(FIND_USER);
            addUserPS = connection.prepareStatement(ADD_USER);
            updateUser = connection.prepareStatement(UPDATE_USER);
            deleteAllUsersPS = connection.prepareStatement(DELETE_ALL_USERS);
            deleteUserPS = connection.prepareStatement(DELETE_USER);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    final String ALL_SUBJECTS = "select * from subjects";
    final String FIND_SUBJECT = "select * from subjects where name = ?";
    final String ALL_USERS = "select * from users";
    final String FIND_USER = "select * from subjects where name = ?";
    final String ADD_USER = "insert into users (name) values(?)";
    final String UPDATE_USER = "update users set counter = counter + 1 where name = ?";
    final String DELETE_USER = "delete from users where name = ?";
    final String DELETE_ALL_USERS = "delete from users";
    final String DB_LINK = "";

    Connection connection;
    PreparedStatement allSubjectsPS;
    PreparedStatement findSubjectPS;
    PreparedStatement findUserPS;
    PreparedStatement allUsersPS;
    PreparedStatement addUserPS;
    PreparedStatement updateUser;
    PreparedStatement deleteUserPS;
    PreparedStatement deleteAllUsersPS;

    public void storeUser(String name) {
        try {
            findUserPS.setString(1, name);
            ResultSet resultSet = findUserPS.executeQuery();

            if (resultSet.next()) {
                updateUser.setString(1, name);
                updateUser.execute();
            } else {
                addUserPS.setString(1, name);
                addUserPS.setInt(2, 1);
                addUserPS.execute();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public String showStoredUsers() {
        Map<String, Integer> usersLogged = new HashMap<>();
        try {
            ResultSet resultSet = allSubjectsPS.executeQuery();
            while (resultSet.next()) {
                usersLogged.put(resultSet.getString("name"), resultSet.getInt("counter"));
            }
            for (Map.Entry<String, Integer> map : usersLogged.entrySet()) {
                System.out.println("User"+ "            " + "No of times logged in");
                System.out.println(map.getKey() + "            " + map.getValue());
            }
            if (usersLogged.isEmpty()) {
                return "No users logged";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return usersLogged.toString();
    }
    public Integer allUsers() {
        Map<String, Integer> usersLogged = new HashMap<>();
        try {
            ResultSet resultSet = allUsersPS.executeQuery();
            while (resultSet.next()) {
                usersLogged.put(resultSet.getString("name"), resultSet.getInt("counter"));
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return usersLogged.size();
    }
}
