package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Nanatsu no Taizai")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Nanatsu no Taizai")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("One push man")
                .id(1L)
                .build();
    }
}
