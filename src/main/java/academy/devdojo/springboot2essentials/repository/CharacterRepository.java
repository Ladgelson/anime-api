package academy.devdojo.springboot2essentials.repository;

import academy.devdojo.springboot2essentials.domain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    List<Character> findByAnimeId(Long id);
    List<Character> findByAnimeIdAndIsVillain(Long animeId, Boolean isVillain);
}
