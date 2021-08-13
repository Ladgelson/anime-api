package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.service.AnimeService;
import academy.devdojo.springboot2essentials.service.CharacterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {
    private final AnimeService animeService;
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable Long id) {
        return ResponseEntity.ok(animeService.findById(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody anime) {
        return new ResponseEntity<>(animeService.save(anime), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody anime) {
        animeService.replace(anime);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}/characters")
    public ResponseEntity<List<Character>> list(@PathVariable Long id, @RequestParam(required = false) Boolean isVillain) {
        return new ResponseEntity<>(characterService.listByType(id, isVillain), HttpStatus.OK);
    }

    @GetMapping("/{animeId}/characters/{characterId}")
    public ResponseEntity<Character> findById(@PathVariable Long animeId, @PathVariable Long characterId) {
        return new ResponseEntity<>(characterService.findById(characterId), HttpStatus.OK);
    }

    @PostMapping("/{id}/characters")
    public ResponseEntity<Character> save(@PathVariable Long id, @RequestBody Character character) {
        return new ResponseEntity<>(characterService.save(id, character), HttpStatus.CREATED);
    }

    @PutMapping("/{animeId}/characters")
    public ResponseEntity<Character> replace(@PathVariable Long animeId, @RequestBody Character character) {
        return new ResponseEntity<>(characterService.save(animeId, character), HttpStatus.OK);
    }

    @DeleteMapping("/{animeId}/characters/{characterId}")
    public ResponseEntity<Void> delete(@PathVariable Long animeId, @PathVariable Long characterId) {
        characterService.delete(characterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
