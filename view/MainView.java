package com.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import java.io.File;
import com.example.viewmodel.MainViewModel;

public class MainView {

    @FXML
    private ListView<String> userListView;

    private MainViewModel viewModel;

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
        updateUserList(); // Загрузка данных при старте
    }

    @FXML
    private void onImportClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите JSON-файл для импорта");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File file = fileChooser.showOpenDialog(userListView.getScene().getWindow());
        if (file != null) {
            try {
                // Импорт данных через ViewModel
                viewModel.importUsersFromJson(file.getAbsolutePath());
                showInfo("Импорт выполнен успешно", "Пользователи успешно добавлены в базу данных.");
                updateUserList();
            } catch (Exception e) {
                showError("Ошибка импорта", "Не удалось импортировать пользователей: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void updateUserList() {
        try {
            userListView.getItems().clear();
            viewModel.getAllUsers().forEach(user -> userListView.getItems().add(
                    String.format("%s (%s)", user.getName(), user.getEmail())
            ));
        } catch (Exception e) {
            showError("Ошибка загрузки пользователей", "Не удалось загрузить список пользователей: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}