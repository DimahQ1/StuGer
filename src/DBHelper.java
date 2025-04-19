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
        String sql = "SELECT * FROM users";
        try(Connection conn = DBHelper.connect()){

            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql);
            System.out.println("\t All Students");
            while(rs.next()){
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("mark") + " | " +
                                rs.getString("email")
                );
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public static void InsertData(String name, int mark, String email){
        String sql = "INSERT INTO users(name , mark , email) VALUES(?, ?, ?)";
        try(Connection conn = DBHelper.connect()){
            var pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setInt(2,mark);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
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
            select = scanner.nextInt();
            switch (select){
                case 0: break;
                case 1: DBHelper.DisplayAll();continue;
                case 2: DBHelper.InsertData("Peter" , 10, "PeterPortof@gmail.com");continue;
                default:
                    System.out.println("WTF? ");continue;
            }
        }
        scanner.close();
    }
}
