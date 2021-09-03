package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.service.CharacterService;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.CharacterCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
public class CharacterControllerTest {
    @InjectMocks
    private CharacterController characterController;

    @Mock
    private CharacterService characterServiceMock;

    Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();

    // all characters
    PageImpl<Character> allCharacters = new PageImpl<Character>(List.of(
            CharacterCreator.createValidCharacter("Escanor", false, nanatsuNoTaizai),
            CharacterCreator.createValidCharacter("Ban", false, nanatsuNoTaizai),
            CharacterCreator.createValidCharacter("Capitão", false, nanatsuNoTaizai),
            CharacterCreator.createValidCharacter("Diane", true, nanatsuNoTaizai),
            CharacterCreator.createValidCharacter("Malteus", true, nanatsuNoTaizai)
    ));

    @BeforeEach
    void setup() {
        BDDMockito.when(characterServiceMock.listAll(ArgumentMatchers.any(), ArgumentMatchers.anyBoolean()))
                .thenReturn(allCharacters);
    }

    @Test
    @DisplayName("listAll: returns a pageable of Characters when successful")
    void listAll_returnsAPageableOfCharacters_whenSuccessful() {
        // mock get with no filters
        Assertions.assertThat(characterController.findAll(null, null))
                .isNotNull();

    }

    @Test
    @DisplayName("listAll: returns a pageable of villain Characters when successful")
    void listAll_returnsAPageableOfVillainCharacters_whenSuccessful() {
        // mock with the filter isVillain=true
        List<Character> villainCharacters = characterController.findAll(true, null).getBody().toList();
        Assertions.assertThat(villainCharacters.stream().filter(Character::getIsVillain).collect(Collectors.toList()))
                .hasSize(2);
    }

    @Test
    @DisplayName("listAll: returns a pageable of not villain Characters when successful")
    void listAll_returnsAPageableOfNotVillainCharacters_whenSuccessful() {
        // mock with the filter isVillain=false
        List<Character> notVillainCharacters = characterController.findAll(false, null).getBody().toList();
        Assertions.assertThat(notVillainCharacters.stream().filter(c -> !c.getIsVillain()).collect(Collectors.toList()))
                .hasSize(3);

    }
}
