package application;

import Product.Category;
import Product.Product;
import Users.StoreSystem;
import Users.User;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import server.*;
import Users.Customer;
import Users.Order;
import javafx.scene.control.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.EventListener;


public class Controller{
    // -------------- ADMIN APPLICATION --------------

    // CREATE A USER
    public Button btnCreateUser;
    public TextField txtEmailAddress;
    public TextField txtPassword;
    public TextField txtUsername;

    // CATEGORY MANAGEMENT
    public TextField addCatIdTextField;
    public TextField addCatNameTextField;
    public TextField addCatDescTextField;
    public Button addCatButton;
    public TextField removeCatIdTextField;
    public Button removeCatButton;

    // PRODUCT MANAGEMENT
    public TextField txtProductID;
    public TextField txtProductName;
    public TextField txtBrandName;
    public TextField txtProductDesc;
    public TextField txtWarranty;
    public TextField txtIMEI;
    public TextField txtRAM;
    public TextField txtOS;
    public TextField txtHardDrive;
    public TextField txtAuthorName;
    public TextField txtNumPages;
    public TextField txtBookEdition;
    public DatePicker calDateIncorp;
    public DatePicker calPubDate;
    public TextField txtSerialNum;
    public TextField txtIntendedLoc;
    public ComboBox<Category> boxProdType;
    public Button btnAddElectronic;
    public Button btnAddCellphone;
    public Button btnAddComputer;
    public Button btnAddBook;
    public Button btnAddHome;
    public EventListener eventListener;

    // LIST ALL PRODUCTS TAB
    public ListView listOfProducts;
    public Tab tabProductList;

    //LIST ALL CATEGORIES TAB
    public ListView listOfCategories;
    public Tab tabCategoryList;

    // FINALIZED ORDER REPORT
    public TextField Orderlist;
    public Button checkOut;
    private Order order = new Order();
    private  Customer c = new Customer();

    Client client;

    // LOGIN WINDOW
    public Button loginButton;
    public TextField usernameTextField;
    public TextField passwordTextField;
    public Label loginLabel;
    public Label loginConfirmLabel;

    private StoreSystem store = new StoreSystem();

    public Controller(){
        this.boxProdType = new ComboBox<>();
        this.listOfCategories = new ListView<>();
        this.listOfProducts = new ListView<>();
        client = new Client();
        client.connect();
    }

    public void initialize() {
        boxProdType.setItems(FXCollections.observableArrayList(store.getListOfCategories()));
        listOfProducts.setItems(FXCollections.observableArrayList(store.getCatalog()));
        listOfCategories.setItems(FXCollections.observableArrayList(store.getListOfCategories()));

        // FOR ADMIN APP - ADD PRODUCT TAB - PRODUCT DEFAULT CATEGORY SELECTION OPTIONS
        boxProdType.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) ->
                // BELOW IS THE CODE WE EXECUTE WHEN OUR DEFAULT PRODUCT CATEGORY IS SELECTED
                System.out.println(newValue)
        );

        // STARTING TESTING FUNCTION - LISTENER FOR VIEWING ITEMS IN A CUSTOMER'S ORDER LIST

        // WE WANT THE FOLLOWING TO HAPPEN WHEN
        // 1. WHEN WE SELECT A USER(OR IF WE ARE THE USER), WE WANT TO SEE THEIR/OUR PERSONAL LIST OF ORDERS.
        // 2. WHEN WE CLICK ON AN ORDER IN A LIST, IN ANOTHER LIST BESIDE IT, WE WANT TO VIEW ALL ITEMS IN THAT ORDER
        //    WE WANT THESE BOTH THESE LISTS TO BE ON THE SAME TAB(ACCOUNT OVERVIEW) IN THE CATALOG APP
        // 3. DUPLICATE ITEMS IN AN ORDER SHOULD BE DISPLAYED AS x2, x3, ETC. INSTEAD OF SHOWING UP TWICE IN THE LIST

        // NOTE: THIS IS THE ORDER ARRAY LIST UNDER THE CUSTOMER CLASS THAT WE NEED TO ACCESS
        // ArrayList<Order> listOfCustOrders;

        /*
        this.listOfCategories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Category>() {

            @Override
            public void changed(ObservableValue<? extends Category> observable, oldValue, newValue) {
                System.out.println("ListView selection changed from oldValue = " + oldValue + " to newValue = " + newValue);

                // EDIT LINE BELOW
                enrolledStudentList.setItems(FXCollections.observableArrayList(newValue.getEnrolledStudents()));
                newValue.getEnrolledStudents();


                public void listProductsByOrderUpdate(Event event) {
                    if (this.tabListStudents.isSelected()){
                        this.listStudents.setItems(FXCollections.observableArrayList(university.getStudents()));
                    }
                }
            }
        }); // END TESTING FUNCTION - LISTENER FOR VIEWING ITEMS IN A CUSTOMER'S ORDER LIST
         */
    }

    public void login(javafx.event.ActionEvent actionEvent) throws Exception {
        for(int i = 0; i < store.getListOfUsers().size(); i ++) {
            if ((usernameTextField.getText().equals(store.getListOfUsers().get(i).getDisplayName()) |
                    usernameTextField.getText().equals(store.getListOfUsers().get(i).getEmail())) &&
                    passwordTextField.getText().equals(store.getListOfUsers().get(i).getPassword())) {
                loginConfirmLabel.setText("Login Successful.");
                loginConfirmLabel.setTextFill(Color.GREEN);

                if(store.getListOfUsers().get(i).getClass().getSimpleName().equals("Admin")) {
                    Parent root = FXMLLoader.load(getClass().getResource("Application.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Store System Admin");
                    primaryStage.setScene(new Scene(root, 1000, 600));
                    primaryStage.show();
                    return;
                }
                else if(store.getListOfUsers().get(i).getClass().getSimpleName().equals("Customer")) {
                    Parent root = FXMLLoader.load(getClass().getResource("CatalogApp.fxml"));
                    Stage primaryStage = new Stage();
                    primaryStage.setTitle("Store System Catalog");
                    primaryStage.setScene(new Scene(root, 1000, 600));
                    primaryStage.show();
                    return;
                }
            }
        }
        loginConfirmLabel.setText("Username or password incorrect.");
        loginConfirmLabel.setTextFill(Color.RED);
    }

    public void createUser(javafx.event.ActionEvent actionEvent) {
        try {
            store.createUser(txtEmailAddress.getText(), txtPassword.getText(), txtUsername.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Account created successfully.");
            alert.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding user: Email or display name already exists.");
            error.show();
        }
    }

    public void addCategory() {
        try {
            store.addCategory(addCatIdTextField.getText(), addCatNameTextField.getText(), addCatDescTextField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Category created successfully.");
            alert.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding category: Category already exists.");
            error.show();
        }
    }

    public void removeCategory() {
        try {
            store.removeCategory(removeCatIdTextField.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Category removed successfully.");
            alert.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error removing category: Category does not exist.");
            error.show();
        }
    }

    public void addElectronic(ActionEvent actionEvent) {
        try {
            store.addElectronic(txtProductID.getText(), txtProductName.getText(),
                    txtBrandName.getText(), txtProductDesc.getText(), calDateIncorp.getValue(),
                    txtSerialNum.getText(), txtWarranty.getText());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "Product has been added successfully.");
            success.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding product. Please try again.");
            error.show();
        }
    }

    public void addCellphone(ActionEvent actionEvent) {
        try {
        store.addCellphone(txtProductID.getText(), txtProductName.getText(),
                txtBrandName.getText(), txtProductDesc.getText(), calDateIncorp.getValue(),
                txtSerialNum.getText(), txtWarranty.getText(), txtIMEI.getText(), txtOS.getText());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "Product has been added successfully.");
            success.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding product. Please try again.");
            error.show();
        }
    }

    public void addComputer(ActionEvent actionEvent) {
        try {
        store.addComputer(txtProductID.getText(), txtProductName.getText(),
                txtBrandName.getText(), txtProductDesc.getText(), calDateIncorp.getValue(),
                txtSerialNum.getText(), txtWarranty.getText(), txtRAM.getText(), txtHardDrive.getText());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "Product has been added successfully.");
            success.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding product. Please try again.");
            error.show();
        }
    }

    public void addBook(ActionEvent actionEvent) {
        try {
        store.addBook(txtProductID.getText(), txtProductName.getText(),
                txtBrandName.getText(), txtProductDesc.getText(), calDateIncorp.getValue(),
                txtAuthorName.getText(), calPubDate.getValue(), Integer.parseInt(txtNumPages.getText()),
                Integer.parseInt(txtBookEdition.getText()));
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "Product has been added successfully.");
            success.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding product. Please try again.");
            error.show();
        }
    }

    public void addHome(ActionEvent actionEvent) {
        try {
        store.addHome(txtProductID.getText(), txtProductName.getText(),
                txtBrandName.getText(), txtProductDesc.getText(), calDateIncorp.getValue(),
                txtIntendedLoc.getText());
            Alert success = new Alert(Alert.AlertType.CONFIRMATION, "Product has been added successfully.");
            success.show();
        }
        catch (IllegalArgumentException iae){
            Alert error = new Alert(Alert.AlertType.ERROR, "Error adding product. Please try again.");
            error.show();
        }
    }


    public void custOrderList(ActionEvent actionEvent) {
        store.countDuplicateItems(this.txtUsername.getText());
    }

    public void finalizeOrder(ActionEvent actionEvent) {
        store.finalizeOrder(this.txtUsername.getText(), order.getOrderNum());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Order Finalized!");
        alert.show();
    }

    public void listProducts(Event event){
        if (this.tabProductList.isSelected()){
            this.listOfProducts.setItems(FXCollections.observableArrayList(store.getCatalog()));
        }
    }

    public void listCategories(Event event){
        if (this.tabCategoryList.isSelected()){
            this.listOfCategories.setItems(FXCollections.observableArrayList(store.getListOfCategories()));
        }
    }

    public void displayCatergories(ActionEvent actionEvent){

    }
}
