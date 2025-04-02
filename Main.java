//Name: Tal Jorno, ID: 213430739
//lecturer: Keren kalif

//Student 2:
//Name: May Shabat,ID: 211392634
//lecturer: Keren kalif

package TalJorno_MayShabat;

import java.util.InputMismatchException;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static Scanner scn = new Scanner(System.in);

    public static void menu(Manager manager) {
        String choice;
        boolean keepRunning = true;

        do {
            System.out.println("A menu with options to choose from:");
            System.out.println("0 - Exit");
            System.out.println("1 - Add Seller");
            System.out.println("2 - Add Customer");
            System.out.println("3 - Add Product to Seller");
            System.out.println("4 - Add Product to Customer");
            System.out.println("5 - Checkout for Customer");
            System.out.println("6 - Display All Customers");
            System.out.println("7 - Display All Sellers");
            System.out.println("8 - Display all products by certain category");
            System.out.println("9 - Create new cart from order history");
            System.out.println("Please enter your choice:");
            choice = scn.nextLine();
            try {
                switch (choice) {
                    case "0":
                        System.out.println("Goodbye! See you later 😉...");
                        keepRunning = false;
                        scn.close();
                        break;
                    case "1":
                        addSeller(manager);
                        break;
                    case "2":
                        addCustomer(manager);
                        break;
                    case "3":
                        addProductToSeller(manager);
                        break;
                    case "4":
                        addProductToCustomer(manager);
                        break;
                    case "5":
                        customerNameForCheckout(manager);
                        break;
                    case "6":
                        manager.displayAllCustomers();
                        break;
                    case "7":
                        manager.displayAllSellers();
                        break;
                    case "8":
                        categoryChosen(manager);
                        break;
                    case "9":
                        createNewCartFromHistory(manager);
                        break;
                    default:
                        System.out.println("Invalid input, try again. ☹️");
                        break;
                }
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println("\n");
        } while (keepRunning);
        scn.close();
    }

    public static void createNewCartFromHistory(Manager manager) throws Exception {
        try {
            String customerName = UserInputChecks.checkCustomerHasOrderHistory(manager);
            Customer customer = manager.findCustomerByName(customerName);

            if (customer.getCart().getProductCount() > 0 && !promptCartReplacement(customer)) {
                System.out.println("Ok, Goodbye!");
                return;
            }

            displayOrderHistory(customer);

            int choice = UserInputChecks.promptOrderHistoryChoice(customer) - 1;
            if (manager.replaceToOrderHistoryCart(customer, choice)) {
                System.out.println("Successfully replaced cart with order history.");
            }
        }
        catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    private static boolean promptCartReplacement(Customer customer) {
        System.out.println("Customer " + customer.getUsername() + " already has a cart.\nWould you like to replace this cart with another from history? (y/n)");
        String response = scn.nextLine().trim().toUpperCase();
        return response.equals("Y");
    }

    private static void displayOrderHistory(Customer customer) {
        System.out.println(customer.getOrderHistory());
        System.out.println("What shopping cart number would you like to choose? (Enter number)");
        int orderHistoryCount = customer.getCartManager().getOrderHistoryCount();

        for (int i = 1; i <= orderHistoryCount; i++) {
            System.out.println("Press - " + i + (i < orderHistoryCount ? "\nor" : ""));
        }
    }

    public static void addSeller(Manager manager) throws Exception{
        String username, password;
        try {
            username = UserInputChecks.promptUsernameForInput("Seller username", manager);
            password = UserInputChecks.promptUserForInput("Password");
            manager.addSeller(username, password);
            System.out.println(username + " has been added as seller.");
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public static void addCustomer(Manager manager) {
        String username ,password;
        try {
            username = UserInputChecks.promptUsernameForInput("Customer username", manager);
            password = UserInputChecks.promptUserForInput("Password");
            Address address = scanningAddressDetails();
            manager.addCustomer(username, password, address);
            System.out.println(username + " has been added as customer.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static Address scanningAddressDetails() throws Exception {
        try {
            String state = UserInputChecks.promptUserForInput("State");
            String city = UserInputChecks.promptUserForInput("City");
            String street = UserInputChecks.promptUserForInput("Street");
            String houseNumber = UserInputChecks.promptUserForInput("House Number");
            return new Address(street, city, state, houseNumber);
        }
        catch (ProgramExceptions.EmptyInputException e) {
            throw new Exception(e.getMessage());
        }
    }


    public static void addProductToSeller(Manager manager) {
        String sellerName;
        try {
            sellerName = UserInputChecks.bringSellerNames(manager);
            Product product = gatherProductDetails(manager, sellerName);

            if (manager.addProductToSeller(sellerName, product)) {
                System.out.println(product.getName() + " has been added to the seller.");
            } else {
                System.out.println(product.getName() + " has already been added.");
            }
        }
        catch (ProgramExceptions.notInitializedStructure | ProgramExceptions.NotFoundObject | IllegalArgumentException | InputMismatchException |
                 ProgramExceptions.EmptyInputException | ProgramExceptions.DuplicateNames | ProgramExceptions.NegativeNumber e) {
            System.out.println(e.getMessage());
        }
    }

    public static Product gatherProductDetails(Manager manager, String sellerName) throws ProgramExceptions.notInitializedStructure, ProgramExceptions.NotFoundObject, IllegalArgumentException, InputMismatchException, ProgramExceptions.EmptyInputException, ProgramExceptions.DuplicateNames, ProgramExceptions.NegativeNumber {
        String productName, categoryName, response;
        double productPrice, packagingCost = 0;

        categoryName = UserInputChecks.promptCategoryFromUser();
        productName = UserInputChecks.promptProductFromUser(manager, sellerName);
        productPrice = UserInputChecks.promptUserForPositiveDouble("Product price");
        System.out.print("Do you want to use special packing for this product? (y/n) ");
        response = scn.nextLine().trim();
        if (response.equalsIgnoreCase("y")) {
            packagingCost = UserInputChecks.promptUserForPositiveDouble("Packaging cost");
        }

        Product.Category productCategory = Product.Category.valueOf(categoryName);
        if (response.equalsIgnoreCase("y")) {
            return new SpecialProduct(productName, productPrice, productCategory, packagingCost);
        } else {
            return new RegularProduct(productName, productPrice, productCategory);
        }
    }


    public static void addProductToCustomer(Manager manager) {
        String customerName, sellerName;
        Product product;
        try {
            customerName = UserInputChecks.bringCustomerNames(manager);
            sellerName = UserInputChecks.bringSellerNames(manager);
            product = UserInputChecks.bringProductNames(manager, sellerName);
            manager.addProductToCustomer(customerName, product);
            System.out.println(Objects.requireNonNull(product).getName() + " has been added to the customer " + customerName);
        }
        catch (ProgramExceptions.NotFoundObject | ProgramExceptions.notInitializedStructure e){
            System.out.println(e.getMessage());
        }

    }

    public static void customerNameForCheckout(Manager manager) {
        String customerName;
        try {
            customerName = UserInputChecks.bringCustomerNames(manager);
            manager.checkoutCustomer(customerName);
            System.out.println("Successfully checked out customer " + customerName);
        }
        catch (ProgramExceptions.NotFoundObject | ProgramExceptions.notInitializedStructure e){
            System.out.println(e.getMessage());
        }

    }


    public static void categoryChosen(Manager manager) {
        String categoryName = UserInputChecks.promptCategoryFromUser();
        manager.displayProductsByCategory(categoryName);
    }


    public static void main(String[] args) {
        Manager manager = new Manager();
        menu(manager);
    }
}