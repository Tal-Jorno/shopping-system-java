package TalJorno_MayShabat;

public class Customer implements Comparable<Customer>, UserData {
    private final String username;
    private final String password;
    private final Address address;
    private final CartManager cartManager;

    public Customer(String username, String password, Address address) {
        this.username = username;
        this.password = password;
        this.address = address;
        this.cartManager = new CartManager();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public CartManager getCartManager() {
        return cartManager;
    }

    public Cart getCart() {
        return cartManager.getCurrentCart();
    }

    public void addToCart(Product product) {
        cartManager.addToCurrentCart(product);
    }

    public void checkout() {
        cartManager.checkOut();
    }

    public Address getAddress() {
        return address;
    }

    public String getOrderHistory() {
        if (cartManager.getOrderHistoryCount() == 0) {
            return null;
        }
        StringBuilder orderHistoryString = new StringBuilder();
        orderHistoryString.append("Order History -\n");

        for (int i = 0; i < cartManager.getOrderHistoryCount(); i++) {
            orderHistoryString.append(i + 1).append(")");
            orderHistoryString.append(cartManager.getOrderHistory()[i].toString());
            orderHistoryString.append("\n");
        }
        return orderHistoryString.toString();
    }

    @Override
    public int compareTo(Customer other) {
        return this.username.compareTo(other.username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Customer:").append(username);
        sb.append("\nPassword: ").append(password);
        sb.append("\n\nAddress-").append(address.toString());
        sb.append(cartManager);
        return sb.toString();
    }

}
