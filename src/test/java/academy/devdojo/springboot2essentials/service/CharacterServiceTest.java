package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.repository.CharacterRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class CharacterServiceTest {
    @InjectMocks
    private CharacterService characterService;
    @Mock
    private CharacterRepository characterRepositoryMock;

    Anime nanatsuNoTaizai = AnimeCreator.createValidAnime();

    @BeforeEach
    void setup() {
        BDDMockito.when(characterRepositoryMock.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(
                        List.of(CharacterCreator.createValidCharacter("Escanor", nanatsuNoTaizai))));
    }

//    @Test
//    @DisplayName("listAll: list all characters when successful")
//    void listAll_returnsAPageableWithAllCharacters_whenSuccessfull() {
////        Assertions.assertThat(characterService.listAll(null, null))
////                .isNotNull();
//
//        Assertions.assertThat(characterService.listAll(null, null))
//                .isNotEmpty();
//    }
}
