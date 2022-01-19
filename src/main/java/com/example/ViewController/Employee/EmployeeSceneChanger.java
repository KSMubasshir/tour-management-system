package com.example.ViewController.Employee;

import com.example.ViewController.TableClasses.Bank;
import com.example.ViewController.TableClasses.Accommodation;
import com.example.ViewController.TableClasses.Guide;
import com.example.ViewController.TableClasses.PoliceStation;
import com.example.ViewController.TableClasses.User;
import com.example.ViewController.TableClasses.Event;
import com.example.ViewController.TableClasses.Transport;
import com.example.ViewController.TableClasses.Hospital;
import com.example.ViewController.TableClasses.Place;
import com.example.ViewController.TableClasses.Restaurant;
import com.example.Database.queryExecutorEmployee;
import com.example.tms.Main;
import com.example.Database.queryExecutorUser;
import com.example.ViewController.General.GeneralSceneChanger;
import com.example.ViewController.TableClasses.TourEmployee;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EmployeeSceneChanger {

    boolean takeGuide = true;
    List<String> resultList = null;

    String user;
    String pw;
    String checkUser, checkPw;
    String userName, password, email, address;
    String searchPlace;
    String chosenPlace = "", chosenTransportType = "", chosenAccommodationType = "", chosenGuide = "";
    String chosenAccommodation = "", chosenTransport = "";
    String selectedPlaceInTable, loggedInEmployee;

    //For Adding Tours
    String username, place, accommodationType, accommodationName;
    String transportType, transportName, guide;

    //For Adding user
    String contact, addressinAddUser;

    //For Adding Place
    String PlaceNameInAddPlace, LocationInAddPlace, RatingInAddPlace, SpecialityInAddPlace;

    //For other addings
    String zero, one, two, three, four, five, six, seven, eight, nine;

    public EmployeeSceneChanger(String loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public void showEmployeeHomePage(Stage stage) throws Exception {

        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 75, 200, 50));
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));
        hb.setSpacing(50);
        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        Label lblHome = new Label("Home");
        lblHome.setFont(new Font("Arial", 20));
        Label lblLoggedInEmployee = new Label(loggedInEmployee);
        lblLoggedInEmployee.setFont(new Font("Arial", 15));

        Button btnManageUsers = new Button("Manage Users");
        Button btnManagePlaces = new Button("Manage Places");
        Button btnManageTours = new Button("Manage Tours");

        Button btnlogOut = new Button("Log Out");

        gridPane.add(btnManageUsers, 0, 1);
        gridPane.add(btnManagePlaces, 0, 2);
        gridPane.add(btnManageTours, 0, 3);
        //gridPane.add(btnlogOut, 0, 9);
        //gridPane.add(lblLoggedInEmployee, 1, 9);

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnManagePlaces.setOnAction((ActionEvent event) -> {
            try {
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnManageUsers.setOnAction((ActionEvent event) -> {
            try {
                showUsers(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnManageTours.setOnAction((ActionEvent event) -> {
            try {
                showTours(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        HBox hbLogout = new HBox();
        hbLogout.setSpacing(5);
        hbLogout.getChildren().addAll(btnlogOut, lblLoggedInEmployee);
        hb.getChildren().addAll(lblHome, hbLogout);
        bp.setTop(hb);
        bp.setCenter(gridPane);
        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp, Color.BLACK);
        stage.setScene(scene);
        stage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        stage.setResizable(true);
        stage.show();

    }

    public void showPlaces(Stage stage) {
        TableView<Place> table = new TableView<>();
        final ObservableList<Place> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(550);
        stage.setHeight(660);

        final Label labelPlace = new Label("Places");
        final Label labelinsTruction = new Label("Click on a place name to view details");

        labelPlace.setFont(new Font("Arial", 20));

        TableColumn placeNameCol = new TableColumn("Place Name");
        //placeNameCol.setMinWidth(25);
        placeNameCol.setCellValueFactory(new PropertyValueFactory<>("PlaceName"));

        TableColumn<Place, String> locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn<Place, String> ratingCol = new TableColumn<>("Rating");
        //ratingCol.setMinWidth(10);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        TableColumn<Place, String> specialityCol = new TableColumn<>("Speciality");
        //specialityCol.setMinWidth(50);
        specialityCol.setCellValueFactory(new PropertyValueFactory<>("Speciality"));

        List<List<String>> placeDataList = queryExecutorUser.getAllPlacesAllColumns();
        placeDataList.forEach((row) -> {
            Place temp = new Place(row.get(0), row.get(1), row.get(2), row.get(3));
            data.add(temp);
        });

        table.getColumns().addAll(placeNameCol, locationCol, ratingCol, specialityCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(510);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                selectedPlaceInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    showEmployeePlaceDetailsPage(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnRestore=new Button("Restore Deleted Places");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnRestore.setOnAction((ActionEvent event) -> {
            try {
                showDeletedPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(200);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblLoggedInUser);

        TextField txtPlaceName = new TextField("Place Name");
        TextField txtLocation = new TextField("Location");
        TextField txtRating = new TextField("Rating");
        TextField txtSpeciality = new TextField("Speciality");

        Button btnAddPlace = new Button("Add");

        btnAddPlace.setOnAction((ActionEvent event) -> {
            PlaceNameInAddPlace = txtPlaceName.getText();
            LocationInAddPlace = txtLocation.getText();
            RatingInAddPlace = txtRating.getText();
            SpecialityInAddPlace = txtSpeciality.getText();
            try {
                queryExecutorEmployee.addPlace(PlaceNameInAddPlace, LocationInAddPlace,
                        RatingInAddPlace, SpecialityInAddPlace);
                table.refresh();
                showPlaces(stage);
            } catch (SQLException ex) {
                
                String message="";
                if(PlaceNameInAddPlace.length()>20){
                    message+="Place Name Should be less than 20 characters";
                }
                if(LocationInAddPlace.length()>255){
                    message+="\nLocation Should be less than 255 characters";
                }
                if(RatingInAddPlace.length()>1){
                    message+="\nRating Should be 0-9";
                }
                if(SpecialityInAddPlace.length()>15){
                    message+="\nSpeciality Should be less than 15 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
                
            }
        });

        final HBox hboxAddtour1 = new HBox();
        hboxAddtour1.setSpacing(5);
        hboxAddtour1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour1.getChildren().addAll(txtPlaceName, txtLocation, txtRating);

        final HBox hboxAddtour2 = new HBox();
        hboxAddtour2.setSpacing(5);
        hboxAddtour2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour2.getChildren().addAll(txtSpeciality, btnAddPlace);

        hboxTitleBox.getChildren().addAll(labelPlace, hbox);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, labelinsTruction, table,btnRestore, hboxAddtour1, hboxAddtour2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showDeletedPlaces(Stage stage) {
        TableView<Place> table = new TableView<>();
        final ObservableList<Place> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(520);
        stage.setHeight(630);

        final Label labelPlace = new Label("Deleted Places");
        final Label labelinsTruction = new Label("Click on a place name and to restore");

        labelPlace.setFont(new Font("Arial", 20));

        TableColumn placeNameCol = new TableColumn("Place Name");
        //placeNameCol.setMinWidth(25);
        placeNameCol.setCellValueFactory(new PropertyValueFactory<>("PlaceName"));

        TableColumn<Place, String> locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn<Place, String> ratingCol = new TableColumn<>("Rating");
        //ratingCol.setMinWidth(10);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        TableColumn<Place, String> specialityCol = new TableColumn<>("Speciality");
        //specialityCol.setMinWidth(50);
        specialityCol.setCellValueFactory(new PropertyValueFactory<>("Speciality"));

        List<List<String>> placeDataList = queryExecutorEmployee.getDeletedPlaces();
        placeDataList.forEach((row) -> {
            Place temp = new Place(row.get(0), row.get(1), row.get(2), row.get(3));
            data.add(temp);
        });

        table.getColumns().addAll(placeNameCol, locationCol, ratingCol, specialityCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(480);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                selectedPlaceInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    queryExecutorEmployee.restorePlace(selectedPlaceInTable);
                    showPlaces(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnPrev=new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnPrev.setOnAction((ActionEvent event) -> {
            try {
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnPrev,btnHome, btnlogOut, lblLoggedInUser);

        hboxTitleBox.getChildren().addAll(labelPlace, hbox);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, labelinsTruction, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showUsers(Stage stage) {
        TableView<User> table = new TableView<>();
        final ObservableList<User> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(400);
        stage.setHeight(650);

        table.setMinWidth(325);
        table.setMinHeight(300);

        final Label labelAccOfPlace = new Label("Users");
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Address");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        List<List<String>> userdataList = queryExecutorEmployee.getAllUsers();
        userdataList.forEach((row) -> {
            User temp = new User(row.get(0), row.get(1), row.get(2));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Button btnRestore=new Button("Restore Deleted Users");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));
        
        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                String selectedInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    queryExecutorEmployee.deleteUser(selectedInTable);
                    showUsers(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnRestore.setOnAction((ActionEvent event) -> {
            try {
                showDeletedUsers(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtUserName = new TextField("User Name");
        TextField txtContact = new TextField("Contact");
        TextField txtAddress = new TextField("Address");
        TextField txtPassword = new TextField("Password");

        Button btnAddUser = new Button("Add");

        btnAddUser.setOnAction((ActionEvent event) -> {
            username = txtUserName.getText();
            contact = txtContact.getText();
            addressinAddUser = txtAddress.getText();
            three = txtPassword.getText();
            try {
                queryExecutorEmployee.addUser(username, contact, addressinAddUser, three);
                table.refresh();
                showUsers(stage);
            } catch (SQLException ex) {
                
                String message="";
                if(username.length()>20){
                    message+="User Name Should be less than 20 characters";
                }
                if(contact.length()>30){
                    message+="\nContact Should be less than 30 characters";
                }
                if(addressinAddUser.length()>30){
                    message+="\nAddress Should be less than 30 characters";
                }
                if(three.length()>3){
                    message+="\nPassword Should be less than 3 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
                
            }
        });

        final HBox hboxAddtour1 = new HBox();
        hboxAddtour1.setSpacing(5);
        hboxAddtour1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour1.getChildren().addAll(txtUserName, txtContact);

        final HBox hboxAddtour2 = new HBox();
        hboxAddtour2.setSpacing(5);
        hboxAddtour2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour2.getChildren().addAll(txtAddress, txtPassword, btnAddUser);

        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        Label lblInstruction=new Label("Click on a User Name to Delete");
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, lblInstruction,table, btnRestore,hboxAddtour1, hboxAddtour2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showDeletedUsers(Stage stage) {
        TableView<User> table = new TableView<>();
        final ObservableList<User> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(480);
        stage.setHeight(650);

        table.setMinWidth(325);
        table.setMinHeight(300);

        final Label labelAccOfPlace = new Label("Deleted Users");
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Address");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        List<List<String>> userdataList = queryExecutorEmployee.getDeletedUsers();
        userdataList.forEach((row) -> {
            User temp = new User(row.get(0), row.get(1), row.get(2));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUsers(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                String selectedInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    queryExecutorEmployee.restoreUser(selectedInTable);
                    showUsers(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        Label lblInstruction=new Label("Click on a User Name to Restore");

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox,lblInstruction, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showTours(Stage stage) {
        TableView<TourEmployee> table = new TableView<>();
        final ObservableList<TourEmployee> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(800);
        stage.setHeight(650);

        final Label labelAccOfPlace = new Label("Tours");
        labelAccOfPlace.setFont(new Font("Arial", 20));
        
        TableColumn tourNoCol = new TableColumn("Tour No");
        //placeNameCol.setMinWidth(25);
        tourNoCol.setCellValueFactory(new PropertyValueFactory<>("TourNo"));
        
        TableColumn userCol = new TableColumn("User");
        //placeNameCol.setMinWidth(25);
        userCol.setCellValueFactory(new PropertyValueFactory<>("User"));

        TableColumn placeCol = new TableColumn("Place");
        //placeNameCol.setMinWidth(25);
        placeCol.setCellValueFactory(new PropertyValueFactory<>("Place"));

        TableColumn accommodationTypeCol = new TableColumn<>("Accommodation Type");
        //locationCol.setMinWidth(180);
        accommodationTypeCol.setCellValueFactory(new PropertyValueFactory<>("AccommodationType"));

        TableColumn accommodationCol = new TableColumn<>("Accommodation Name");
        //locationCol.setMinWidth(180);
        accommodationCol.setCellValueFactory(new PropertyValueFactory<>("Accommodation"));

        TableColumn transportTypeCol = new TableColumn<>("Transport Type");
        //locationCol.setMinWidth(180);
        transportTypeCol.setCellValueFactory(new PropertyValueFactory<>("TransportType"));

        TableColumn transportCol = new TableColumn<>("Transport Name");
        //locationCol.setMinWidth(180);
        transportCol.setCellValueFactory(new PropertyValueFactory<>("Transport"));

        TableColumn guideNameCol = new TableColumn<>("Guide");
        //ratingCol.setMinWidth(10);
        guideNameCol.setCellValueFactory(new PropertyValueFactory<>("Guide"));

        List<List<String>> tourDataList = queryExecutorEmployee.getAllTours();
        tourDataList.forEach((row) -> {
            TourEmployee temp = new TourEmployee(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6),row.get(7));
            data.add(temp);
        });

        table.getColumns().addAll(tourNoCol,userCol, placeCol, accommodationTypeCol, accommodationCol, transportTypeCol, transportCol, guideNameCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(765);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                String selectedInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    queryExecutorEmployee.deleteTour(selectedInTable);
                    showTours(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Button btnRestore = new Button("Restore Deleted Tours");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnRestore.setOnAction((ActionEvent event) -> {
            try {
                showDeletedTours(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtUserName = new TextField("User Name");
        TextField txtPlace = new TextField("Place");
        TextField txtAccommodationType = new TextField("Accommodation Type");
        TextField txtAccommodationName = new TextField("Accommodation Name");
        TextField txtTransportType = new TextField("Transport Type");
        TextField txtTransportName = new TextField("Transport Name");
        TextField txtGuide = new TextField("Guide");

        Button btnAddTour = new Button("Add");

        btnAddTour.setOnAction((ActionEvent event) -> {
            username = txtUserName.getText();
            place = txtPlace.getText();
            accommodationType = txtAccommodationType.getText();
            accommodationName = txtAccommodationName.getText();
            transportType = txtTransportType.getText();
            transportName = txtTransportName.getText();
            guide = txtGuide.getText();
            try {
                queryExecutorEmployee.addTour(place, username, transportType, transportName,
                        accommodationType, accommodationName, guide);
                table.refresh();
                username = place = accommodationType = accommodationName = transportType = transportName = guide = "";
                showTours(stage);
            } catch (Exception ex) {
                
                String message="";
                if(username.length()>20){
                    message+="User Name Should be less than 20 characters";
                }
                if(place.length()>20){
                    message+="\nPlace Name Should be less than 20 characters";
                }
                if(accommodationType.length()>6){
                    message+="\nAccommodation Type Should be less than 6 characters";
                }
                
                if(transportType.length()>5){
                    message+="\nTransport Type Should be less than 5 characters";
                }
                
                if(accommodationName.length()>20){
                    message+="\nAccommodation Name Should be less than 20 characters";
                }
                if(transportName.length()>20){
                    message+="\nTransport Name Should be less than 20 characters";
                }
                
                if(guide.length()>20){
                    message+="\nGuide Name Should be less than 20 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddtour1 = new HBox();
        hboxAddtour1.setSpacing(5);
        hboxAddtour1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour1.getChildren().addAll(txtUserName, txtPlace, txtAccommodationType, txtAccommodationName);

        final HBox hboxAddtour2 = new HBox();
        hboxAddtour2.setSpacing(5);
        hboxAddtour2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtour2.getChildren().addAll(txtTransportType, txtTransportName, txtGuide, btnAddTour);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(350);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        Label lblInstruction=new Label("Click on a Tour to Delete");
        

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox,lblInstruction, table, btnRestore,hboxAddtour1, hboxAddtour2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showDeletedTours(Stage stage) {
        TableView<TourEmployee> table = new TableView<>();
        final ObservableList<TourEmployee> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(620);

        final Label labelAccOfPlace = new Label("Deleted Tours");
        labelAccOfPlace.setFont(new Font("Arial", 20));
        
         TableColumn tourNoCol = new TableColumn("Tour No");
        //placeNameCol.setMinWidth(25);
        tourNoCol.setCellValueFactory(new PropertyValueFactory<>("TourNo"));

        TableColumn userCol = new TableColumn("User");
        //placeNameCol.setMinWidth(25);
        userCol.setCellValueFactory(new PropertyValueFactory<>("User"));

        TableColumn placeCol = new TableColumn("Place");
        //placeNameCol.setMinWidth(25);
        placeCol.setCellValueFactory(new PropertyValueFactory<>("Place"));

        TableColumn accommodationTypeCol = new TableColumn<>("Accommodation Type");
        //locationCol.setMinWidth(180);
        accommodationTypeCol.setCellValueFactory(new PropertyValueFactory<>("AccommodationType"));

        TableColumn accommodationCol = new TableColumn<>("Accommodation Name");
        //locationCol.setMinWidth(180);
        accommodationCol.setCellValueFactory(new PropertyValueFactory<>("Accommodation"));

        TableColumn transportTypeCol = new TableColumn<>("Transport Type");
        //locationCol.setMinWidth(180);
        transportTypeCol.setCellValueFactory(new PropertyValueFactory<>("TransportType"));

        TableColumn transportCol = new TableColumn<>("Transport Name");
        //locationCol.setMinWidth(180);
        transportCol.setCellValueFactory(new PropertyValueFactory<>("Transport"));

        TableColumn guideNameCol = new TableColumn<>("Guide");
        //ratingCol.setMinWidth(10);
        guideNameCol.setCellValueFactory(new PropertyValueFactory<>("Guide"));

        List<List<String>> tourDataList = queryExecutorEmployee.getDeletedTours();
        tourDataList.forEach((row) -> {
            TourEmployee temp = new TourEmployee(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6),row.get(7));
            data.add(temp);
        });

        table.getColumns().addAll(tourNoCol,userCol, placeCol, accommodationTypeCol, accommodationCol, transportTypeCol, transportCol, guideNameCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(715);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();
        
        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                String selectedInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    queryExecutorEmployee.restoreTour(selectedInTable);
                    showTours(stage);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));
        
        

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showTours(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(300);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        Label lblInstruction=new Label("Click on a Tour to Restore");
        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox,lblInstruction,table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showEmployeePlaceDetailsPage(Stage stage) throws Exception {

        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(580);

        Text text = new Text("Details of\n" + selectedPlaceInTable);
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        Label loggedInUserLabel = new Label(loggedInEmployee);
        loggedInUserLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 20));

        Button btnViewAccommodations = new Button("Accommodations");
        Button btnViewTransports = new Button("Transports");
        Button btnviewPoliceStations = new Button("Police Stations");
        Button btnViewHospitals = new Button("Hospitals");
        Button btnViewBanks = new Button("Banks");
        Button btnViewRestaurants = new Button("Restaurants");
        Button btnViewGuides = new Button("Guides");
        Button btnViewEvents = new Button("Events");
        Button btnPreviousPage = new Button("Previous Page");
        Button btnHome = new Button("Home");
        Button btnLogOut = new Button("Log Out"); 
        Button btnDelete=new Button("Delete");

        //Action for btns
        btnLogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EmployeeSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnPreviousPage.setOnAction((ActionEvent event) -> {
            try {
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewAccommodations.setOnAction((ActionEvent event) -> {
            try {
                showAccommodationsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewTransports.setOnAction((ActionEvent event) -> {
            try {
                showTransportsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnviewPoliceStations.setOnAction((ActionEvent event) -> {
            try {
                showPoliceStationsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewRestaurants.setOnAction((ActionEvent event) -> {
            try {
                showRestaurantsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewHospitals.setOnAction((ActionEvent event) -> {
            try {
                showHospitalsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewBanks.setOnAction((ActionEvent event) -> {
            try {
                showBanksOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewGuides.setOnAction((ActionEvent event) -> {
            try {
                showGuidesOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnViewEvents.setOnAction((ActionEvent event) -> {
            try {
                showEventsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewEvents.setOnAction((ActionEvent event) -> {
            try {
                showEventsOfPlace(stage, selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnDelete.setOnAction((ActionEvent event) -> {
            try {
                queryExecutorEmployee.deletePlace(selectedPlaceInTable);
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        final HBox containerbox = new HBox();
        containerbox.setSpacing(5);
        containerbox.setPadding(new Insets(10, 0, 0, 10));

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));

        hbox.getChildren().addAll(btnPreviousPage, btnHome, btnLogOut, loggedInUserLabel);

        final HBox hboxDelRes = new HBox();
        hboxDelRes.setSpacing(5);
        hboxDelRes.setPadding(new Insets(10, 0, 0, 10));
        hboxDelRes.getChildren().addAll(btnDelete);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        final VBox motherbox = new VBox();
        motherbox.setSpacing(5);
        motherbox.setPadding(new Insets(10, 0, 0, 10));

        btnViewAccommodations.setMinWidth(vbox.getPrefWidth());
        btnViewTransports.setMinWidth(vbox.getPrefWidth());
        btnViewBanks.setMinWidth(50);
        btnViewEvents.setMinWidth(vbox.getPrefWidth());
        btnViewGuides.setMinWidth(vbox.getPrefWidth());
        btnViewHospitals.setMinWidth(vbox.getPrefWidth());
        btnViewRestaurants.setMinWidth(vbox.getPrefWidth());
        btnviewPoliceStations.setMinWidth(vbox.getPrefWidth());

        vbox.getChildren().addAll(text, btnViewAccommodations, btnViewTransports,
                btnViewHospitals, btnViewRestaurants, btnviewPoliceStations,
                btnViewBanks, btnViewGuides, btnViewEvents);

        final ImageView imv = new ImageView();
        final Image image2 = new Image(Main.class.getResourceAsStream(queryExecutorUser.getPlaceImage(selectedPlaceInTable)));
        imv.setImage(image2);
        imv.setFitWidth(530);
        imv.setFitHeight(400);

        Label lblPlace = new Label(selectedPlaceInTable);
        lblPlace.setFont(Font.font("Courier New", FontWeight.BOLD, 30));

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(150);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(lblPlace, hbox);

        containerbox.getChildren().addAll(imv, vbox);
        motherbox.getChildren().addAll(hboxTitleBox, containerbox, hboxDelRes);
        ((Group) scene.getRoot()).getChildren().addAll(motherbox);
        stage.setScene(scene);
        stage.show();
    }

    public void showAccommodationsOfPlace(Stage stage, String place) {
        TableView<Accommodation> table = new TableView<>();
        final ObservableList<Accommodation> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(630);

        final Label labelAccOfPlace = new Label("Accommodations of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn typeCol = new TableColumn<>("Type");
        //ratingCol.setMinWidth(10);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));

        TableColumn costCol = new TableColumn<>("Cost");
        //ratingCol.setMinWidth(10);
        costCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        TableColumn ratingCol = new TableColumn<>("Rating");
        //ratingCol.setMinWidth(10);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        List<List<String>> placeDataList = queryExecutorEmployee.getAccommodationsOfPlace(place);
        placeDataList.forEach((row) -> {
            Accommodation temp = new Accommodation(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol, typeCol, ratingCol, costCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(715);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtAccommodationName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtRating = new TextField("Rating");
        TextField txtCost = new TextField("Cost");
        TextField txtContact = new TextField("Contact");
        TextField txtAccommodationType = new TextField("Type");

        Button btnAddAccommodation = new Button("Add");

        btnAddAccommodation.setOnAction((ActionEvent event) -> {
            zero = txtAccommodationName.getText();
            one = txtLocation.getText();
            two = txtRating.getText();
            three = txtCost.getText();
            four = txtContact.getText();
            five = txtAccommodationType.getText();
            try {
                queryExecutorEmployee.addAccommodation(zero, one, two, three, four, five, place);
                table.refresh();
                showAccommodationsOfPlace(stage, place);
            } catch (SQLException ex) {
                
                String message="";
                
                if(zero.length()>20){
                    message+="\nAccommodation Name Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Location Should be less than 255 characters";
                }
                if(two.length()>1){
                    message+="\nRating Should be less than 0-9";
                }
                if(five.length()>6){
                    message+="\nAccommodation Type Should be less than 6 characters";
                }
                
                if(four.length()>255){
                    message+="\nContact Should be less than 255 characters";
                }
                
                if(three.length()>100){
                    message+="\nCost Should be less than 100 characters";
                }
               
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddAccommodation1 = new HBox();
        hboxAddAccommodation1.setSpacing(5);
        hboxAddAccommodation1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddAccommodation1.getChildren().addAll(txtAccommodationName,
                txtLocation, txtRating);

        final HBox hboxAddAccommodation2 = new HBox();
        hboxAddAccommodation2.setSpacing(5);
        hboxAddAccommodation2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddAccommodation2.getChildren().addAll(txtCost, txtContact, txtAccommodationType, btnAddAccommodation);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(130);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddAccommodation1, hboxAddAccommodation2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showTransportsOfPlace(Stage stage, String place) {
        TableView<Transport> table = new TableView<>();
        final ObservableList<Transport> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(770);
        stage.setHeight(620);

        final Label labelAccOfPlace = new Label("Transports of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Starting Place");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn typeCol = new TableColumn<>("Type");
        //ratingCol.setMinWidth(10);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));

        TableColumn costCol = new TableColumn<>("Cost");
        //ratingCol.setMinWidth(10);
        costCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        TableColumn ratingCol = new TableColumn<>("Rating");
        //ratingCol.setMinWidth(10);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        TableColumn startTimeCol = new TableColumn<>("Starting Time");
        //ratingCol.setMinWidth(10);
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));

        List<List<String>> transportsOfPlaceDataList = queryExecutorUser.getTransportsOfPlace(place);
        transportsOfPlaceDataList.forEach((row) -> {
            Transport temp = new Transport(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, contactCol, locationCol, startTimeCol, typeCol, ratingCol, costCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(735);

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtTransportName = new TextField("Name");
        TextField txtContact = new TextField("Contact");
        TextField txtStartingPlace = new TextField("Starting Place");
        TextField txtStartingTime = new TextField("Starting Time");
        TextField txtRating = new TextField("Rating");
        TextField txtCost = new TextField("Cost");
        TextField txtType = new TextField("Type");

        Button btnAddTransport = new Button("Add");

        btnAddTransport.setOnAction((ActionEvent event) -> {
            zero = txtTransportName.getText();
            one = txtContact.getText();
            two = txtStartingPlace.getText();
            three = txtStartingTime.getText();
            four = txtRating.getText();
            five = txtCost.getText();
            six = txtType.getText();

            try {
                queryExecutorEmployee.addTransport(zero, one, two, three, four, five, six, place);
                table.refresh();
                showTransportsOfPlace(stage, place);
            } catch (SQLException ex) {
                
                String message="";
                
                if(zero.length()>20){
                    message+="\nTransport Name Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Contact Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nStarting Place Should be less than 255 characters";
                }
                if(three.length()>5){
                    message+="\nStarting Time Should be less than 5 characters";
                }
                
                if(four.length()>1){
                    message+="\nRating Should be 0-9";
                }
                
                if(five.length()>100){
                    message+="\nCost Should be less than 100 characters";
                }
               
                if(six.length()>5){
                    message+="\nType Should be less than 5 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddTransport1 = new HBox();
        hboxAddTransport1.setSpacing(5);
        hboxAddTransport1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddTransport1.getChildren().addAll(txtTransportName, txtContact,
                txtStartingPlace, txtStartingTime);

        final HBox hboxAddtransport2 = new HBox();
        hboxAddtransport2.setSpacing(5);
        hboxAddtransport2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtransport2.getChildren().addAll(txtRating, txtCost, txtType, btnAddTransport);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(200);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddTransport1, hboxAddtransport2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showPoliceStationsOfPlace(Stage stage, String place) {
        TableView<PoliceStation> table = new TableView<>();
        final ObservableList<PoliceStation> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(620);
        stage.setHeight(630);

        final Label labelAccOfPlace = new Label("Police Stations of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        List<List<String>> policeStationsOfPlaceDataList = queryExecutorUser.getPoliceStationsOfPlace(place);
        policeStationsOfPlaceDataList.forEach((row) -> {
            PoliceStation temp = new PoliceStation(row.get(0), row.get(1), row.get(2));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, contactCol, locationCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(315);
        table.setMinHeight(300);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtContact = new TextField("Contact");

        Button btnAddpoliceStation = new Button("Add");

        btnAddpoliceStation.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtContact.getText();
            two = txtLocation.getText();
            try {
                queryExecutorEmployee.addPoliceStation(zero, one, two, place);
                table.refresh();
                showPoliceStationsOfPlace(stage, place);
            } catch (Exception ex) {
                
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Contact Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nLocation Should be less than 255 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddTransport1 = new HBox();
        hboxAddTransport1.setSpacing(5);
        hboxAddTransport1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddTransport1.getChildren().addAll(txtName, txtContact);

        final HBox hboxAddtransport2 = new HBox();
        hboxAddtransport2.setSpacing(5);
        hboxAddtransport2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddtransport2.getChildren().addAll(txtLocation, btnAddpoliceStation);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddTransport1, hboxAddtransport2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showRestaurantsOfPlace(Stage stage, String place) {
        TableView<Restaurant> table = new TableView<>();
        final ObservableList<Restaurant> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(600);
        stage.setHeight(630);

        table.setMinWidth(490);

        final Label labelAccOfPlace = new Label("Restaurants of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn ratingCol = new TableColumn<>("Rating");
        //specialityCol.setMinWidth(50);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        TableColumn minCostCol = new TableColumn<>("Min Cost");
        //specialityCol.setMinWidth(50);
        minCostCol.setCellValueFactory(new PropertyValueFactory<>("MinCost"));

        TableColumn maxCostCol = new TableColumn<>("Max Cost");
        //specialityCol.setMinWidth(50);
        maxCostCol.setCellValueFactory(new PropertyValueFactory<>("MaxCost"));

        List<List<String>> restaurantsOfPlaceDataList = queryExecutorUser.getRestaurantsOfPlace(place);
        restaurantsOfPlaceDataList.forEach((row) -> {
            Restaurant temp = new Restaurant(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, ratingCol, minCostCol, maxCostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtRating = new TextField("Rating");
        TextField txtCostMin = new TextField("Min Cost");
        TextField txtCostMax = new TextField("Max Cost");

        Button btnAddRestaurant = new Button("Add");

        btnAddRestaurant.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtRating.getText();
            two = txtLocation.getText();
            three = txtCostMin.getText();
            four = txtCostMax.getText();
            try {
                queryExecutorEmployee.addRestaurant(zero,two,one, three, four, place);
                table.refresh();
                showRestaurantsOfPlace(stage, place);
            } catch (Exception ex) {
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>1){
                    message+="\nRating Should be 0-9";
                }
                if(two.length()>255){
                    message+="\nLocation Should be less than 255 characters";
                }
                if(three.length()>100){
                    message+="\nCost Min Should be less than 100 characters";
                }
                
                if(four.length()>100){
                    message+="\nCost Max Should be less than 100 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddRestaurant1 = new HBox();
        hboxAddRestaurant1.setSpacing(5);
        hboxAddRestaurant1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant1.getChildren().addAll(txtName, txtLocation, txtRating);

        final HBox hboxAddRestaurant2 = new HBox();
        hboxAddRestaurant2.setSpacing(5);
        hboxAddRestaurant2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant2.getChildren().addAll(txtCostMin, txtCostMax, btnAddRestaurant);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddRestaurant1, hboxAddRestaurant2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showHospitalsOfPlace(Stage stage, String place) {
        TableView<Hospital> table = new TableView<>();
        final ObservableList<Hospital> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(600);
        stage.setHeight(630);

        table.setMinWidth(350);

        final Label labelAccOfPlace = new Label("Hospitals of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn CostCol = new TableColumn<>("Cost");
        //specialityCol.setMinWidth(50);
        CostCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        List<List<String>> hospitalsOfPlaceDataList = queryExecutorUser.getHospitalsOfPlace(place);
        hospitalsOfPlaceDataList.forEach((row) -> {
            Hospital temp = new Hospital(row.get(0), row.get(1), row.get(2), row.get(3));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol, CostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtContact = new TextField("Contact");
        TextField txtCost = new TextField("Cost");

        Button btnAddHospital = new Button("Add");

        btnAddHospital.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtLocation.getText();
            two = txtContact.getText();
            three = txtCost.getText();
            try {
                queryExecutorEmployee.addHospital(zero, one, two, three, place);
                table.refresh();
                showHospitalsOfPlace(stage, place);
            } catch (SQLException ex) {
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Location Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nContact Should be less than 255 characters";
                }
                if(three.length()>100){
                    message+="\nCost Should be less than 100 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddRestaurant1 = new HBox();
        hboxAddRestaurant1.setSpacing(5);
        hboxAddRestaurant1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant1.getChildren().addAll(txtName, txtLocation);

        final HBox hboxAddRestaurant2 = new HBox();
        hboxAddRestaurant2.setSpacing(5);
        hboxAddRestaurant2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant2.getChildren().addAll(txtContact, txtCost, btnAddHospital);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddRestaurant1, hboxAddRestaurant2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showBanksOfPlace(Stage stage, String place) {
        TableView<Bank> table = new TableView<>();
        final ObservableList<Bank> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(580);
        stage.setHeight(630);

        table.setMinWidth(530);

        final Label labelAccOfPlace = new Label("Banks of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn holidayCol = new TableColumn<>("Holiday");
        //specialityCol.setMinWidth(50);
        holidayCol.setCellValueFactory(new PropertyValueFactory<>("Holiday"));

        TableColumn openingTimeCol = new TableColumn<>("Opening Time");
        //specialityCol.setMinWidth(50);
        openingTimeCol.setCellValueFactory(new PropertyValueFactory<>("OpeningTime"));

        List<List<String>> restaurantsOfPlaceDataList = queryExecutorUser.getBanksOfPlace(place);
        restaurantsOfPlaceDataList.forEach((row) -> {
            Bank temp = new Bank(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol, holidayCol, openingTimeCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtContact = new TextField("Contact");
        TextField txtHoliday = new TextField("Holiday");
        TextField txtOpeningTime = new TextField("Opening Time");

        Button btnAddBank = new Button("Add");

        btnAddBank.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtLocation.getText();
            two = txtContact.getText();
            three = txtHoliday.getText();
            four = txtOpeningTime.getText();
            try {
                queryExecutorEmployee.addBank(zero, one, two, three, four, place);
                table.refresh();
                showBanksOfPlace(stage, place);
            } catch (Exception ex) {
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Location Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nContact Should be less than 255 characters";
                }
                if(three.length()>9){
                    message+="\nHoliday Should be less than 9 characters";
                }
                
                if(four.length()>8){
                    message+="\nOpening Time Should be less than 8 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddRestaurant1 = new HBox();
        hboxAddRestaurant1.setSpacing(5);
        hboxAddRestaurant1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant1.getChildren().addAll(txtName, txtLocation, txtContact);

        final HBox hboxAddRestaurant2 = new HBox();
        hboxAddRestaurant2.setSpacing(5);
        hboxAddRestaurant2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant2.getChildren().addAll(txtHoliday, txtOpeningTime, btnAddBank);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(50);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddRestaurant1, hboxAddRestaurant2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showGuidesOfPlace(Stage stage, String place) {
        TableView<Guide> table = new TableView<>();
        final ObservableList<Guide> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(540);
        stage.setHeight(630);

        table.setMinWidth(460);

        final Label labelAccOfPlace = new Label("Guides of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn ratingCol = new TableColumn<>("Rating");
        //specialityCol.setMinWidth(50);
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("Rating"));

        TableColumn CostCol = new TableColumn<>("Cost");
        //specialityCol.setMinWidth(50);
        CostCol.setCellValueFactory(new PropertyValueFactory<>("Cost"));

        List<List<String>> guidesOfPlaceDataList = queryExecutorUser.getAllColumnsOfGuidesOfPlace(place);
        guidesOfPlaceDataList.forEach((row) -> {
            Guide temp = new Guide(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol, ratingCol, CostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtContact = new TextField("Contact");
        TextField txtRating = new TextField("Rating");
        TextField txtCost = new TextField("Cost");

        Button btnAddGuide = new Button("Add");

        btnAddGuide.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtLocation.getText();
            two = txtContact.getText();
            three = txtRating.getText();
            four = txtCost.getText();
            try {
                queryExecutorEmployee.addGuide(zero, one, two, three, four, place);
                table.refresh();
                showGuidesOfPlace(stage, place);
            } catch (Exception ex) {
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Location Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nContact Should be less than 255 characters";
                }
                if(three.length()>1){
                    message+="\nRating Should be 0-9";
                }
                
                if(four.length()>100){
                    message+="\nCost Should be less than 100 characters";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddRestaurant1 = new HBox();
        hboxAddRestaurant1.setSpacing(5);
        hboxAddRestaurant1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant1.getChildren().addAll(txtName, txtLocation, txtContact);

        final HBox hboxAddRestaurant2 = new HBox();
        hboxAddRestaurant2.setSpacing(5);
        hboxAddRestaurant2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant2.getChildren().addAll(txtRating, txtCost, btnAddGuide);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddRestaurant1, hboxAddRestaurant2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public void showEventsOfPlace(Stage stage, String place) {
        TableView<Event> table = new TableView<>();
        final ObservableList<Event> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());

        stage.setWidth(530);
        stage.setHeight(630);

        table.setMinWidth(480);

        final Label labelAccOfPlace = new Label("Events of " + place);
        labelAccOfPlace.setFont(new Font("Arial", 20));

        TableColumn NameCol = new TableColumn("Name");
        //placeNameCol.setMinWidth(25);
        NameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));

        TableColumn locationCol = new TableColumn<>("Location");
        //locationCol.setMinWidth(180);
        locationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));

        TableColumn contactCol = new TableColumn<>("Contact");
        //specialityCol.setMinWidth(50);
        contactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));

        TableColumn startDateCol = new TableColumn<>("Start Date");
        //specialityCol.setMinWidth(50);
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));

        TableColumn durationCol = new TableColumn<>("Duration");
        //specialityCol.setMinWidth(50);
        durationCol.setCellValueFactory(new PropertyValueFactory<>("Duration"));

        List<List<String>> eventsOfPlaceDataList = queryExecutorUser.getEventsOfPlace(place);
        eventsOfPlaceDataList.forEach((row) -> {
            Event temp = new Event(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol, contactCol, startDateCol, durationCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInEmployee);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showEmployeeHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showEmployeePlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage, btnHome, btnlogOut, lblLoggedInUser);

        TextField txtName = new TextField("Name");
        TextField txtLocation = new TextField("Location");
        TextField txtContact = new TextField("Contact");
        TextField txtStartDate = new TextField("Start Date");
        TextField txtDuration = new TextField("Duration");

        Button btnAddEvent = new Button("Add");

        btnAddEvent.setOnAction((ActionEvent event) -> {
            zero = txtName.getText();
            one = txtLocation.getText();
            two = txtContact.getText();
            three = txtStartDate.getText();
            four = txtDuration.getText();
            try {
                queryExecutorEmployee.addEvent(zero, one, two, three, four, place);
                table.refresh();
                showEventsOfPlace(stage, place);
            } catch (Exception ex) {
                String message="";
                
                if(zero.length()>20){
                    message+="\nName Should be less than 20 characters";
                }
                if(one.length()>255){
                    message+="Location Should be less than 255 characters";
                }
                if(two.length()>255){
                    message+="\nContact Should be less than 255 characters";
                }
                if(three.length()>12){
                    message+="\nStart Date Should be less than 12 characters";
                }
                
                if(four.length()>1){
                    message+="\nDuration Should be 1-9 days";
                }
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText(message);
                alert.showAndWait();
            }
        });

        final HBox hboxAddRestaurant1 = new HBox();
        hboxAddRestaurant1.setSpacing(5);
        hboxAddRestaurant1.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant1.getChildren().addAll(txtName, txtLocation, txtContact);

        final HBox hboxAddRestaurant2 = new HBox();
        hboxAddRestaurant2.setSpacing(5);
        hboxAddRestaurant2.setPadding(new Insets(10, 0, 0, 10));
        hboxAddRestaurant2.getChildren().addAll(txtStartDate, txtDuration, btnAddEvent);

        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table, hboxAddRestaurant1, hboxAddRestaurant2);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

}
