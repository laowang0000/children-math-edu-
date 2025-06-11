import java.io.*;
import java.util.*;

public class UserManager {
    private static final String USER_FILE = "users.txt";
    private List<User> users = new ArrayList<>();

    public UserManager() {
        loadUsers();
    }

    private void loadUsers() {
        File userFile = new File(USER_FILE);
        if (!userFile.exists()) {
            try {
                userFile.createNewFile();  // Create the file if it doesn't exist.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (Scanner scanner = new Scanner(userFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] credentials = line.split(",");
                if (credentials.length == 3) {  // Ensure valid data format.
                    users.add(new User(credentials[0], credentials[1], credentials[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String name, String username, String password) {
        for (User user : users) {
            if (user.username.equals(username)) {
                return false;  // Username already exists.
            }
        }

        users.add(new User(name, username, password));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))) {
            writer.write(name + "," + username + "," + password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public User loginUser(String username, String password) {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;  // Successful login.
            }
        }
        return null;  // Invalid login.
    }
}
