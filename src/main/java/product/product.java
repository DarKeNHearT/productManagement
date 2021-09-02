package product;

import java.sql.*;
import java.util.Scanner;

public class product {
    static Scanner input = new Scanner(System.in);
    public String url = "jdbc:postgresql://localhost:5432/ProductManagement";
    public String username = "postgres";
    public String password = "Nevermore123";
    public byte result = 0;


    public void display(){
        boolean condition = true;
        while(condition) {
            System.out.println("1.Add");
            System.out.println("2.update");
            System.out.println("3.delete");
            int result = input.nextInt();
            switch (result){
                case 1:
                    addProduct();
                    condition=false;
                    break;
                case 2:
                    updateProduct();
                    condition=false;
                    break;
                case 3:
                    deleteProduct();
                    condition=false;
                    break;
                default:
                    System.out.println("Incorrect Input");
            }

        }
    }

    public void addProduct(){

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established");
            System.out.println("------------");
            System.out.print("Enter Product Name: ");
            input.nextLine();
            String productName = input.nextLine();
            System.out.print("Enter Product Price: ");
            float productPrice = input.nextFloat();

            String command = "INSERT INTO product (product_name,product_price) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(command);
            statement.setString(1,productName);
            statement.setFloat(2,productPrice);
            int executedRows = statement.executeUpdate();
            System.out.printf("Affected Rows = %d\n",executedRows);
            connection.close();
        }
        catch (SQLException e) {
            System.out.println("Error Occurred");
            e.printStackTrace();
        }

    }

    public void deleteProduct(){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("---------------Available Data----------");
            select();
            while (true) {
                System.out.println("Delete By:");
                System.out.println("1. Id");
                System.out.println("2. Name");
                System.out.print("Action: ");
                int result = input.nextByte();
                if (result == 1) {
                    String command = "DELETE FROM product WHERE product_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Product Id: ");
                    statement.setInt(1, input.nextInt());
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n", executedRows);
                    connection.close();
                    break;
                }
                else if (result == 2) {
                    String command = "DELETE FROM product WHERE product_name = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Product Name: ");
                    input.nextLine();
                    statement.setString(1, input.nextLine().trim());
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n", executedRows);
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

    public void updateProduct(){
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("---------------Available Data----------");
            select();
            System.out.print("Enter Product ID: ");
            int productId = input.nextInt();
            while(true){
                System.out.println("---------------------");
                System.out.println("1. Update Product Name: ");
                System.out.println("2. Update Price: ");
                System.out.println("3. Update Both: ");
                System.out.println("Action: ");
                result = input.nextByte();
                if(result == 1){
                    String command = "UPDATE product SET product_name = ? WHERE product_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Enter product Name: ");
                    input.nextLine();
                    statement.setString(1,input.nextLine());
                    statement.setInt(2,productId);
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n",executedRows);
                    connection.close();
                    break;
                }
                else if(result == 2){
                    String command = "UPDATE product SET product_price = ? WHERE product_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Enter product Price: ");
                    input.nextLine();
                    statement.setFloat(1,input.nextFloat());
                    statement.setInt(2,productId);
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n",executedRows);
                    connection.close();
                    break;
                }
                else if(result == 3){
                    String command = "UPDATE product SET product_name = ?,product_price = ? WHERE product_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    System.out.print("Enter product Name: ");
                    input.nextLine();
                    statement.setString(1,input.nextLine());
                    System.out.print("Enter product Price: ");
                    statement.setFloat(2,input.nextFloat());
                    statement.setInt(3,productId);
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n",executedRows);
                    connection.close();
                    break;
                }
            }

        }
        catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    public void select(){
        String sql = "SELECT * FROM product";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()){
                int id = result.getInt("product_id");
                String name = result.getString("product_name");
                float price = result.getFloat("product_price");
                System.out.printf("%d %s %f\n",id,name,price);
            }
            connection.close();

        }
        catch (SQLException e){
            System.out.println("Error");
            e.printStackTrace();
        }

    }
}
