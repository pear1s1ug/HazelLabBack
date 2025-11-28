package com.carrie.hazellabev2.controllers;

import com.carrie.hazellabev2.dto.LoginRequest;
import com.carrie.hazellabev2.entities.Usuario;
import com.carrie.hazellabev2.services.UsuarioService;
import com.carrie.hazellabev2.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Operaciones de login y autenticación de usuarios")
public class LoginRestController {
    
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtils jwtUtils;

    @Operation(summary = "Iniciar sesión", description = "Autentica un usuario con email y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas o usuario inactivo"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("=== LOGIN ATTEMPT ===");
            System.out.println("Email: " + loginRequest.getEmail());
            
            Usuario usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getPassword());
            
            System.out.println("Usuario encontrado: " + usuario.getEmail());
            System.out.println("Rol: " + usuario.getRole());
            
            // Generar token JWT
            String token = jwtUtils.generateToken(usuario.getEmail());
            System.out.println("Token generado: " + token.substring(0, 20) + "...");
            
            // Preparar respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", usuario);
            response.put("type", "Bearer");
            
            usuario.setPassword(null); // Eliminar contraseña de la respuesta
            
            System.out.println("Login exitoso para: " + usuario.getEmail() + " con rol: " + usuario.getRole());
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            System.out.println("Error en login: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}