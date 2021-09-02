package product;

import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        boolean condition = true;
        product products = new product();
        customer customers = new customer();
        order orders = new order();
        report reports = new report();
        while(condition) {
            System.out.println("Enter Task:");
            System.out.println("1. Add Customer");
            System.out.println("2. Add Product");
            System.out.println("3. Add order");
            System.out.println("4. Report");
            System.out.println("5. quit");
            System.out.print("Input : ");
            byte result = input.nextByte();
            input.nextLine();
            switch (result)
            {
                case 1:
                    customers.display();
                    break;
                case 2:
                    products.display();
                    break;
                case 3:
                    orders.orderList();
                    break;
                case 4:
                    System.out.println("1.Customer Total Purchase");
                    System.out.println("2.Product Total Sales");
                    System.out.println("3.Sales Within TimeFrame");
                    int answer = input.nextInt();
                    input.nextLine();
                    if(answer == 1)
                        reports.reportPurchase();
                    else if(answer == 2)
                        reports.reportSales();
                    else if(answer == 3)
                        reports.reportTimeFrame();
                    else
                        System.out.println("You Typed Wrong");
                    break;
                case 5:
                    System.out.println("Thank You");
                    condition=false;
                    break;
                default:
                    System.out.println("Incorrect Input");
            }
        }
    }
}
