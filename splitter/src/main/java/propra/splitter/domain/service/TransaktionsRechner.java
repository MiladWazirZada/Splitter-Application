package propra.splitter.domain.service;

import java.util.Set;
import propra.splitter.domain.model.Ausgabe;
import propra.splitter.domain.model.Transaktion;

public interface TransaktionsRechner {

  Set<Transaktion> rechne(Set<Ausgabe> expenses);
}
