package com.epam.coffeecorner;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.ProductType;

import java.util.Scanner;

public class CoffeeCorner {

    public static void main(String[] args) {
        run();
    }

    protected static void run() {
        OrderService orderService = new OrderService();

        printInfo();
        boolean continueOrder;

        do {
            int choice = getMainChoice();

            Product chosenProduct = OrderService.MENU.get(choice);
            orderService.addProductToOrder(chosenProduct);

            if (ProductType.COFFEE.equals(chosenProduct.getType())) {
                Product extraProduct = handleCoffeeOrder();
                orderService.addProductToOrder(extraProduct);
            }
            System.out.println("Do you want something else - Y for yes N for no");
            Scanner continueOrderChoice = new Scanner(System.in);
            String continueOrderText = continueOrderChoice.next();
            continueOrder = "Y".equals(continueOrderText);
        } while (continueOrder);

        orderService.printReceipt();
    }

    private static Product handleCoffeeOrder() {
        System.out.println("You can add extras to coffee - pick a number:");
        OrderService.EXTRAS.forEach((id, product) -> System.out.format("%1d. %s --- %2f CHF \n", id, product.getName(),
                product.getPrice()));
        Scanner extrasChoiceScan = new Scanner(System.in);
        try {
            int extraChoice = Integer.parseInt(extrasChoiceScan.next());
            return OrderService.EXTRAS.get(extraChoice);
        } catch (NumberFormatException nfe) {
            System.err.println("You can choose only numbers");
        }
        return null;
    }

    private static int getMainChoice() {
        printMenu();
        Scanner scan = new Scanner(System.in);
        try {
            return Integer.parseInt(scan.next());
        } catch (NumberFormatException nfe) {
            System.err.println("You can choose only numbers");
        }
        return -1;
    }

    private static void printMenu() {
        OrderService.MENU.forEach((id, product) -> System.out.format("%1d. %s --- %2f CHF \n", id, product.getName(),
                product.getPrice()));
        System.out.println("Chose position in menu:");
    }

    private static void printInfo() {
        System.out.println("Welcome to Coffee Corner! Use numbers to make order");
    }

}
