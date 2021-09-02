package product;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class report {
    static Scanner input = new Scanner(System.in);
    public String url = "jdbc:postgresql://localhost:5432/ProductManagement";
    public String username = "postgres";
    public String password = "Nevermore123";


    public void reportPurchase(){
        float price=0;
        int customerId=0;
        System.out.println("Enter Customer Name: ");
        String name = input.nextLine();
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            String commandCustomerId = "Select * from customer where customer_name = '"+name+"'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultCustomer = statementCustomer.executeQuery(commandCustomerId);
            while(resultCustomer.next()) {
                customerId = resultCustomer.getInt("customer_id");
            }

            String command ="Select * from orders where customer_id = '"+customerId+"'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            while(result.next()){
                price += result.getFloat("total_price");
            }
            System.out.print("Total purchase: ");
            System.out.println(price);
        }
        catch (SQLException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public void reportSales(){
        int totalSales=0;
        int productId=0;
        System.out.println("Enter Product Name: ");
        String name = input.nextLine();
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            String commandProductId = "Select * from product where product_name = '"+name+"'";
            Statement statementCustomer = connection.createStatement();
            ResultSet resultCustomer = statementCustomer.executeQuery(commandProductId);
            while(resultCustomer.next()) {
                productId = resultCustomer.getInt("product_id");
            }

            String command ="Select * from orders where product_id = '"+productId+"'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            while(result.next()){
                totalSales += result.getInt("quantity");
            }
            System.out.print("Total Sales: ");
            System.out.println(totalSales);
        }
        catch (SQLException e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public void reportTimeFrame(){
        int totalSales = 0;
        int Purchase=0;
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");
        System.out.print("From: ");

        try {
            Connection connection = DriverManager.getConnection(url,username,password);
            String fromDate = input.nextLine();
            System.out.println("To:");
            String toDate = input.next();
            String command = "SELECT * from orders WHERE datetime between '"+fromDate+"' AND "+"'"+toDate+"'";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(command);
            while(result.next()){
                totalSales += result.getInt("quantity");
                Purchase += result.getFloat("total_price");
            }
            System.out.println("Total Quantity: ");
            System.out.println(totalSales);
            System.out.println("Total Purchase: ");
            System.out.println(Purchase);
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}