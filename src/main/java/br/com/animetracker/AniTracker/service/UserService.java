package br.com.animetracker.AniTracker.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.animetracker.AniTracker.model.User;
import br.com.animetracker.AniTracker.repository.UserRepository;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User registerUser(String username, String encodedPassword, String email) {
        if (userRepository.existsByUsername(username)) {
            return null;
        }

        if (userRepository.existsByEmail(email)) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setEmail(email);

        return userRepository.save(user);
    }


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }


    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }


    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
}
