package TalJorno_MayShabat;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;

public class UserInputChecks {
    private static final int ATTEMPTS = 3;


    public static String promptUserForInput(String field) throws ProgramExceptions.EmptyInputException {
        int attempts = ATTEMPTS;
        String input = null;

        while (attempts > 0) {
            System.out.println("Enter " + field + ":");
            input = Main.scn.nextLine().trim();
            if (input.isEmpty()) {
                attempts--;
                System.out.println("Empty input, try again. Attempts left: " + attempts);
                continue;
            }
            break;
        }
        if (attempts == 0) {
            throw new ProgramExceptions.EmptyInputException("Failed to enter a valid " + field + " after " + ATTEMPTS + " attempts.");
        }
        return input;
    }

    public static String promptUsernameForInput(String field, Manager manager) throws ProgramExceptions.EmptyInputException, ProgramExceptions.DuplicateNames {
        int attempts = ATTEMPTS;
        String input = null;
        boolean typeExc = false;

        while (attempts > 0) {
            System.out.println("Enter " + field + ":");
            input = Main.scn.nextLine().trim();
            if (input.isEmpty()) {
                attempts--;
                System.out.println("Empty input, try again. Attempts left: " + attempts);
                typeExc = true;
                continue;
            }
            if (field.equals("Seller username")) {
                if (manager.findSellerByName(input) != null){
                    attempts--;
                    System.out.println(field+ " already exists, try again. Attempts left: " + attempts);
                    typeExc = false;
                    continue;
                }
            } else {
                if (manager.findCustomerByName(input) != null){
                    attempts--;
                    System.out.println(field +" already exists, try again. Attempts left: " + attempts);
                    typeExc = false;
                    continue;
                }
            }
            break;
        }
        if (attempts == 0) {
            if (typeExc) {
                throw new ProgramExceptions.EmptyInputException("Failed to enter a empty username input.");
            }
            throw new ProgramExceptions.DuplicateNames("Failed to enter a valid existing of " + field + ".");
        }
        return input;
    }

    public static String promptCategoryFromUser() throws IllegalArgumentException {
        String categoryChoice;
        String res = null;
        int attempts = ATTEMPTS;

        while (attempts > 0) {
            System.out.println("The Categories are:\n" + Product.getCategoryName());
            System.out.println("Enter the number of category you chose: ");
            categoryChoice = Main.scn.nextLine().trim();
            switch (categoryChoice) {
                case "1":
                    res = "KIDS";
                    return res;
                case "2":
                    res = "ELECTRIC";
                    return res;
                case "3":
                    res = "OFFICE";
                    return res;
                case "4":
                    res = "CLOTHING";
                    return res;
                default:
                    attempts--;
                    System.out.println("Invalid category choice. Attempts left: " + attempts);
                    break;
            }
            if (attempts == 0) {
                throw new IllegalArgumentException("Failed to enter a valid " + categoryChoice + " category.");
            }
        }
        return res;
    }


    public static String promptProductFromUser(Manager manager, String sellerName) throws ProgramExceptions.EmptyInputException, ProgramExceptions.DuplicateNames {
        int attempts = ATTEMPTS;
        String productName = null;
        Seller s = manager.getSellerByName(sellerName);
        boolean typeExc = false;

        while (attempts > 0) {
            System.out.println("Enter product name:");
            productName = Main.scn.nextLine().trim();
            if (productName.isEmpty()) {
                attempts--;
                System.out.println("Empty input, try again. Attempts left: " + attempts);
                typeExc = true;
                continue;
            }
            if (s.findProductByName(productName) != null) {
                attempts--;
                System.out.println("Existing product with name " + productName + " found. Attempt left: " + attempts);
                typeExc = false;
                continue;
            }
            typeExc = false;
            break;
        }
        if (attempts == 0) {
            if (typeExc){
                throw new ProgramExceptions.EmptyInputException("Failed after "+ ATTEMPTS + " to enter empty input");
            }
            throw new ProgramExceptions.DuplicateNames("Failed after " + ATTEMPTS + " to enter existing product");
        }
        return productName;
    }


    public static String bringSellerNames(Manager manager) throws ProgramExceptions.NotFoundObject,ProgramExceptions.notInitializedStructure {
        if (manager.isEmptySellers()) {
            throw new ProgramExceptions.notInitializedStructure("There are no sellers.");
        }
        manager.displaySellerNames();
        String sellerUsername = null;
        int attempts = ATTEMPTS;
        while (attempts > 0) {
            System.out.println("Please enter the seller's username:");
            sellerUsername = Main.scn.nextLine().trim();
            if (manager.findSellerByName(sellerUsername) != null) {
                break;
            }
            attempts--;
            System.out.println("This seller does not exist. Attempts left: " + attempts);
        }
        if (attempts == 0) {
            throw new ProgramExceptions.NotFoundObject("Failed to enter seller name after " + ATTEMPTS + " attempts.");
        }

        return sellerUsername;
    }


    public static String bringCustomerNames(Manager manager) throws ProgramExceptions.notInitializedStructure, ProgramExceptions.NotFoundObject {
        if (manager.isEmptyCustomers()) {
            throw new ProgramExceptions.notInitializedStructure("There are no customers.");
        }
        manager.displayCustomerNames();
        String customerUsername = null;
        int attempts = ATTEMPTS;
        while (attempts > 0) {
            System.out.println("Please enter the customer's username:");
            customerUsername = Main.scn.nextLine().trim();
            if (manager.findCustomerByName(customerUsername) != null) {
                break;
            }
            attempts--;
            System.out.println("This customer does not exist. Attempts left: " + attempts);
        }
        if (attempts == 0) {
            throw new ProgramExceptions.NotFoundObject("Failed to enter customer name after " + ATTEMPTS + " attempts.");
        }
        return customerUsername;
    }


    public static Product bringProductNames(Manager manager, String sellerName) throws ProgramExceptions.notInitializedStructure,ProgramExceptions.NotFoundObject , NullPointerException {
        Seller seller = manager.findSellerByName(sellerName);
        if (seller.getProductCount() == 0) {
            throw new ProgramExceptions.notInitializedStructure("The seller has no items for sale.");
        }
        boolean typeExc = false;
        manager.displayProductNames(seller);
        String productName;
        Product[] products = seller.getProducts();
        int attempts = ATTEMPTS;

        while (attempts > 0) {
            typeExc = false;
            System.out.println("Enter the name of the product you want to add to the cart:");
            productName = Main.scn.nextLine().trim();
            for (int i = 0; i < seller.getProductCount(); i++) {
                if (products[i].getName().equals(productName)) {
                    return products[i];
                }
            }
            attempts--;
            System.out.println("This product does not exist. Attempts left: " + attempts);

        }
        if (attempts == 0) {
            if(typeExc){
                throw new NullPointerException("Input cannot be null, empty, or whitespace.");
            }
            throw new ProgramExceptions.NotFoundObject("Failed to enter product name after " + ATTEMPTS + " attempts.");
        }

        return null;
    }


    public static double promptUserForPositiveDouble(String field) throws InputMismatchException, ProgramExceptions.NegativeNumber {
        int attempts = ATTEMPTS;
        double input = -1;
        boolean invalidInput = false;

        while (attempts > 0) {
            System.out.println("Enter " + field + ":");
            try {
                input = Double.parseDouble(Main.scn.nextLine().trim());
                if (input > 0) {
                    return input;
                } else {
                    invalidInput = false;
                    attempts--;
                    System.out.println("Price must be positive. Attempts left: " + attempts);
                }
            } catch (NumberFormatException e) {
                invalidInput = true;
                attempts--;
                System.out.println("Price must be a number. Attempts left: " + attempts);
            }
        }
        if (attempts == 0) {
            if (invalidInput) {
                throw new InputMismatchException("Failed after " + ATTEMPTS + " attempts to enter a valid number.");
            }
            throw new ProgramExceptions.NegativeNumber("Failed after " + ATTEMPTS + " attempts to enter a positive number.");
        }
        return input;
    }


    public static String checkCustomerHasOrderHistory(Manager manager) throws ProgramExceptions.notInitializedStructure, ProgramExceptions.NotFoundObject {
        String customerUsername = bringCustomerNames(manager);
        Customer customer = manager.findCustomerByName(customerUsername);
        if (customer.getCartManager().getOrderHistoryCount() == 0){
            throw new ProgramExceptions.notInitializedStructure("Failed to get cart empty order history.");
        }
        return customerUsername;
    }


    public static int promptOrderHistoryChoice(Customer customer) throws ProgramExceptions.NotFoundObject, InputMismatchException {
        int attempts = ATTEMPTS;
        int choice = 0;
        boolean typeExc = false;
        while (attempts > 0) {
            typeExc = false;
            try {
                System.out.println("Enter your order history choice:");
                choice = Integer.parseInt(Main.scn.nextLine().trim());
                if (choice < 1 || choice > customer.getCartManager().getOrderHistoryCount()) {
                    typeExc = true;
                    System.out.println("Invalid order history choice.");
                    continue;
                }
                break;
            } catch (NumberFormatException | NoSuchElementException | IllegalStateException e) {
                attempts--;
                System.out.println("Invalid input. Please enter a valid order history choice number. Attempts left: " + attempts);
            } catch (IllegalArgumentException e) {
                attempts--;
                System.out.println(e.getMessage() + " Attempts left: " + attempts);
            }
        }
        if (attempts == 0) {
            if(typeExc){
                throw new ProgramExceptions.NotFoundObject("Failed to find Order history number" + choice + " after " + ATTEMPTS + " attempts.");
            }
            throw new InputMismatchException("Failed to find order history with invalid input after " + ATTEMPTS + " attempts.");
        }

        return choice;
    }


}
