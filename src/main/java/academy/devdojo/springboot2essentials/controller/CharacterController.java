package academy.devdojo.springboot2essentials.controller;

import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.requests.CharacterPostRequestBody;
import academy.devdojo.springboot2essentials.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

//    @GetMapping("/{id}/characters")
//    public ResponseEntity<List<Character>> findAll(@PathVariable Long id,
//                                                   @RequestParam(required = false) Boolean isVillain) {
//        return ResponseEntity.ok(characterService.listAll(id));
//    }


}
