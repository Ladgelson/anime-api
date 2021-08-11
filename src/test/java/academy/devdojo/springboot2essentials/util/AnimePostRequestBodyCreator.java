package academy.devdojo.springboot2essentials.util;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {
    public static AnimePostRequestBody createAnimeToBeSaved() {
        return AnimePostRequestBody
                .builder()
                .name("Nanatsu no Taizai")
                .build();
    }
}
