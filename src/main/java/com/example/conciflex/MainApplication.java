package com.example.conciflex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 780, 590);
        Image icon = new Image(getClass().getResourceAsStream("images/favicon-2.png"));
        stage.getIcons().add(icon);
        stage.setTitle("Leitura de Arquivos");
        stage.setScene(scene);
        stage.setResizable(false);
/*        stage.initStyle(StageStyle.UTILITY);*/
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}