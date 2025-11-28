// package com.carrie.hazellabev2.utils;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// @Component
// public class PasswordGenerator implements CommandLineRunner {

//     @Override
//     public void run(String... args) throws Exception {
//         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
//         System.out.println("\nGENERADOR DE CONTRASEÑAS BCrypt");
//         System.out.println("======================================");
        
//         // Generar contraseña para admin
//         String adminPassword = "admin123";
//         String encodedAdminPassword = encoder.encode(adminPassword);
        
//         System.out.println("Contraseña original: " + adminPassword);
//         System.out.println("Contraseña encriptada BCrypt:");
//         System.out.println(encodedAdminPassword);
//         System.out.println();
        
//         // También generar para otros usuarios de prueba
//         String[] passwords = {"password123", "123456", "cliente123"};
//         for (String pwd : passwords) {
//             String encoded = encoder.encode(pwd);
//             System.out.println(pwd + " → " + encoded);
//         }
        
//         System.out.println("======================================\n");
        
//         // Verificar que funciona
//         boolean matches = encoder.matches(adminPassword, encodedAdminPassword);
//         System.out.println("Verificación BCrypt: " + matches);
//     }
// }