package br.com.lemes.VLbank.service;

import br.com.lemes.VLbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void createUser(UserDTO data) {

    }
}
