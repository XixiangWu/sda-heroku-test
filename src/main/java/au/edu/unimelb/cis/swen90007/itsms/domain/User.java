package au.edu.unimelb.cis.swen90007.itsms.domain;

import au.edu.unimelb.cis.swen90007.itsms.database.EmployeeGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.TechGateway;
import au.edu.unimelb.cis.swen90007.itsms.database.UserFinder;
import au.edu.unimelb.cis.swen90007.itsms.database.UserGateway;

/**
 * The User class defines the Domain layer interface
 * for User data,
 */
public abstract class User {

    private int id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    public User(int id, String username, String password, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(UserGateway userGateway) {
        this.id = userGateway.getId();
        this.username = userGateway.getUsername();
        this.password = userGateway.getPassword();
        this.firstName = userGateway.getFirstName();
        this.lastName = userGateway.getLastName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static User getUser(int userId) {
        UserFinder finder = new UserFinder();
        UserGateway userGateway = finder.findUserById(userId);
        User result = null;
        if (userGateway != null) {
            if (userGateway instanceof EmployeeGateway) {
                result = new Employee((EmployeeGateway)userGateway);
            } else if (userGateway instanceof TechGateway) {
                result = new Tech((TechGateway)userGateway);
            }
        }
        return result;
    }

    public static User getUserByUsername(String username) {
        UserFinder finder = new UserFinder();
        UserGateway userGateway = finder.findUserByUsername(username);
        User result = null;
        if (userGateway != null) {
            if (userGateway instanceof EmployeeGateway) {
                result = new Employee((EmployeeGateway)userGateway);
            } else if (userGateway instanceof TechGateway) {
                result = new Tech((TechGateway)userGateway);
            }
        }
        return result;
    }
}
