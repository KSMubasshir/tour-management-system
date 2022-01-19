package com.example.ViewController.General;

import com.example.Database.queryExecutorEmployee;
import com.example.Database.queryExecutorUser;
import com.example.tms.Main;
import com.example.ViewController.Employee.EmployeeSceneChanger;
import com.example.ViewController.User.UserSceneChanger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GeneralSceneChanger {
    
    static String checkUser,checkPw,loggedInUser;
    
    public static void showLoginPage(Stage stage) throws Exception {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 40, 50, 50));

        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(20, 20, 20, 30));

        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        final Label lblMessage = new Label();
        final Label orLabel = new Label("Or");
        Button btncreateAccount = new Button("Create Account");

        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 1, 2);
        gridPane.add(orLabel, 1, 3);
        gridPane.add(btncreateAccount, 1, 4);
        gridPane.add(lblMessage, 1, 5);

        //Adding text and DropShadow effect to it
        Text text = new Text("Tour De Bangladesh");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 28));

        //Adding text to HBox
        hb.getChildren().add(text);

        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        btncreateAccount.setId("btnCreateAccount");
        text.setId("text");

        //Action for btnLogin
        btnLogin.setOnAction((ActionEvent event) -> {
            checkUser = txtUserName.getText();
            checkPw = pf.getText();
            boolean successUser = new queryExecutorUser().validateLogin(checkUser, checkPw);
            boolean successEmployee=new queryExecutorEmployee().validateLogin(checkUser, checkPw);
            if (successUser||successEmployee) {
                loggedInUser = checkUser;
                if(successUser){
                try {
                    UserSceneChanger userScnChngr=new UserSceneChanger(loggedInUser);
                    userScnChngr.showUserHomePage(stage);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                }else
                {
                    EmployeeSceneChanger empScnChngr=new EmployeeSceneChanger(loggedInUser);
                    try {
                        empScnChngr.showEmployeeHomePage(stage);
                    } catch (Exception ex) {
                        Logger.getLogger(GeneralSceneChanger.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Credentials");
                alert.setHeaderText("Incorrect Credentials");
                alert.setContentText("The username and password you provided is not correct.");
                alert.showAndWait();
            }
            txtUserName.setText("");
            pf.setText("");
        });
        btncreateAccount.setOnAction((ActionEvent event) -> {
            try {
                UserSceneChanger userScnChngr=new UserSceneChanger("");
                userScnChngr.showCreateAccountPage(stage);
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
