package propra.splitter.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import propra.splitter.application.Gruppen;
import propra.splitter.domain.model.Ausgabe;
import propra.splitter.domain.model.Gruppe;
import propra.splitter.web.displayentities.DisplayTransaktion;
import propra.splitter.web.helper.WithMockOAuth2User;

@WebMvcTest
@DisplayName("Der Controller ")
public class TransaktionsControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  Gruppen gruppen;

  @Test
  @WithMockOAuth2User(login = "Milo")
  @DisplayName("gewährt autorisierten Usern Zugriff")
  void test_01() throws Exception {
    // Arrange
    Gruppe group = mock(Gruppe.class);
    when(group.id()).thenReturn(1L);
    when(gruppen.getGruppe("Milo", 1L)).thenReturn(group);

    // Act
    mvc.perform(get("/gruppen/1/transaktionen").with(csrf()))

        // Assert
        .andExpect(status().isOk());
  }

  @Test
  @DisplayName("leitet auf GitHub weiter, wenn nicht-autorisierte User versuchen zuzugreifen")
  void test_02() throws Exception {
    // Act
    MvcResult mvcResult = mvc.perform(get("/gruppen/1/transaktionen"))

        //Assert
        .andExpect(status().is3xxRedirection()).andReturn();
    assertThat(mvcResult.getResponse().getRedirectedUrl()).contains("oauth2/authorization/github");
  }

  @Test
  @WithMockOAuth2User(login = "A")
  @DisplayName("zeigt alle Transaktionen an")
  void test_03() throws Exception {
    //Arrange
    Gruppe group = mock(Gruppe.class);
    when(group.id()).thenReturn(1L);
    when(gruppen.getGruppe("A", 1L)).thenReturn(group);

    DisplayTransaktion t1 = new DisplayTransaktion("B", "A", "300.00 €");
    DisplayTransaktion t2 = new DisplayTransaktion("C", "A", "300.00 €");
    DisplayTransaktion t3 = new DisplayTransaktion("E", "D", "50.00 €");

    Ausgabe a1 = new Ausgabe("", 60000, "A", Set.of("B", "C"), LocalDateTime.MAX);
    Ausgabe a2 = new Ausgabe("", 5000, "D", Set.of("E"), LocalDateTime.MAX);
    Set<Ausgabe> ausgaben = Set.of(a1, a2);

    when(gruppen.getGruppe("A", 1L).ausgaben()).thenReturn(ausgaben);

    //Act
    mvc.perform(get("/gruppen/1/transaktionen"))

        //Assert
        .andExpect(model().attribute("glaeubiger_transaktionen", Set.of(t1, t2)))
        .andExpect(model().attribute("andere_transaktionen", Set.of(t3)));
  }
}
