package propra.splitter.persistence;

import java.util.Set;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import propra.splitter.persistence.entities.GruppenEntity;

public interface GruppenRepository extends CrudRepository<GruppenEntity, Long> {

  @Query("SELECT * "
      + "FROM gruppen_entity "
      + "INNER JOIN gruppen_teilnehmer_entity "
      + "ON gruppen_entity.id = gruppen_teilnehmer_entity.gruppen_entity "
      + "WHERE gruppen_teilnehmer_entity.username = :participant")
  Set<GruppenEntity> findAllByParticipantsContains(String participant);
}
