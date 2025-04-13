package com.fridsonfirmino.todoList.user;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/users")
public class UserController {
    
    /**
     * Esse paramentro vai vir dentro do Body
     * da requisição para isso devo colocar 
     * a anotation @RequestBody
     */
    
    @PostMapping("/create")
    public void create(@RequestBody UserModel userModel){
    }
}
