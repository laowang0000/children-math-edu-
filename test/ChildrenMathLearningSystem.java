import javax.swing.*;

public class ChildrenMathLearningSystem {
    public static void main(String[] args) {
        // Create the UserManager and launch the login/register screen.
        UserManager userManager = new UserManager();
        new MathGUI(userManager);
    }
}
