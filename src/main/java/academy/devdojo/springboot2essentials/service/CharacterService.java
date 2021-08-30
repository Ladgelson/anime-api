package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.mapper.AnimeInterfaceMapper;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.repository.CharacterRepository;
import academy.devdojo.springboot2essentials.requests.CharacterPostRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final AnimeRepository animeRepository;

    public Page<Character> listAll(Pageable pageable, Boolean isVillain) {
        Optional<Boolean> villain = Optional.ofNullable(isVillain);
        if(villain.isPresent()) return characterRepository.findCharactersByIsVillain(pageable, isVillain);
        return characterRepository.findAll(pageable);
    }

    public List<Character> listByType(Long animeId, Boolean isVillain) {
        if(isVillain == null) {
            return characterRepository.findByAnimeId(animeId);
        } else {
            return characterRepository.findByAnimeIdAndIsVillain(animeId, isVillain);
        }
    }

    public Character findById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Character ID not found"));
    }

    public Character save(Long id, Character character) {
        Optional<Anime> anime = animeRepository.findById(id);
        character.setAnime(anime.get());
        return characterRepository.save(character);
    }

    public void delete(Long id) {
        Optional<Character> character = characterRepository.findById(id);
        characterRepository.delete(character.get());
    }
}
