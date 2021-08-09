package academy.devdojo.springboot2essentials.repository;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.h2.command.ddl.CreateAggregate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("save: Save anime when successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = animeRepository.save(animeToBeSaved);
        Assertions.assertThat(savedAnime).isNotNull();
        Assertions.assertThat(savedAnime.getId()).isNotNull();
        Assertions.assertThat(savedAnime.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("save: Update anime when successful")
    void save_UpdateAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = animeRepository.save(animeToBeSaved);
        savedAnime.setName("Boku no Hero");
        Anime updatedAnime = animeRepository.save(savedAnime);

        Assertions.assertThat(updatedAnime).isNotNull();
        Assertions.assertThat(updatedAnime.getId()).isNotNull();
        Assertions.assertThat(updatedAnime.getName()).isEqualTo(savedAnime.getName());
    }

    @Test
    @DisplayName("save: throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime();
        // verify if a Anime with a empty name return a instance of ConstraintViolationException
        Assertions.assertThatThrownBy(() -> animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DisplayName("delete: Delete anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = animeRepository.save(animeToBeSaved);

        animeRepository.delete(savedAnime);

        Optional<Anime> animeOptional = animeRepository.findById(savedAnime.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("findByName: returns a list of anime when successful")
    void findByName_findListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        Anime savedAnime = animeRepository.save(animeToBeSaved);

        List<Anime> animes = animeRepository.findByName("Nanatsu no Taizai");

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes).contains(savedAnime);
    }

    @Test
    @DisplayName("findByName: returns a empty list of anime if anime name not exists")
    void findByName_findListOfAnime_WhenAnimeNameIsNotFound() {
        Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
        animeRepository.save(animeToBeSaved);

        List<Anime> animes = animeRepository.findByName("Naruto");
        // verify if returns empty list
        Assertions.assertThat(animes).isEmpty();
    }
}