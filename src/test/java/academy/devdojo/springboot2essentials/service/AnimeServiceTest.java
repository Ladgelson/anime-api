package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.controller.AnimeController;
import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import academy.devdojo.springboot2essentials.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2essentials.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AssertionsKt;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks
    private AnimeService animeService;
    @Mock
    private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setup() {
        // mocks the function listAll of anime service
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(AnimeCreator.createValidAnime())));

        // mocks the function findById of anime service
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        // mocks the function findByName of anime service
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // mocks the function save of anime service
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // mocks the function findById of anime service
        BDDMockito.doNothing()
                .when(animeRepositoryMock)
                .delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("findById: returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() {
        Long expectedId = AnimeCreator.createValidAnime().getId();
        Anime anime = animeService.findById(1L);

        Assertions.assertThat(anime).isNotNull();
        Assertions.assertThat(anime.getId()).isEqualTo(expectedId);
    }


    @Test
    @DisplayName("findById: throws BadRequestException when anime isn't found")
    void findById_ThrowsBadRequestException_WhenAnimeIsntFound() {

        // mocks the function findById of anime service
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> animeService.findById(1L));
    }

    @Test
    @DisplayName("findByName: returns anime when successful")
    void findByName_ReturnsAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animes = animeService.findByName("Nanatsu no Taizai");

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
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Anime> animes = animeService.findByName("Nanatsu no Taizai");

        Assertions.assertThat(animes)
                .isEmpty();
    }

    @Test
    @DisplayName("save: returns and persists an anime when successful")
    void save_PersistsAnime_WhenSuccessful() {
        Anime animeSaved = animeService.save(AnimePostRequestBodyCreator.createAnimeToBeSaved());

        Assertions.assertThat(animeSaved)
                .isNotNull()
                .isEqualTo(AnimeCreator.createValidAnime());
    }

    @Test
    @DisplayName("delete: delete an anime existent anime when successful")
    void delete_DeleteAnExistentAnime_WhenSuccessful() {
        Assertions.assertThatCode( () -> animeService.delete(1L))
                .doesNotThrowAnyException();
    }
}