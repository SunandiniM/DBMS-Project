import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Login loginObj = new Login();
        String role = loginObj.AskLogin(in);
        System.out.println(role);
        // based on role give the landing pages

        if (role == "Admin") {
            AdminLandingPage adminLandingPageObj = new AdminLandingPage();
            adminLandingPageObj.AskAdmin();
        }else if (role == "customer") {

        } else if (role == "receptionist") {
            Receptionist receptionist = new Receptionist();
            receptionist.LandingPageMenu();
        } else if (role == "manager") {
            Manager manager = new Manager();
            manager.LandingPageMenu();
        } else if (role == "mechanic") {
            
        }
    }
}