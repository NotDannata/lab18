package com.example.viewmodel;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

import com.example.model.User;
import com.example.model.UserRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainViewModel {
    private UserRepository userRepository;

    public MainViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void importUsersFromJson(String filePath) throws Exception {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<User>>() {}.getType();
        List<User> users = gson.fromJson(new FileReader(filePath), listType);

        for (User user : users) {
            userRepository.addUser(user);
        }
    }

    public List<User> getAllUsers() throws Exception {
        return userRepository.getUsers();
    }
}
