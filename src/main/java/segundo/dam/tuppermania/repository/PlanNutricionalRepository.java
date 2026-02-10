package segundo.dam.tuppermania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.PlanNutricional;
import java.util.List;

@Repository
public interface PlanNutricionalRepository extends MongoRepository<PlanNutricional, String> {

    // Busca planes donde el campo 'usuarioId' coincida
    List<PlanNutricional> findByUsuarioId(String usuarioId);
}