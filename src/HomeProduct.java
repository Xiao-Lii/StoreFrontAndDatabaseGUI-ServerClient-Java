import java.time.LocalDate;

public class HomeProduct extends Product {
    private String intendedLoc;

    public HomeProduct(String productID, String productName, String brandName, String productDesc,
                       LocalDate dateOfIncorp, String intendedLoc) {
        super(productID, productName, brandName, productDesc, dateOfIncorp);
        this.intendedLoc = intendedLoc;
    }

    public String getIntendedLoc() {
        return intendedLoc;
    }

    public void setIntendedLoc(String intendedLoc) {
        this.intendedLoc = intendedLoc;
    }
}