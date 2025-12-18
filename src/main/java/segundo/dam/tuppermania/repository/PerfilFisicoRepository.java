package segundo.dam.tuppermania.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.PerfilFisico;

@Repository
public interface PerfilFisicoRepository extends JpaRepository<PerfilFisico, Long> {
}