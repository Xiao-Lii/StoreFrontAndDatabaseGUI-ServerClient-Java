package Product;

public class Category {
    private String catID;
    private String catName;
    private String catDesc;

    public Category(String catID, String catName, String catDesc) {
        this.catID = catID;
        this.catName = catName;
        this.catDesc = catDesc;
    }

    /*
    public Category() {
        this.catID = "Default00";
        this.catName = "Default";
        this.catDesc = "The default category for viewing all the entire contents of the catalog.";
    }
     */

    // THIS IS IMPORTANT WHEN DISPLAYING TO LISTVIEW TABLES
    @Override
    public String toString(){
        return String.format("%-15s\t - \t%-15s\t - \t%s", this.getCatID(), this.getCatName(), this.getCatDesc());
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }
}
