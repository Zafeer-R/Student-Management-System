package StudentManagementSystem.src;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        System.out.println("\t\t\t-----------------------Welcome to student management system-----------------------");
        System.out.print("1.Login\n2.Enroll\n3.Exit\nEnter you choice: ");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        while(choice!=3) {
            switch(choice) {
                case 1: boolean ans = login();
                        if(ans) {
                            System.out.println("Login successful");
                            System.out.print("\033[H\033[2J");
                            int logChoice=-1;
                            while(logChoice!=4) {
                                System.out.print("1.Display student details\n2.Edit student details\n3.Delete student details\n4.Exit\nEnter your choice: ");
                                logChoice=in.nextInt();
                                switch (logChoice) {
                                    case 1: display();
                                            System.out.println("Do you wish to continue ? Press 1 for continuing.");
                                            logChoice=in.nextInt();
                                            if(logChoice!=1) logChoice=4;
                                            break;
                                    case 2: edit();
                                            System.out.println("Do you wish to continue ? Press 1 for continuing.");
                                            logChoice=in.nextInt();
                                            if(logChoice!=1) logChoice=4;
                                            break;
                                    case 3: delete();
                                            System.out.println("Do you wish to continue ? Press 1 for continuing.");
                                            logChoice=in.nextInt();
                                            if(logChoice!=1) logChoice=4;
                                            break;
                                    case 4: logChoice=4;
                                            break;
                                    default: System.out.println("Wrong choice :(");
                                            //  choice=-1;
                                             break;
                                }
                            }
                            choice=3;
                        } else {
                            System.out.println("Login unsuccessful");
                            System.out.print("Do you want to retry?\nPress '1' for retrying: ");
                            int input;
                            input = in.nextInt();
                            if(input==1) {
                                choice = 1;
                            } else {
                                choice = 3;
                            }
                        }
                        break;
                case 2: enroll();
                        choice = 3;
                        break;
                case 3: choice=3;
                        break;
                default: System.out.println("Wrong choice :(\nPlease try again !");
                         System.out.print("1.Login\n2.Enroll\n3.Exit\nEnter you choice: ");
                         choice=in.nextInt();                         
                         break;
            }
        }
    }

    private static boolean login() {
        Scanner in = new Scanner(System.in);
        boolean result = false;
        String username = "";
        String password = "";
        String usernameCheck="";
        String passwordCheck="";
        System.out.print("\nEnter username: ");
        username=in.nextLine();
        System.out.print("\nEnter password: ");
        password=in.nextLine();
        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","Admin@2541");
            java.sql.Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("Select username,password from login");
            while(rs.next()) {
                usernameCheck = rs.getString(1);
                passwordCheck = rs.getString(2);
                if(username.equals(usernameCheck) && password.equals(passwordCheck)) {
                    result = true;
                    break;
                }
            }
        } catch(Exception e) {

            System.out.println(e);

        }

        return result;
    }

    private static void enroll() {
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to student enroll section\n");

        System.out.print("\nEnter student ID: ");
        int sId = in.nextInt();
        
        in.nextLine();
        System.out.print("\nEnter name: ");
        String sName = in.nextLine();

        System.out.print("\nEnter student branch: ");
        String sBranch = in.nextLine();

        System.out.print("\nEnter student section: ");
        String sSection = in.nextLine();

        System.out.print("\nEnter student Year: ");
        int sYear = in.nextInt();
        
        in.nextLine();
        System.out.print("\nEnter student email: ");
        String sEmail = in.nextLine();

        System.out.print("\nEnter student contact: ");
        long sContact = in.nextLong();

        System.out.println("Details saved successfully!\nNow enter login credentials");

        in.nextLine();
        System.out.print("Enter username: ");
        String username = in.nextLine();

        System.out.print("\nEnter password: ");
        int password = in.nextInt();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","Admin@2541");
            String query = "insert into enroll (sId,sName,username,password,sBranch,sSection,sYear,sEmail,sContact)" + "values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,sId);
            ps.setString(2,sName);
            ps.setString(3,username);
            ps.setInt(4,password);
            ps.setString(5,sBranch);
            ps.setString(6,sSection);
            ps.setInt(7,sYear);
            ps.setString(8,sEmail);
            ps.setLong(9,sContact);
            int i=ps.executeUpdate();
            if(i>0) {
                System.out.println("Information updated successfully!");
                String loginQuery = "insert into login (username,password)"+"values (?,?)";
                PreparedStatement loginPs = con.prepareStatement(loginQuery);
                loginPs.setString(1,username);
                loginPs.setInt(2,password);
                // loginPs.setInt(3,sId);
                loginPs.executeUpdate();
            }
            else System.out.println("Information updation unsuccessfully :(");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void display() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","Admin@2541");
            String query = "select sId,sName,username,sBranch,sSection,sYear,sEmail,sContact from enroll";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery(query);
            System.out.print("sId\t\tsName\t\tusername\t\tsBranch\t\tsSection\tsYear\t\tsEmail\t\tsContact\n");
            while(rs.next()) {
                System.out.println(rs.getInt(1)+"\t\t "+rs.getString(2)+"\t\t "+rs.getString(3)+"\t\t "+rs.getString(4)+"\t\t "+rs.getString(5)+"\t\t "+rs.getString(6)+"\t "+rs.getString(7)+"\t\t "+rs.getString(8));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void delete() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","Admin@2541");
            String query = "delete from enroll where sId=?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.print("Enter sId to delete the student records ");
            Scanner in = new Scanner(System.in);
            int sid = in.nextInt();
            ps.setInt(1,sid);
            int i = ps.executeUpdate();
            if(i>0) System.out.println("Record successfully deleted!");
            else System.out.println("Record were not deleted. Try again.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void edit() {
        Scanner in = new Scanner(System.in);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sms", "root","Admin@2541");
            System.out.print("Enter the student id for the records to be edited: ");
            int id = in.nextInt();
            System.out.println("Fields available for edition:\n1.Name\n2.Branch\n3.Section\n4.Year\n5.Contact");
            System.out.print("Enter your choice: ");
            int choice = in.nextInt();
            String query="";
            PreparedStatement ps;
            switch (choice) {
                case 1: query="update enroll set sName=? where sId=?";
                        ps=con.prepareStatement(query);
                        String newName;
                        System.out.print("\nEnter changed name: ");
                        in.nextLine();
                        newName=in.nextLine();
                        ps.setString(1,newName);
                        ps.setInt(2,id);
                        ps.executeUpdate();
                        break;
                case 2: query="update enroll set sBranch=? where sId=?";
                        ps=con.prepareStatement(query);
                        String newBranch;
                        System.out.print("\nEnter changed branch: ");
                        in.nextLine();
                        newBranch=in.nextLine();
                        ps.setString(1,newBranch);
                        ps.setInt(2,id);
                        ps.executeUpdate();
                        break;
                case 3: query="update enroll set sSection=? where sId=?";
                        ps=con.prepareStatement(query);
                        String newSection;
                        System.out.print("\nEnter changed section: ");
                        in.nextLine();
                        newSection=in.nextLine();
                        ps.setString(1,newSection);
                        ps.setInt(2,id);
                        ps.executeUpdate();
                        break;
                case 4: query="update enroll set sYear=? where sId=?";
                        ps=con.prepareStatement(query);
                        int newYear;
                        System.out.print("\nEnter changed year: ");
                        in.nextLine();
                        newYear=in.nextInt();
                        ps.setInt(1,newYear);
                        ps.setInt(2,id);
                        ps.executeUpdate();
                        break;
                case 5: query="update enroll set sContact=? where sId=?";
                        ps=con.prepareStatement(query);
                        int newContact;
                        System.out.print("\nEnter changed contact: ");
                        in.nextLine();
                        newContact=in.nextInt();
                        ps.setInt(1,newContact);
                        ps.setInt(2,id);
                        ps.executeUpdate();
                        break;        
                default: System.out.println("Wrong choice :(");
                         break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}