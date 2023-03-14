package propra.splitter.web;

import java.util.Set;
import java.util.stream.Collectors;
import propra.splitter.domain.model.Ausgabe;
import propra.splitter.domain.model.Gruppe;
import propra.splitter.web.displayentities.DisplayAusgabe;
import propra.splitter.web.displayentities.DisplayGruppe;

public class Adapter {

  static DisplayGruppe toDisplayGruppe(Gruppe gruppe) {
    Set<DisplayAusgabe> ausgaben =
        gruppe.ausgaben().stream().map(Adapter::toDisplayAusgabe).collect(Collectors.toSet());
    return new DisplayGruppe(gruppe.id(), gruppe.name(), gruppe.teilnehmer(), ausgaben,
        gruppe.hatAusgaben(), gruppe.geschlossen());
  }

  static DisplayAusgabe toDisplayAusgabe(Ausgabe ausgabe) {
    String geld = String.format("%d.%02d €", ausgabe.cents() / 100, ausgabe.cents() % 100);
    return new DisplayAusgabe(ausgabe.grund(), geld, ausgabe.glauebiger(), ausgabe.schuldner(),
        ausgabe.datetime());
  }
}
