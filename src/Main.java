public class Main {
    public static void main(String[] args) {
        Login loginObj = new Login();
        String role = loginObj.AskLogin();
        System.out.println(role);
        // based on role give the landing pages
    }
}