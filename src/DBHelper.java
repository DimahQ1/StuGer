import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBHelper {
    public static Connection connect(){
    String url = "jdbc:sqlite:Students.db";
    try{
        return DriverManager.getConnection(url);
    }catch (SQLException ex){
        System.out.println("Exception: " + ex.getMessage());
        return null;
    }
    }
    public static void DisplayAll(){
        String sql = "SELECT * FROM Students_table";
        try(Connection conn = DBHelper.connect()){

            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql);
            System.out.println("\t All Students");
            while(rs.next()){
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getDouble("mark") + " | " +
                                rs.getString("email") + " | " +
                                rs.getString("phoneNumber")
                );
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void InsertData(String name, double mark, String email, String phoneNumber){
        String sql = "INSERT INTO Students_table(name , mark , email , phoneNumber) VALUES(?, ?, ?, ?)";
        try(Connection conn = DBHelper.connect()){
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setDouble(2,mark);
            pstmt.setString(3, email);
            pstmt.setString(4, phoneNumber);
            pstmt.executeUpdate();
            System.out.println("Misson Compelete");
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void GetData(){
        String name, email, phoneNumber;
        double mark;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Student's name: ");
        name = scanner.nextLine();

        System.out.print("Enter " + name + "'s mark: ");
        mark = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter " + name + "'s email: ");
        email = scanner.nextLine();

        System.out.print("Enter " + name + "'s Phonenumber: ");
        phoneNumber = scanner.nextLine();
        scanner.close();
        DBHelper.InsertData(name, mark, email , phoneNumber);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int select = 100;
        while(select != 0){
            System.out.println("\n1. Display all Students");
            System.out.println("2. Add new Student");
            System.out.println("0. Exit");
            System.out.println("Now what do you want? ");
            System.out.println(" ");
            // Safe input check
            if (scanner.hasNextInt()) {
                select = scanner.nextInt();
                scanner.nextLine(); // Flush newline
            } else {
                System.out.println("❌ Invalid input. Enter a number.");
                scanner.nextLine(); // Clear bad input
                continue;
            }
            switch (select){
                case 0: break;
                case 1: DBHelper.DisplayAll();break;
                case 2: DBHelper.GetData();break;
                default:
                    System.out.println("WTF? ");break;
            }
        }
        scanner.close();
    }
}
