package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;

public class CharacterCreator {
    public static Character createCharacterToBeSaved(String name, Anime anime) {
        return Character.builder()
                .name(name)
                .isVillain(true)
                .age(22)
                .anime(anime)
                .build();
    }

    public static Character createValidCharacter(String name, Anime anime) {
        return Character.builder()
                .id(1L)
                .name(name)
                .isVillain(true)
                .age(22)
                .anime(anime)
                .build();
    }

    public static Character createValidCharacter(String name, Boolean isVillain, Anime anime) {
        return Character.builder()
                .id(1L)
                .name(name)
                .isVillain(isVillain)
                .age(22)
                .anime(anime)
                .build();
    }

    public static Character createValidUpdatedAnime(String name, Anime anime) {
        return Character.builder()
                .id(1L)
                .name(name)
                .isVillain(true)
                .age(22)
                .anime(anime)
                .build();
    }
}
