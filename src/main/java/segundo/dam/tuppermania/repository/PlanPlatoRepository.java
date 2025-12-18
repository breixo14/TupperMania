package segundo.dam.tuppermania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.PlanPlato;

import java.util.List;

@Repository
public interface PlanPlatoRepository extends JpaRepository<PlanPlato, Long> {
    // Para obtener todos los platos asociados a un plan nutricional
    List<PlanPlato> findByPlan_IdPlan(Long idPlan);
}