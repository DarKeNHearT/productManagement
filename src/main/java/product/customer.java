package product;


import java.sql.*;
import java.util.Scanner;

public class customer {
    static Scanner input = new Scanner(System.in);
    public String url = "jdbc:postgresql://localhost:5432/ProductManagement";
    public String username = "postgres";
    public String password = "Nevermore123";



    public void display(){
        boolean condition = true;
        while(condition) {
            System.out.println("1.Add");
            System.out.println("2.update");
            System.out.println("3.delete");
            int result = input.nextInt();

            switch (result){
                case 1:
                    addCustomer();
                    condition=false;
                    break;
                case 2:
                    updateCustomer();
                    condition=false;
                    break;
                case 3:
                    deleteCustomer();
                    condition=false;
                    break;
                default:
                    System.out.println("Incorrect Input");
            }

        }
    }

    public void addCustomer() {

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established");
            System.out.println("------------");
            System.out.print("Enter Customer Name: ");

            input.nextLine();
            String customerName = input.nextLine();

            String command = "INSERT INTO customer (customer_name) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1, customerName);
            int executedRows = statement.executeUpdate();
            System.out.printf("Affected rows: %d\n",executedRows);
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Error Occurred");
            e.printStackTrace();
        }
    }

    public void deleteCustomer(){
        try{
            System.out.println("---------------Available Data----------");
            select();
            Connection connection = DriverManager.getConnection(url,username,password);
            while(true) {
                System.out.println("Delete By:");
                System.out.println("1. Id");
                System.out.println("2. Name");
                System.out.print("Action: ");
                int result = input.nextByte();
                if(result==1)
                {
                    String command = "DELETE FROM customer WHERE customer_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("customer Id: ");
                    statement.setInt(1,input.nextInt());
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n",executedRows);
                    connection.close();
                    break;
                }
                else if(result == 2){
                    String command = "DELETE FROM customer WHERE customer_name = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Customer Name:");
                    input.nextLine();
                    String dummy = input.nextLine();
                    statement.setString(1,dummy);
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n",executedRows);
                    connection.close();
                    break;
                }
            }

        }
        catch(SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void updateCustomer(){
        try{
            System.out.println("---------------Available Data----------");
            select();
            Connection connection = DriverManager.getConnection(url,username,password);
            System.out.print("Enter Customer Id:");
            int customerId = input.nextInt();
            String command = "UPDATE customer SET customer_name = ? WHERE customer_id = ?";
            PreparedStatement statement = connection.prepareStatement(command);
            System.out.print("Enter Name: ");
            input.nextLine();
            statement.setString(1, input.nextLine());
            statement.setInt(2,customerId);
            int executedRows = statement.executeUpdate();
            System.out.printf("Affected Rows: %d\n",executedRows);
            connection.close();
        }
        catch(SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    public void select(){
        String sql = "SELECT * FROM customer";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                int id = result.getInt("customer_id");
                String name = result.getString("customer_name");
                System.out.printf("%d %s\n",id,name);
            }
            connection.close();

        }
        catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }

    }
}
