package segundo.dam.tuppermania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.PlanNutricional;

import java.util.List;

@Repository
public interface PlanNutricionalRepository extends JpaRepository<PlanNutricional, Long> {
    List<PlanNutricional> findByUsuario_IdUsuario(Long idUsuario);
}