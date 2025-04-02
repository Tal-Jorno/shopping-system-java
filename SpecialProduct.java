package TalJorno_MayShabat;
public class SpecialProduct extends Product {
    private double packagingCost;

    public SpecialProduct(String name, double price, Category category, double packagingCost) {
        super(name, price, category);
        this.packagingCost = packagingCost;
    }

    public SpecialProduct(SpecialProduct original) {
        super(original.getName(), original.getPrice(), original.getCategory());
        this.packagingCost = original.packagingCost;
    }

    public double getPackagingCost() {
        return packagingCost;
    }

    public void setPackagingCost(double packagingCost) {
        this.packagingCost = packagingCost;
    }

    public double getTotalPrice() {
        return getPrice() + packagingCost;
    }

    @Override
    public String toString() {
        return "SpecialProduct:\n" +
                "Name: " + getName() + "\n" +
                "Category: " + getCategory() + "\n" +
                "Serial number:" +getSerialNumber() + "\n"+
                "Price: " + getPrice() + "\n" +
                "Packaging Cost: " + getPackagingCost() + "\n" +
                "Final Price (Packaging + Product) : " + getTotalPrice() + "\n";
    }
}
