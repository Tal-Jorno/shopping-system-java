// Product.java
package TalJorno_MayShabat;

public abstract class Product {
    private static int serialCounter = 1000;
    private final int serialNumber;
    private final String name;
    private double price;
    private final Category category;

    public enum Category {
        KIDS(0), ELECTRIC(1), OFFICE(2), CLOTHING(3);

        private final int value;

        Category(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Product(String name, double price, Category category) {
        this.serialNumber = ++serialCounter;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Product() {
        this(null, 0, null);
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public static String getCategoryName() {
        StringBuilder categoryNames = new StringBuilder();
        for (Category categoryType : Category.values()) { // כאן משתמשים במשתנה מקומי
            categoryNames.append(categoryType.getValue() + 1).append(") ").append(categoryType.name()).append("\n");
        }
        return categoryNames.toString();
    }

    public boolean setPrice(int price) {
        if (price <= 0) {
            return false;
        }
        this.price = price;
        return true;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public abstract double getTotalPrice();
}


