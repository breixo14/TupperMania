package segundo.dam.tuppermania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.PlanNutricional;
import java.util.List;

@Repository
public interface PlanNutricionalRepository extends MongoRepository<PlanNutricional, String> {

    List<PlanNutricional> findByUsuarioId(String usuarioId);

    // CONSULTA SIMPLE 2: Buscar planes dentro de un rango calórico
    List<PlanNutricional> findByCaloriasTotalesBetween(Integer min, Integer max);
}