package br.com.lemes.VLbank.controllers.user;

import br.com.lemes.VLbank.record.user.UserDTO;
import br.com.lemes.VLbank.record.user.UserDetailsDTO;
import br.com.lemes.VLbank.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/vlbank/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailsDTO> createUser(@RequestBody UserDTO data) {
        var user = userService.createUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDetailsDTO(user));
    }
}
