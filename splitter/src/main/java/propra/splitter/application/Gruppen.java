package propra.splitter.application;

import java.util.Set;
import propra.splitter.domain.model.Gruppe;

public interface Gruppen {

  long neueGruppe(String caller, String titel);

  void neuerTeilnehmer(String caller, long id, String teilnehmer);

  void neueAusgabe(String caller, long id, String grund, String glaeubiger, int cents,
      Set<String> schuldner);

  void schliessen(String caller, long id);

  Gruppe getGruppe(String caller, long id);

  Set<Gruppe> getGruppen(String user);
}
