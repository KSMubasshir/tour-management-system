package com.example.ViewController.User;

import com.example.ViewController.General.GeneralSceneChanger;
import com.example.tms.Main.*;
import com.example.ViewController.TableClasses.*;
import com.example.Database.queryExecutorUser;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserSceneChanger {

    String user;
    String pw;
    String checkUser, checkPw;
    String userName, password, email, address;
    String searchPlace;
    String chosenPlace = "", chosenTransportType = "", chosenAccommodationType = "", chosenGuide = "";
    String chosenAccommodation = "", chosenTransport = "";
    boolean takeGuide = true;
    List<String> resultList = null;
    String selectedPlaceInTable, loggedInUser;

    public UserSceneChanger(String loggedInUser) {
        this.loggedInUser=loggedInUser;
    }
    
    

    public void showCreateAccountPage(Stage stage) throws Exception {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 40, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(40, 60, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username:");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password:");
        final PasswordField pf = new PasswordField();
        Label lblretypePassword = new Label("Retype Password:");
        final PasswordField rpf = new PasswordField();
        Label lblEmail = new Label("Email:");
        final TextField txtEmail = new TextField();
        Label lblAddress = new Label("Addresss:");
        final TextField txtAddress = new TextField();
        Label lblOr = new Label("Or");
        Button btncreateAccount = new Button("Create Account");
        Button btnShowLoginPage = new Button("Go to Login Page");

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(lblretypePassword, 0, 2);
        gridPane.add(rpf, 1, 2);
        gridPane.add(lblEmail, 0, 3);
        gridPane.add(txtEmail, 1, 3);
        gridPane.add(lblAddress, 0, 4);
        gridPane.add(txtAddress, 1, 4);
        gridPane.add(btncreateAccount, 1, 5);
        gridPane.add(lblOr, 1, 6);
        gridPane.add(btnShowLoginPage, 1, 7);
        //Adding text
        Text text = new Text("Welcome!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btncreateAccount.setId("btnCreateAccount");
        text.setId("text");

        //Action for btnCreateAccount
        btncreateAccount.setOnAction((ActionEvent event) -> {
            userName = txtUserName.getText();
            password = pf.getText();
            address = txtAddress.getText();
            email = txtEmail.getText();
            try {
                queryExecutorUser.createAccount(userName, password, email, address);
            } catch (SQLException ex) {
                Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Account successfully created!");
            alert.show();
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnShowLoginPage.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);

        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        stage.show();
    }

    public void showUserHomePage(Stage stage) throws Exception {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 75, 200, 50));
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));
        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Implementing Nodes for GridPane
        Label lblsearchPlace = new Label("Search Place:");
        final TextField txtsearchPlace = new TextField();
        Label loggedInUserLabel = new Label(loggedInUser);
        loggedInUserLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        Button btnSearch = new Button("Search");
        Button btnLogOut = new Button("Log Out");
        Button btnviewPlaces = new Button("View Places");
        Button btnMakeTourPlan = new Button("Make Tour Plan");
        Button btnMyTours=new Button("My Tours");

        //Adding Nodes to GridPane layout
        gridPane.add(txtsearchPlace, 0, 5);
        gridPane.add(btnSearch, 0, 6);
        gridPane.add(loggedInUserLabel, 0, 13);
        gridPane.add(btnLogOut, 0, 12);
        gridPane.add(btnviewPlaces, 0, 3);
        gridPane.add(btnMakeTourPlan, 0, 4);
        gridPane.add(btnMyTours, 0, 7);

        //Adding text
        Text text = new Text("Homepage");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        //Adding text to HBox
        hb.getChildren().add(text);
        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnSearch.setId("btnSearch");
        text.setId("text");
        loggedInUserLabel.setId("loggedInUserText");
        //Action for btnCreateAccount
        btnSearch.setOnAction((ActionEvent event) -> {
            searchPlace = txtsearchPlace.getText();
            try {
                showSearchedPlaces(stage, searchPlace);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnLogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnviewPlaces.setOnAction((ActionEvent event) -> {
            try {
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnMakeTourPlan.setOnAction((ActionEvent event) -> {
            try {
                showTourPlanPageOne(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnMyTours.setOnAction((ActionEvent event) -> {
            showUserTours(stage);
        });

        //Add HBox and GridPane layout to BorderPane Layout
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
    
    public void showUserTours(Stage stage){
        TableView<TourUser> table = new TableView<>();
        final ObservableList<TourUser> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(550);

        final Label labelAccOfPlace = new Label("Tours of you");
        labelAccOfPlace.setFont(new Font("Arial", 20));

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

        List<List<String>> tourDataList = queryExecutorUser.getAllTours(loggedInUser);
        tourDataList.forEach((row) -> {
            TourUser temp = new TourUser(row.get(0), row.get(1), row.get(2), row.get(3),row.get(4),row.get(5));
            data.add(temp);
        });

        table.getColumns().addAll(placeCol,accommodationTypeCol,accommodationCol,transportTypeCol, transportCol, guideNameCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(715);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(250);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showTourPlanPageOne(Stage stage) throws SQLException {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 40, 50, 50));
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(40, 60, 20, 30));
        hb.setSpacing(20);
        //Adding GridPane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Implementing Nodes for GridPane

        Label lblSelectPlace = new Label("Select Place:");
        Label lblSelectTransportType = new Label("Transport Type:");
        Label lblSelectAccommodationType = new Label("Accommodation Type:");
        Label lblTakeGuide = new Label("Take Guide:");
        Label lblloggedInUser = new Label(loggedInUser);

        Button btnMakeTour = new Button("Next");
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");

        ObservableList<String> places;
        places = FXCollections.observableArrayList(queryExecutorUser.getAllPlaces());
        ObservableList<String> transportType;
        transportType = FXCollections.observableArrayList("AIR", "WATER", "ROAD");
        ObservableList<String> accommodationType;
        accommodationType = FXCollections.observableArrayList("HOTEL", "RESORT");
        
        ChoiceBox<String> choicePlace = new ChoiceBox<>(places);
        ChoiceBox<String> choiceTransportType = new ChoiceBox<>(transportType);
        ChoiceBox<String> choiceAccommodationType = new ChoiceBox<>(accommodationType);
        
        
        choicePlace.getSelectionModel().selectFirst();
        choiceTransportType.getSelectionModel().selectFirst();
        choiceAccommodationType.getSelectionModel().selectFirst();
        
        RadioButton rbtakeGuide = new RadioButton("Yes");
        RadioButton rbdonttakeGuide = new RadioButton("No");

        // Create a toggle group.     
        ToggleGroup tg = new ToggleGroup();

        // Add each button to a toggle group.     
        rbtakeGuide.setToggleGroup(tg);
        rbdonttakeGuide.setToggleGroup(tg);

        //Adding Nodes to GridPane layout
        gridPane.add(lblSelectPlace, 0, 0);
        gridPane.add(choicePlace, 1, 0);
        gridPane.add(lblSelectTransportType, 0, 1);
        gridPane.add(choiceTransportType, 1, 1);
        gridPane.add(lblTakeGuide, 0, 2);
        gridPane.addRow(2, rbtakeGuide, rbdonttakeGuide);
        gridPane.add(lblSelectAccommodationType, 0, 3);
        gridPane.add(choiceAccommodationType, 1, 3);
        gridPane.add(btnMakeTour, 1, 5);
        gridPane.add(btnHome, 0, 11);
        gridPane.add(btnlogOut, 1, 11);
        gridPane.add(lblloggedInUser, 2, 11);

        Text text = new Text("Make a Tour Plan!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblloggedInUser);
        
        //Adding text to HBox
        hb.getChildren().addAll(text,hbox);
        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnMakeTour.setId("btnCreateAccount");
        text.setId("text");

        choicePlace.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenPlace = places.get(new_value.intValue());
            }
        });

        choiceTransportType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenTransportType = transportType.get(new_value.intValue());
            }
        });

        choiceAccommodationType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenAccommodationType = accommodationType.get(new_value.intValue());
            }
        });
        rbtakeGuide.fire();
        // Handle action events for the radio buttons.
        rbtakeGuide.setOnAction((ActionEvent ae) -> {
            takeGuide = true;
        });
        rbdonttakeGuide.setOnAction((ActionEvent ae) -> {
            takeGuide = false;
        });
        //Action for btns
        btnMakeTour.setOnAction((ActionEvent event) -> {
            if (!(chosenPlace.equals("") || chosenAccommodationType.equals("") || chosenTransportType.equals(""))) {
                try {
                    showTourPlanPagetwo(stage);
                } catch (SQLException ex) {
                    Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("All fields must be selected");
                alert.showAndWait();
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);
        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        stage.show();
    }

    public void showTourPlanPagetwo(Stage stage) throws SQLException {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 40, 50, 50));
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(40, 60, 20, 30));
        //Adding GridPane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Implementing Nodes for GridPane

        Label lblSelectPlace = new Label("Place:");
        Label lblSelectedPlace = new Label(chosenPlace);
        Label lblSelectTransportType = new Label("Transport Type:");
        Label lblSelectedTransportType = new Label(chosenTransportType);
        Label lblSelectAccommodationType = new Label("Accommodation Type:");
        Label lblSelectedAccommodationType = new Label(chosenAccommodationType);
        Label lblTakeGuide = new Label("Take Guide:");
        Label lblloggedInUser = new Label(loggedInUser);
        Label lblSelectTransport = new Label("Select Transport:");
        Label lblSelectHotel = new Label("Select Hotel:");
        Label lblSelectResort = new Label("Select Resort:");
        Label lblSelectGuide = new Label("Select Guide:");

        Button btnMakeTour = new Button("Next");
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnChange = new Button("Change");

        ObservableList<String> transport;
        transport = FXCollections.observableArrayList(queryExecutorUser.getTransportsOfPlace(chosenPlace, chosenTransportType));
        ObservableList<String> accommodations;
        accommodations = FXCollections.observableArrayList(queryExecutorUser.getAccommodationsOfPlace(chosenPlace, chosenAccommodationType));
        ObservableList<String> guides;
        guides = FXCollections.observableArrayList(queryExecutorUser.getGuidesOfPlace(chosenPlace));
        ChoiceBox<String> choiceTransport = new ChoiceBox<>(transport);
        ChoiceBox<String> choiceAccommodation = new ChoiceBox<>(accommodations);
        ChoiceBox<String> choiceGuide = new ChoiceBox<>(guides);
        
        choiceTransport.getSelectionModel().selectFirst();
        choiceAccommodation.getSelectionModel().selectFirst();
        choiceGuide.getSelectionModel().selectFirst();

        //Adding Nodes to GridPane layout
        gridPane.add(lblSelectPlace, 0, 0);
        gridPane.add(lblSelectedPlace, 1, 0);
        gridPane.add(lblSelectTransportType, 0, 1);
        gridPane.add(lblSelectedTransportType, 1, 1);
        gridPane.add(lblSelectAccommodationType, 0, 2);
        gridPane.add(lblSelectedAccommodationType, 1, 2);
        gridPane.add(lblSelectTransport, 0, 4);
        gridPane.add(choiceTransport, 1, 4);
        if ("HOTEL".equals(chosenAccommodationType)) {
            gridPane.add(lblSelectHotel, 0, 5);
        } else {
            gridPane.add(lblSelectResort, 0, 5);
        }
        gridPane.add(choiceAccommodation, 1, 5);
        if (takeGuide == true) {
            gridPane.add(lblSelectGuide, 0, 6);
            gridPane.add(choiceGuide, 1, 6);

        }

        gridPane.add(btnChange, 1, 3);
        gridPane.add(btnMakeTour, 1, 8);
        gridPane.add(btnHome, 0, 11);
        gridPane.add(btnlogOut, 1, 11);
        gridPane.add(lblloggedInUser, 2, 11);

        Text text = new Text("Make a Tour Plan!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblloggedInUser);
        
        //Adding text to HBox
        hb.getChildren().addAll(text,hbox);
        hb.setSpacing(5);
        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnMakeTour.setId("btnCreateAccount");
        text.setId("text");

        choiceTransport.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenTransport = transport.get(new_value.intValue());
            }
        });

        choiceAccommodation.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenAccommodation = accommodations.get(new_value.intValue());
            }
        });

        choiceGuide.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                chosenGuide = guides.get(new_value.intValue());
            }
        });

        //Action for btns
        btnMakeTour.setOnAction((ActionEvent event) -> {
            if (chosenTransport.equals("") || chosenAccommodation.equals("") || (takeGuide == true && chosenGuide.equals(""))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("All fields must be selected");
                alert.showAndWait();
            } else {
                try {
                    showTourPlanPageThree(stage);
                } catch (SQLException ex) {
                    Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                chosenAccommodation = chosenAccommodationType = chosenGuide = "";
                chosenPlace = chosenTransport = chosenTransportType = "";
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            chosenAccommodation = chosenAccommodationType = chosenGuide = "";
            chosenPlace = chosenTransport = chosenTransportType = "";
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnChange.setOnAction((ActionEvent event) -> {
            try {
                showTourPlanPageOne(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);
        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        stage.show();
    }

    public void showTourPlanPageThree(Stage stage) throws SQLException {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 40, 50, 50));
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(40, 60, 20, 30));
        //Adding GridPane
        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);
        //Implementing Nodes for GridPane

        Label lblSelectPlace = new Label("Place:");
        Label lblSelectedPlace = new Label(chosenPlace);
        Label lblSelectTransportType = new Label("Transport Type:");
        Label lblSelectedTransportType = new Label(chosenTransportType);
        Label lblSelectAccommodationType = new Label("Accommodation Type:");
        Label lblSelectedAccommodationType = new Label(chosenAccommodationType);
        Label lblGuide = new Label("Guide:");
        Label lblSelectedGuide = new Label(chosenGuide);
        Label lblTransport = new Label("Transport");
        Label lblSelectedTransport = new Label(chosenTransport);
        Label lblHotel = new Label("Hotel");
        Label lblResort = new Label("Resort");
        Label lblSelectedHotel = new Label(chosenAccommodation);
        Label lblSelectedResort = new Label(chosenAccommodation);
        Label lblloggedInUser = new Label(loggedInUser);

        Button btnMakeTour = new Button("Confirm");
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnChange = new Button("Change");

        //Adding Nodes to GridPane layout
        gridPane.add(lblSelectPlace, 0, 0);
        gridPane.add(lblSelectedPlace, 1, 0);
        gridPane.add(lblSelectTransportType, 0, 1);
        gridPane.add(lblSelectedTransportType, 1, 1);
        gridPane.add(lblSelectAccommodationType, 0, 2);
        gridPane.add(lblSelectedAccommodationType, 1, 2);
        gridPane.add(lblTransport, 0, 3);
        gridPane.add(lblSelectedTransport, 1, 3);
        if ("Hotel".equals(chosenAccommodationType)) {
            gridPane.add(lblHotel, 0, 4);
            gridPane.add(new Label(chosenAccommodation), 1, 4);
        } else {
            gridPane.add(lblResort, 0, 4);
            gridPane.add(new Label(chosenAccommodation), 1, 4);
        }
        if (takeGuide == true) {
            gridPane.add(lblGuide, 0, 5);
            gridPane.add(new Label(chosenGuide), 1, 5);

        }

        //gridPane.addRow(2, rbtakeGuide,rbdonttakeGuide);
        gridPane.add(btnChange, 0, 6);
        gridPane.add(btnMakeTour, 1, 6);
        gridPane.add(btnHome, 0, 11);
        gridPane.add(btnlogOut, 1, 11);
        gridPane.add(lblloggedInUser, 2, 11);

        Text text = new Text("Make a Tour Plan!");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblloggedInUser);
        
        //Adding text to HBox
        hb.getChildren().addAll(text,hbox);
        hb.setSpacing(5);
        
        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnMakeTour.setId("btnCreateAccount");
        text.setId("text");

        //Action for btns
        btnMakeTour.setOnAction((ActionEvent event) -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Tour Plan successfully created!\nCheck your mail.");
            alert.show();
            try {
                queryExecutorUser.addTour(chosenPlace, loggedInUser, chosenTransportType, chosenTransport, chosenAccommodationType, chosenAccommodation, chosenGuide);
            } catch (SQLException ex) {
                Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }
            takeGuide = true;
            chosenAccommodation = chosenAccommodationType = chosenGuide = "";
            chosenPlace = chosenTransport = chosenTransportType = "";
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            chosenAccommodation = chosenAccommodationType = chosenGuide = "";
            chosenPlace = chosenTransport = chosenTransportType = "";
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            chosenAccommodation = chosenAccommodationType = chosenGuide = "";
            chosenPlace = chosenTransport = chosenTransportType = "";
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnChange.setOnAction((ActionEvent event) -> {
            try {
                chosenAccommodation = chosenGuide = "";
                chosenTransport = "";
                showTourPlanPagetwo(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);
        //Adding BorderPane to the scene and loading CSS
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.titleProperty().bind(
                scene.widthProperty().asString().
                        concat(" : ").
                        concat(scene.heightProperty().asString()));
        stage.show();
    }

    public void showPlaces(Stage stage) {
        TableView<Place> table = new TableView<>();
        final ObservableList<Place> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(520);
        stage.setHeight(550);

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
        table.setMinWidth(480);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                selectedPlaceInTable = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                try {
                    showUserPlaceDetailsPage(stage);
                } catch (Exception ex) {
                    Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(200);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, labelinsTruction, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showSearchedPlaces(Stage stage,String place) throws SQLException {
        TableView<Place> table = new TableView<>();
        final ObservableList<Place> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(520);
        stage.setHeight(550);

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

        List<List<String>> placeDataList = queryExecutorUser.searchPlace(place);
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
                    showUserPlaceDetailsPage(stage);
                } catch (Exception ex) {
                    Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(220);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, labelinsTruction, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showUserPlaceDetailsPage(Stage stage) throws Exception {
        
        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(550);
        
        Text text = new Text("Details of\n"+selectedPlaceInTable);
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));
        Label loggedInUserLabel = new Label(loggedInUser);
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

        //Action for btns
        btnLogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (SQLException ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UserSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnPreviousPage.setOnAction((ActionEvent event) -> {
            try {
                showPlaces(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewAccommodations.setOnAction((ActionEvent event) -> {
            try {
                showAccommodationsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewTransports.setOnAction((ActionEvent event) -> {
            try {
                showTransportsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnviewPoliceStations.setOnAction((ActionEvent event) -> {
            try {
                showPoliceStationsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewRestaurants.setOnAction((ActionEvent event) -> {
            try {
                showRestaurantsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewHospitals.setOnAction((ActionEvent event) -> {
            try {
                showHospitalsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewBanks.setOnAction((ActionEvent event) -> {
            try {
                showBanksOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewGuides.setOnAction((ActionEvent event) -> {
            try {
                showGuidesOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnViewEvents.setOnAction((ActionEvent event) -> {
            try {
                showEventsOfPlace(stage,selectedPlaceInTable);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox containerbox = new HBox();
        containerbox.setSpacing(5);
        containerbox.setPadding(new Insets(10, 0, 0, 10));
        
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        
        hbox.getChildren().addAll(btnPreviousPage,btnHome,btnLogOut,loggedInUserLabel);
        
        Label labelPlace=new Label(selectedPlaceInTable);
        labelPlace.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(150);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelPlace, hbox);
        
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
                btnViewHospitals,btnViewRestaurants,btnviewPoliceStations,
                btnViewBanks,btnViewGuides,btnViewEvents);
        
        
        final ImageView imv = new ImageView();
        final Image image2 = new Image(com.example.tms.Main.class.getResourceAsStream(queryExecutorUser.getPlaceImage(selectedPlaceInTable)));
        imv.setImage(image2);
        imv.setFitWidth(500);
        imv.setFitHeight(400);
        
        containerbox.getChildren().addAll(imv,vbox);
        motherbox.getChildren().addAll(hboxTitleBox,containerbox);
        ((Group) scene.getRoot()).getChildren().addAll(motherbox);
        stage.setScene(scene);
        stage.show();
    }
    
    public void showAccommodationsOfPlace(Stage stage,String place){
        TableView<Accommodation> table = new TableView<>();
        final ObservableList<Accommodation> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(750);
        stage.setHeight(550);

        final Label labelAccOfPlace = new Label("Accommodations of "+place);
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


        List<List<String>> placeDataList = queryExecutorUser.getAccommodationsOfPlace(place);
        placeDataList.forEach((row) -> {
            Accommodation temp = new Accommodation(row.get(0), row.get(1), row.get(2), row.get(3),row.get(4),row.get(5));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,contactCol,typeCol, ratingCol, costCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(715);

        table.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = table.getSelectionModel().getSelectedCells();

        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(130);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showTransportsOfPlace(Stage stage,String place){
        TableView<Transport> table = new TableView<>();
        final ObservableList<Transport> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(770);
        stage.setHeight(550);

        final Label labelAccOfPlace = new Label("Transports of "+place);
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
            Transport temp = new Transport(row.get(0), row.get(1), row.get(2), row.get(3),row.get(4),row.get(5),row.get(6));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol,contactCol, locationCol,startTimeCol,typeCol, ratingCol, costCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(735);
        
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(200);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showPoliceStationsOfPlace(Stage stage,String place){
        TableView<PoliceStation> table = new TableView<>();
        final ObservableList<PoliceStation> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setWidth(620);
        stage.setHeight(550);

        final Label labelAccOfPlace = new Label("Police Stations of "+place);
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

        table.getColumns().addAll(NameCol,contactCol, locationCol);
        table.setItems(data);
        table.setVisible(true);
        table.setMinWidth(315);
        table.setMinHeight(300);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showRestaurantsOfPlace(Stage stage,String place){
        TableView<Restaurant> table = new TableView<>();
        final ObservableList<Restaurant> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        
        stage.setWidth(600);
        stage.setHeight(550);
        
        table.setMinWidth(490);

        final Label labelAccOfPlace = new Label("Restaurants of "+place);
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
            Restaurant temp = new Restaurant(row.get(0), row.get(1), row.get(2),row.get(3),row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,ratingCol,minCostCol,maxCostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showHospitalsOfPlace(Stage stage,String place){
        TableView<Hospital> table = new TableView<>();
        final ObservableList<Hospital> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        
        stage.setWidth(600);
        stage.setHeight(550);
        
        table.setMinWidth(350);

        final Label labelAccOfPlace = new Label("Hospitals of "+place);
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
            Hospital temp = new Hospital(row.get(0), row.get(1), row.get(2),row.get(3));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,contactCol,CostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showBanksOfPlace(Stage stage,String place){
        TableView<Bank> table = new TableView<>();
        final ObservableList<Bank> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        
        stage.setWidth(580);
        stage.setHeight(550);
        
        table.setMinWidth(530);

        final Label labelAccOfPlace = new Label("Banks of "+place);
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
            Bank temp = new Bank(row.get(0), row.get(1), row.get(2),row.get(3),row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,contactCol,holidayCol,openingTimeCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(50);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showGuidesOfPlace(Stage stage,String place){
        TableView<Guide> table = new TableView<>();
        final ObservableList<Guide> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        
        stage.setWidth(540);
        stage.setHeight(550);
        
        table.setMinWidth(460);

        final Label labelAccOfPlace = new Label("Guides of "+place);
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
            Guide temp = new Guide(row.get(0), row.get(1), row.get(2),row.get(3),row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,contactCol,ratingCol,CostCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
    
    public void showEventsOfPlace(Stage stage,String place){
        TableView<Event> table = new TableView<>();
        final ObservableList<Event> data = FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        
        stage.setWidth(550);
        stage.setHeight(550);
        
        table.setMinWidth(480);

        final Label labelAccOfPlace = new Label("Events of "+place);
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
            Event temp = new Event(row.get(0), row.get(1), row.get(2),row.get(3),row.get(4));
            data.add(temp);
        });

        table.getColumns().addAll(NameCol, locationCol,contactCol,startDateCol,durationCol);
        table.setItems(data);
        table.setVisible(true);
        Button btnHome = new Button("Home");
        Button btnlogOut = new Button("Log Out");
        Button btnprevPage = new Button("Previous Page");
        Label lblLoggedInUser = new Label(loggedInUser);
        lblLoggedInUser.setFont(new Font("Arial", 20));

        btnHome.setOnAction((ActionEvent event) -> {
            try {
                showUserHomePage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnlogOut.setOnAction((ActionEvent event) -> {
            try {
                GeneralSceneChanger.showLoginPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        btnprevPage.setOnAction((ActionEvent event) -> {
            try {
                showUserPlaceDetailsPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(com.example.tms.Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        final HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(10, 0, 0, 10));
        hbox.getChildren().addAll(btnprevPage,btnHome, btnlogOut, lblLoggedInUser);
        
        final HBox hboxTitleBox = new HBox();
        hboxTitleBox.setSpacing(20);
        hboxTitleBox.setPadding(new Insets(10, 0, 0, 10));
        hboxTitleBox.getChildren().addAll(labelAccOfPlace, hbox);
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(hboxTitleBox, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
