package TalJorno_MayShabat;

public class CartManager {
    public static final int INIT_HISTORY_SIZE = 10;
    protected Cart currentCart = new Cart();
    private Cart[] orderHistory = new Cart[INIT_HISTORY_SIZE];
    private int orderHistoryCount = 0;

    public Cart getCurrentCart() {
        return currentCart;
    }

    public Cart[] getOrderHistory() {
        return orderHistory;
    }

    public int getOrderHistoryCount() {
        return orderHistoryCount;
    }

    private void resizeOrderHistoryArray() {
        Cart[] newOrderHistory = new Cart[orderHistory.length * 2];
        if (orderHistoryCount >= 0) System.arraycopy(orderHistory, 0, newOrderHistory, 0, orderHistoryCount);
        orderHistory = newOrderHistory;
    }

    public void addToCurrentCart(Product product) {
        currentCart.addProduct(product);
    }

    public void checkOut() {
        if (orderHistoryCount == orderHistory.length) {
            resizeOrderHistoryArray();
        }
        currentCart.setCheckoutDate();
        orderHistory[orderHistoryCount++] = currentCart;
        deleteCart();
    }

    public void deleteCart() {
        currentCart = new Cart();
    }

    public void checkIn(int orderNumber) {
        Cart originalCart = orderHistory[orderNumber];
        currentCart = new Cart(originalCart);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\ncurrent cart-\n");
        sb.append(currentCart);
        sb.append("\norder history-\n");
        for (int cartIndex = 0; cartIndex < orderHistoryCount; cartIndex++) {
            sb.append(cartIndex + 1).append(": ");
            if (orderHistory[cartIndex] != null) {
                sb.append(orderHistory[cartIndex].toString()).append("\n");
            }
        }
        if (orderHistoryCount == 0){
            sb.append("No order history\n");
        }
        sb.append("_______________________________________");
        return sb.toString();
    }
}
