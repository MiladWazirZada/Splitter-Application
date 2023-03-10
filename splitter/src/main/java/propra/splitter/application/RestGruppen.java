package propra.splitter.application;

import java.util.Set;
import propra.splitter.domain.model.Gruppe;

public interface RestGruppen {

  long neueGruppe(Set<String> teilnehmer, String title);

  void neueAusgabe(long id, String grund, String glaeubiger, int cents,
      Set<String> schuldner);

  void schliessen(long id);

  Gruppe getGruppe(long id);

  Set<Gruppe> getGruppen(String user);
}
