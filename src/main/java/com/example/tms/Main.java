package com.example.tms;

import com.example.ViewController.General.GeneralSceneChanger;
import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Main extends Application {
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        GeneralSceneChanger gnrlScnChngr = new GeneralSceneChanger();
        gnrlScnChngr.showLoginPage(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
