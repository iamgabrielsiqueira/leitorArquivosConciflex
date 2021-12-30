package com.example.conciflex.controller;

import com.example.conciflex.MainApplication;
import com.example.conciflex.model.classes.Arquivo;
import com.example.conciflex.model.jdbc.JDBCArquivoDAO;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;

public class ArquivosController {

    @FXML
    public Parent mainWindow;

    @FXML
    private TableColumn tcArquivo;

    @FXML
    private TableColumn tcData;

    @FXML
    private TableView tbArquivos;

    @FXML
    private Label lbTotalArquivos;

    private ObservableList<Arquivo> listaArquivos = FXCollections.observableArrayList();

    @FXML
    public void voltar() {
        trocarJanela("main.fxml");
    }

    public void initialize() {
        mainWindow.getStylesheets().add(getClass().getResource("/css/tabela.css").toExternalForm());
        carregarListaArquivos();
    }

    public void carregarListaArquivos() {
        listaArquivos.clear();
        tcArquivo.setCellValueFactory(new PropertyValueFactory<>("arquivo"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataArquivoString"));

        try {
            listaArquivos.addAll(JDBCArquivoDAO.getInstance().listarArquivos());
        } catch (Exception e) {
            e.printStackTrace();
        }

        tbArquivos.setItems(listaArquivos);

        lbTotalArquivos.setText("Total de Arquivos: " + listaArquivos.size());
    }

    public void trocarJanela(String address){
        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource(address));

            try {
                Parent layoutWindow = loader.load();

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), layoutWindow);
                fadeTransition.setFromValue(0.5);
                fadeTransition.setToValue(1.0);
                fadeTransition.play();

                Stage stage = (Stage) mainWindow.getScene().getWindow();
                stage.setScene(new Scene(layoutWindow,780, 590));
                stage.setResizable(false);
            } catch (IOException e){
                e.printStackTrace();
            }
        });

    }
}
