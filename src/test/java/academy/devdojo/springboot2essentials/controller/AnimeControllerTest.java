package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.service.AnimeService;
import academy.devdojo.springboot2essentials.service.CharacterService;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
    @InjectMocks
    private AnimeController animeController;

    @Mock
    private AnimeService animeServiceMock;

    @Mock
    private CharacterService characterServiceMock;

    @BeforeEach
    void setup() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        List<Character> characterList = new ArrayList<>(
                List.of(CharacterCreator.createValidCharacter("Escanor", AnimeCreator.createValidAnime())));

        // mocks the function listAll of anime service
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        // mocks the function findById of anime service
        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        // mocks the function findByName of anime service
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // mocks the function save of anime service
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // mocks the function replace of anime service
        BDDMockito.doNothing()
                .when(animeServiceMock)
                .replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        // mocks the function findById of anime service
        BDDMockito.doNothing()
                .when(animeServiceMock)
                .delete(ArgumentMatchers.anyLong());

        // mocks the function listByType of character service
        BDDMockito
                .when(characterServiceMock.listByType(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(characterList);

        // mocks the function findById of character service
        BDDMockito
                .when(characterServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CharacterCreator.createValidCharacter("Escanor", AnimeCreator.createValidAnime()));// mocks the function listCharacter of character service

        // mocks the function save from character service
        BDDMockito
                .when(characterServiceMock.save(ArgumentMatchers.anyLong(), ArgumentMatchers.any()))
                .thenReturn(CharacterCreator.createValidCharacter("Escanor", AnimeCreator.createValidAnime()));
    }

    @Test
    @DisplayName("List: return list of anime inside page object when successful")
    void list_ReturnListOfAnimesInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById: returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeController.findByName("Nanatsu no Taizai").getBody();

        Assertions.assertThat(animes)
                .isNotEmpty()
                .isNotNull()
                .hasSize(1);
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list of anime when anime.name is not found")
    void findByName_ReturnsAnEmptyList_whenAnimeNameIsntFound() {
        // mocks the function findByName of anime service
        // this mock is inside test function because this is
        // a possible test case but isn't the best case
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeController.findByName("Nanatsu no Taizai").getBody();

        Assertions.assertThat(animes)
                .isEmpty();
    }

    @Test
    @DisplayName("save: returns and persists an anime when successful")
    void save_PersistsAnime_WhenSuccessful() {
        Anime animeSaved = animeController.save(AnimePostRequestBodyCreator.createAnimeToBeSaved()).getBody();

        Assertions.assertThat(animeSaved)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("replace: returns and persists an anime existent anime when successful")
    void replace_PersistsAnExistentAnime_WhenSuccessful() {
        ResponseEntity<Void> entity = animeController
                .replace(AnimePutRequestBodyCreator.createAnimeToBeUpdated());

        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete: delete an anime existent anime when successful")
    void delete_DeleteAnExistentAnime_WhenSuccessful() {
        ResponseEntity<Void> entity = animeController
                .delete(AnimePutRequestBodyCreator.createAnimeToBeUpdated().getId());

        Assertions.assertThat(entity)
                .isNotNull();

        Assertions.assertThat(entity.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("listCharacter: returns a list of characters when successful")
    void listCharacter_ReturnsAListOfCharactersFromAnAnime_WhenSuccessful() {
        Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();
        String escanor = CharacterCreator.createValidCharacter("Escanor", nanatsuNoTaizai).getName();

        List<Character> characters = animeController.listCharacter(nanatsuNoTaizai.getId(), null).getBody();

        Assertions.assertThat(characters).isNotNull();
        Assertions.assertThat(characters.get(0).getName()).isEqualTo(escanor);
    }

    @Test
    @DisplayName("findCharacterById: returns a character when successful")
    void findCharacterById_ReturnsCharactersFromAnAnime_WhenSuccessful() {
        Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();
        Character escanor = CharacterCreator.createValidCharacter("Escanor", nanatsuNoTaizai);

        Character character = animeController.findCharacterById(nanatsuNoTaizai.getId(), escanor.getId()).getBody();

        Assertions.assertThat(character).isNotNull();
        Assertions.assertThat(character.getName()).isEqualTo(escanor.getName());
        Assertions.assertThat(character.getId()).isEqualTo(escanor.getId());
    }

    @Test
    @DisplayName("saveCharacter: saves a character when successful")
    void saveCharacter_PersistsACharacter_WhenSuccessful() {
        Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();
        Character escanor = CharacterCreator.createCharacterToBeSaved("Escanor", nanatsuNoTaizai);

        Character characterSaved = animeController.saveCharacter(nanatsuNoTaizai.getId(), escanor).getBody();

        Assertions.assertThat(characterSaved).isNotNull();
        Assertions.assertThat(characterSaved.getName()).isEqualTo(escanor.getName());
    }

    @Test
    @DisplayName("replaceCharacter: update a character when successful")
    void replaceCharacter_updateCharacter_WhenSuccessful() {
        Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();
        Character escanor = CharacterCreator.createValidUpdatedAnime("Escanor", nanatsuNoTaizai);

        Character characterSaved = animeController.replaceCharacter(nanatsuNoTaizai.getId(), escanor).getBody();

        Assertions.assertThat(characterSaved).isNotNull();
        Assertions.assertThat(characterSaved.getName()).isEqualTo(escanor.getName());
        Assertions.assertThat(characterSaved.getId()).isEqualTo(escanor.getId());
    }

    @Test
    @DisplayName("deleteCharacter: delete a character when successful")
    void deleteCharacter_deleteCharacter_WhenSuccessful() {
        Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();
        Character escanor = CharacterCreator.createValidUpdatedAnime("Escanor", nanatsuNoTaizai);

        ResponseEntity<Void> req = animeController.deleteCharacter(nanatsuNoTaizai.getId(), escanor.getId());

        Assertions.assertThat(req).isNotNull();

        Assertions.assertThat(req.getStatusCode())
                .isEqualTo(HttpStatus.NO_CONTENT);
    }
}