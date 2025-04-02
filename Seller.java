package TalJorno_MayShabat;

import java.util.Arrays;

public class Seller implements Comparable<Seller>, UserData {
    private final String username;
    private final String password;
    private Product[] products;
    private int productCount;

    private final int INITIAL_PRODUCT_SIZE = 10;

    public Seller(String username, String password) {
        this.username = username;
        this.password = password;
        this.products = new Product[INITIAL_PRODUCT_SIZE];
        this.productCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addProduct(Product product) {
        if (productCount == products.length) {
            resizeProductsArray();
        }
        products[productCount++] = product;
    }

    public Product[] getProducts() {
        return Arrays.copyOf(products, products.length);
    }

    public int getProductCount() {
        return productCount;
    }

    private void resizeProductsArray() {
        Product[] newProducts = new Product[products.length * 2];
        if (productCount >= 0) System.arraycopy(products, 0, newProducts, 0, productCount);
        products = newProducts;
    }

    public Product findProductByName(String name) {
        for (int i = 0; i < productCount; i++) {
            if (products[i].getName().equals(name)) {
                return products[i];
            }
        }
        return null;
    }
    @Override
    public int compareTo(Seller other) {
        return Integer.compare(other.productCount, this.productCount); // Sorting in descending order
    }

    public boolean equals(String o) {
        return username.equals(o);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Seller: ").append(username);
        sb.append("\nPassword: ").append(password);
        if (productCount == 0) {
            sb.append("\nProducts: empty");
        }
        else {
            sb.append("\nProducts Count: ").append(productCount).append("\n");
        }
        for (int i = 0; i < productCount; i++) {
            sb.append("\n").append(products[i].toString()).append("");
        }
        sb.append("\n_________________________________________________\n");
        return sb.toString();
    }
}
