package segundo.dam.tuppermania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.Rol;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(String correo);

    // CONSULTA SIMPLE 1: Buscar usuarios por su rol (Ej: Todos los ADMIN)
    List<Usuario> findByRol(Rol rol);
}