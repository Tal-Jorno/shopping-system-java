package TalJorno_MayShabat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Cart {
    private static final int INIT_PRODUCTS_ARRAY_SIZE = 10;
    protected Date date;
    private final SimpleDateFormat sdf = new SimpleDateFormat(" dd/MM/yyyy HH:mm:ss");
    private int productCount;
    protected double totalPrice;

    private Product[] products = new Product[INIT_PRODUCTS_ARRAY_SIZE];

    public Cart() {
        productCount = 0;
        totalPrice = 0;
    }
    public Cart(Cart original) {
        this.productCount = original.productCount;
        this.totalPrice = original.totalPrice;
        this.products = new Product[original.products.length];
        for (int i = 0; i < original.productCount; i++) {
            this.products[i] = copyProduct(original.products[i]);
        }
    }

    private Product copyProduct(Product product) {
        if (product instanceof SpecialProduct) {
            return new SpecialProduct((SpecialProduct) product);
        } else if (product instanceof RegularProduct) {
            return new RegularProduct((RegularProduct) product);
        }
        return null;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setCheckoutDate() {
        date = new Date();
    }

    protected void resizeProductArray(int newSize) {
        Product[] newProducts = new Product[newSize];
        System.arraycopy(products, 0, newProducts, 0, products.length);
        products = newProducts;
    }

    public void addProduct(Product product) {
        if (productCount == products.length) {
            resizeProductArray(products.length * 2);
        }
        products[productCount++] = product;
        totalPrice += product.getTotalPrice();
    }

    public boolean isEmpty() {
        return productCount == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (productCount == 0) {
            sb.append("empty\n");
            return sb.toString();
        }
        if (date != null) {
            sb.append("Date: ").append(sdf.format(date)).append("\n");
        }
        sb.append("productCount: ").append(productCount).append("\n");
        sb.append("products: \n");
        for (Product p : products) {
            if (p != null) {
                sb.append(p).append("\n");
            }
        }
        sb.append("Total Price: ").append(totalPrice).append("\n").append("______________\n");
        return sb.toString();
    }
}
