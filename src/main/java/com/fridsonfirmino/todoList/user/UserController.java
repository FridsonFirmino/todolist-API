package com.fridsonfirmino.todoList.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;


@RestController
@RequestMapping("/users")
public class UserController {
    
    /**
     * Esse paramentro vai vir dentro do Body
     * da requisição para isso devo colocar 
     * a anotation @RequestBody
     */

    // @Autowired
    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody UserModel userModel){
        var user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null){
            // 400 Bad Request
            return ResponseEntity.status(400).body("User already exists");
        }

        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashred);
        var userCreated = this.userRepository.save(userModel);
        // 201 Created
        return ResponseEntity.status(201).body(userCreated);
    }
}
