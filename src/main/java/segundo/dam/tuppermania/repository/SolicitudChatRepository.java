package segundo.dam.tuppermania.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import segundo.dam.tuppermania.model.SolicitudChat;
import java.util.List;

@Repository
public interface SolicitudChatRepository extends MongoRepository<SolicitudChat, String> {
    List<SolicitudChat> findAllByOrderByFechaCreacionDesc();
}