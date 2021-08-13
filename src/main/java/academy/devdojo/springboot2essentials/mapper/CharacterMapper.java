package academy.devdojo.springboot2essentials.mapper;

import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.requests.CharacterPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CharacterMapper {
    public static final CharacterMapper INSTANCE = Mappers.getMapper(CharacterMapper.class);
    public abstract Character toCharacter(CharacterPostRequestBody character);
}
