package product;


import java.sql.*;

import java.util.Scanner;


public class order {
    static Scanner input = new Scanner(System.in);
    public String url = "jdbc:postgresql://localhost:5432/ProductManagement";
    public String username = "postgres";
    public String password = "Nevermore123";


    public void orderList() {
        try {
            int productId = 0;
            int customerId = 0;
            float price = 0;
            String productName = "";
            String customerName = "";
            int quantity = 0;
            long millis=System.currentTimeMillis();
            Date date=new Date(millis);
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Enter Product Name: ");
            productName = input.nextLine();
            System.out.println("Enter Customer Name: ");
            customerName = input.nextLine();
            System.out.println(productName);
            System.out.println(customerName);
            System.out.print("Enter Quantity: ");
            quantity = input.nextInt();
            input.nextLine();
            String command = "INSERT INTO orders (product_id,customer_id,quantity,total_price,date) VALUES (?,?,?,?,?)";
            String commandProduct = "SELECT * FROM product WHERE product_name = '"+productName+"'";
            String commandCustomer = "SELECT * FROM customer WHERE customer_name = '"+customerName+"'";
            Statement statementProduct = connection.createStatement();
            ResultSet resultProduct = statementProduct.executeQuery(commandProduct);
            while(resultProduct.next()) {
                productId = resultProduct.getInt("product_id");
                price = resultProduct.getFloat("product_price");
            }

            Statement statementCustomer = connection.createStatement();
            ResultSet resultCustomer = statementCustomer.executeQuery(commandCustomer);
            while(resultCustomer.next()) {
                customerId = resultCustomer.getInt("customer_id");
            }

            PreparedStatement statement = connection.prepareStatement(command);
            statement.setInt(1, productId);
            statement.setInt(2, customerId);
            statement.setInt(3, quantity);
            statement.setFloat(4, price);
            statement.setDate(5,date);
            float total_price = (float) quantity * price;
            statement.setFloat(4, total_price);
            int rows = statement.executeUpdate();
            System.out.printf("Affected Rows: %d\n", rows);

        } catch (SQLException e) {
            System.out.println("Error");
            e.printStackTrace();
        }

    }

    public void deleteOrder(int id) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
                    String command = "DELETE FROM orders WHERE customer_id = ?";
                    PreparedStatement statement = connection.prepareStatement(command);
                    statement.setInt(1,id);
                    int executedRows = statement.executeUpdate();
                    System.out.printf("Affected Rows: %d\n", executedRows);
                    connection.close();

        }
        catch(SQLException e){
            System.out.println("error");
            e.printStackTrace();
        }
    }
}

