//Address.java

package TalJorno_MayShabat;

public class Address {
    private String street;
    private String city;
    private String state;
    private String houseNumber;

    public Address(String street, String city, String state, String houseNumber) {
        setStreet(street);
        setCity(city);
        setState(state);
        setHouseNumber(houseNumber);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String toString() {
        return "\nstate: " + state + "\ncity: " + city + "\nstreet: " +street + "\nhouse number: " + houseNumber;
    }
}
