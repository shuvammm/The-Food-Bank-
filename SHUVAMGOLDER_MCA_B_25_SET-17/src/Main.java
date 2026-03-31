import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    //  1. Record Donation -----------------------------------------------------
    public static void recordDonation() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter donor name: ");
            String donor = sc.nextLine();

            System.out.print("Enter item: ");
            String item = sc.nextLine();

            System.out.print("Enter quantity (kg): ");
            double qty = sc.nextDouble();
            sc.nextLine();

            String query = "INSERT INTO donations(donor, item, qty_kg) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, donor);
            ps.setString(2, item);
            ps.setDouble(3, qty);

            ps.executeUpdate();
            System.out.println("Donation recorded successfully");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //  2. Register Family section ----------------------------------------------------
    public static void registerFamily() {
        try {
            Connection con = DBConnection.getConnection();

            System.out.print("Enter family name: ");
            String name = sc.nextLine();

            System.out.print("Enter address: ");
            String address = sc.nextLine();

            System.out.print("Enter family size: ");
            int size = sc.nextInt();
            sc.nextLine();

            String query = "INSERT INTO families(name, address, size) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setInt(3, size);

            ps.executeUpdate();
            System.out.println("Family registered successfully");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 3. Distribute Food section ----------------------------------------------------
    public static void distributeFood() {
        try {
            Connection con = DBConnection.getConnection();

            // Show registered families
            String showFamilies = "SELECT * FROM families";
            PreparedStatement ps1 = con.prepareStatement(showFamilies);
            ResultSet rs = ps1.executeQuery();

            System.out.println("\n===== Registered Families =====");
            while (rs.next()) {
                System.out.println(
                    rs.getInt("fam_id") + " - " +
                    rs.getString("name") + " | " +
                    rs.getString("address") + " | Size: " +
                    rs.getInt("size")
                );
            }

            System.out.print("\nEnter family ID: ");
            int famId = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter item: ");
            String item = sc.nextLine();

            System.out.print("Enter quantity (kg): ");
            double qty = sc.nextDouble();
            sc.nextLine();

            String query = "INSERT INTO distributions(fam_id, item, qty_kg) VALUES (?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(query);

            ps2.setInt(1, famId);
            ps2.setString(2, item);
            ps2.setDouble(3, qty);

            ps2.executeUpdate();
            System.out.println("Food distributed successfully");

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // 4. view current stock section ----------------------------------------------------
    public static void viewStock() {
        try {
            Connection con = DBConnection.getConnection();

            String query = """
                SELECT d.item,
                       COALESCE(SUM(d.qty_kg), 0) -
                       COALESCE((SELECT SUM(qty_kg)
                                 FROM distributions
                                 WHERE item = d.item), 0) AS stock
                FROM donations d
                GROUP BY d.item
            """;

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n===== Current Stock =====");
            while (rs.next()) {
                System.out.println(
                    rs.getString("item") + " : " +
                    rs.getDouble("stock") + " kg available"
                );
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //  Main Menu section
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Food Bank System =====");
            System.out.println("1. Record Donation");
            System.out.println("2. Register Family");
            System.out.println("3. Distribute Food");
            System.out.println("4. View Current Stock");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> recordDonation();
                case 2 -> registerFamily();
                case 3 -> distributeFood();
                case 4 -> viewStock();
                case 5 -> {
                    System.out.println("Thank you!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}