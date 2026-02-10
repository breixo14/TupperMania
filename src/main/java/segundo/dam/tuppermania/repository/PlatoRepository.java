package segundo.dam.tuppermania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.Plato;
import java.util.List;

@Repository
public interface PlatoRepository extends MongoRepository<Plato, String> {
    List<Plato> findByNombreContainingIgnoreCase(String nombre);
}