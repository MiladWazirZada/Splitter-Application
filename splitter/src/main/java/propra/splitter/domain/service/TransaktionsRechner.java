package propra.splitter.domain.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import propra.splitter.domain.model.Ausgabe;

public class TransaktionsRechner {

  public static Set<Liability> liabilities(Set<Ausgabe> expenses) {
    Map<String, MoneyWrapper> liabilities = new HashMap<>();
    for (Ausgabe ausgabe : expenses) {
      MoneyWrapper share = MoneyWrapper.ofCents(ausgabe.cents()).divide(ausgabe.anzahlTeilnehmer());
      for (String participant : ausgabe.schuldner()) {
        liabilities.compute(participant, (p, m) -> m == null ? share.negate() : m.subtract(share));
      }
      liabilities.compute(ausgabe.glauebiger(),
          (p, m) -> m == null ? MoneyWrapper.ofCents(ausgabe.cents())
              : m.addCents(ausgabe.cents()));
    }
    return Liability.mapToSet(liabilities);
  }
}
