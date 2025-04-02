package TalJorno_MayShabat;

public class RegularProduct extends Product{
    public RegularProduct(String name, double price, Category category) {
        super(name, price, category);
    }

    public RegularProduct(RegularProduct original) {
        super(original.getName(), original.getPrice(), original.getCategory());
    }


    @Override
    public double getTotalPrice() {
        return getPrice();
    }

    public String toString() {
        return "Regular Product:\n" +
                "Name: " + getName() + "\n" +
                "Category: " + getCategory() + "\n" +
                "Serial number:" +getSerialNumber() + "\n"+
                "Price: " + getPrice() + "\n";
    }
}
