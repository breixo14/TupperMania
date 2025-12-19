package segundo.dam.tuppermania.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.Rol;
import segundo.dam.tuppermania.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository repository, PasswordEncoder encoder) {
        return args -> {
            String adminEmail = "admin@tuppermania.com";
            if (repository.findByCorreo(adminEmail).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("Administrador");
                admin.setCorreo(adminEmail);
                admin.setContrasena(encoder.encode("admin123")); // Contraseña encriptada
                admin.setRol(Rol.ADMIN);
                repository.save(admin);
                System.out.println(">>> Usuario administrador creado: admin@tuppermania.com / admin123");
            }
        };
    }
}