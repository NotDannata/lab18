package com.example;

import com.example.model.UserRepository;
import com.example.view.MainView;
import com.example.viewmodel.MainViewModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

@Override
    public void start(Stage primaryStage) throws Exception {
        UserRepository repository = new UserRepository("users.db");
        MainViewModel viewModel = new MainViewModel(repository);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainView.fxml"));
        Scene scene = new Scene(loader.load());

        MainView view = loader.getController();
        view.setViewModel(viewModel);

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX + SQLite + MVVM");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}