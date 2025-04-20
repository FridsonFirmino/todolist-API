package com.fridsonfirmino.todoList.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fridsonfirmino.todoList.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Verifica se o header Authorization existe
        var servletPath = request.getServletPath();

        if(!servletPath.equals("/tasks/create")){
            filterChain.doFilter(request, response);
            return;
        }

        var authorization = request.getHeader("Authorization");
        var authorizationDataEncode = authorization.substring("Basic".length()).trim();

        // Decode the authorization data
        String decodedAuthorization = new String(Base64.getDecoder().decode(authorizationDataEncode));
        String[] credentials = decodedAuthorization.split(":");
        String username = credentials[0];
        String password = credentials[1];

        // Validar o usuário e senha
        var user = this.userRepository.findByUsername(username);
        if(user == null){
            // 401 Unauthorized
            response.sendError(401, "Utilizador não tem autorização");
            return;
        }else{
            var result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            if(!result.verified){
                // 401 Unauthorized
                response.sendError(401, "Utilizador não tem autorização");
                return;
            }

            request.setAttribute("userId", user.getId());
            filterChain.doFilter(request, response);
        }

    }
}
