package com.fridsonfirmino.todoList.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
    public UserModel create(@RequestBody UserModel userModel){
        // System.out.println(userModel.getUsername());
        this.userRepository.save(userModel);

        return userModel;
    }
}
