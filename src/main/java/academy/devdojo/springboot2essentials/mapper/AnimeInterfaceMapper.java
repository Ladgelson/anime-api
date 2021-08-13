package academy.devdojo.springboot2essentials.mapper;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import academy.devdojo.springboot2essentials.requests.CharacterPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnimeInterfaceMapper {
    AnimeInterfaceMapper INSTANCE = Mappers.getMapper( AnimeInterfaceMapper.class );

    @Mapping(source = "name", target = "name")
    Anime postAnimeToAnime(AnimePostRequestBody anime);

    @Mapping(source = "name", target = "name")
    Anime putAnimeToAnime(AnimePutRequestBody anime);

    Character postCharacterToCharacter(CharacterPostRequestBody character);
}
