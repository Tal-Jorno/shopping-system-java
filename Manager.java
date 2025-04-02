package TalJorno_MayShabat;

import java.util.Arrays;

public class Manager {
    private Seller[] sellers;
    private int sellerCount;
    private Customer[] customers;
    private int customerCount;

    private final int INITIAL_SELLER_SIZE = 3;
    private final int INITIAL_CUSTOMER_SIZE = 3;

    public Manager() {
        this.sellers = new Seller[INITIAL_SELLER_SIZE];
        this.sellerCount = 0;
        this.customers = new Customer[INITIAL_CUSTOMER_SIZE];
        this.customerCount = 0;
    }

    public int getSellerLength() {
        return this.sellers.length;
    }

    public int getSellerCount() {
        return sellerCount;
    }

    public int getCustomerLength() {
        return this.customers.length;
    }

    public int getCustomerCount() {
        return this.customerCount;
    }

    public Seller[] getSellers() {
        return Arrays.copyOf(this.sellers, this.sellerCount);
    }

    public Customer[] getCustomers() {
        return Arrays.copyOf(customers, customerCount);
    }

    public Seller getSellerByName(String name) {
        for (int i = 0; i < sellerCount; i++) {
            if (sellers[i].getUsername().equals(name)) {
                return sellers[i];
            }
        }
        return null;
    }


    public void addSeller(String username, String password) {
        if (getSellerCount() == getSellerLength()) {
            sellers = Arrays.copyOf(sellers, getSellerLength() * 2);
        }
        Seller seller = new Seller(username, password);
        sellers[sellerCount++] = seller;
    }

    public void addCustomer(String username, String password, Address address) {
        if (getCustomerCount() == getCustomerLength()) {
            customers = Arrays.copyOf(customers, getCustomerLength() * 2);
        }
        Customer customer = new Customer(username, password, address);
        customers[customerCount++] = customer;
    }

    public boolean addProductToSeller(String sellerUsername, Product product) {
        Seller seller = findSellerByName(sellerUsername);
        if (seller == null) {
            return false;
        }
        seller.addProduct(product);
        return true;
    }

    public void addProductToCustomer(String customerName, Product productName) {
        Customer customer = findCustomerByName(customerName);
        customer.addToCart(productName);
    }

    public void checkoutCustomer(String customerName) throws ProgramExceptions.notInitializedStructure{
        Customer customer = findCustomerByName(customerName);
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            throw new ProgramExceptions.notInitializedStructure("The cart is empty.");
        }
        double totalAmount = cart.totalPrice;
        System.out.println("Total amount to be paid: " + totalAmount);
        customer.checkout();
        System.out.println("Payment processed for " + customer.getUsername());
    }

    public boolean isEmptyCustomers() {
        return this.customerCount == 0;
    }

    public boolean isEmptySellers() {
        return this.sellerCount == 0;
    }

    public Seller findSellerByName(String username) {
        for (int i = 0; i < sellerCount; i++) {
            if (sellers[i].getUsername().equals(username)) {
                return sellers[i];
            }
        }
        return null;
    }

    public Customer findCustomerByName(String username) {
        for (int i = 0; i < customerCount; i++) {
            if (customers[i].getUsername().equals(username)) {
                return customers[i];
            }
        }
        return null;
    }

    private void displayNames(UserData[] ToDisplay, int length){
         for (int i = 0; i < length; i++) {
             System.out.println(ToDisplay[i].getUsername());
         }
    }

    public void displaySellerNames() {
        if (sellerCount == 0) {
            System.out.println("There are no sellers.");
            return;
        }
        System.out.println("The Seller Names are:");
        displayNames(sellers, sellerCount);
    }

    public void displayCustomerNames() {
        if (customerCount == 0) {
            System.out.println("There are no customers.");
            return;
        }
        System.out.println("The customer usernames are:");
        displayNames(getCustomers(), customerCount);
    }

    public void displayProductNames(Seller seller) {
        System.out.println("The products are:");
        Product[] products = seller.getProducts();
        for (int i = 0; i < seller.getProductCount(); i++) {
            System.out.println(products[i].toString());
        }
    }

    public void displayAllCustomers() {
        if (customerCount == 0) {
            System.out.println("There are no customers.");
            return;
        }
        Customer[] sortedCustomers = getCustomers();
        Arrays.sort(sortedCustomers);
        for (Customer c : sortedCustomers) {
            System.out.println(c);
        }
    }

    public void displayAllSellers() {
        if (sellerCount == 0) {
            System.out.println("There are no sellers.");
            return;
        }
        Seller[] sortedSellers = getSellers();
        Arrays.sort(sortedSellers);
        for (Seller s : sortedSellers) {
            System.out.println(s.toString());
        }
    }

    public void displayProductsByCategory(String categoryName) {
        Product.Category category = Product.Category.valueOf(categoryName.toUpperCase());
        boolean foundProducts = false;
        for (int i = 0; i < sellerCount; i++) {
            Seller seller = sellers[i];
            Product[] products = seller.getProducts();
            for (int j = 0; j < seller.getProductCount(); j++) {
                if (products[j].getCategory() == category) {
                    if (!foundProducts) {
                        System.out.println("Products in category " + categoryName + ":");
                        foundProducts = true;
                    }
                    System.out.println("Seller name - " + seller.getUsername() + ":\n" + products[j]);
                }
            }
        }
        if (!foundProducts) {
            System.out.println("No products found in category " + categoryName + ".");
        }
    }

    public boolean replaceToOrderHistoryCart(Customer customer, int orderNumber) {
        customer.getCartManager().deleteCart();
        customer.getCartManager().checkIn(orderNumber);
        return !customer.getCartManager().currentCart.isEmpty();
    }
}
