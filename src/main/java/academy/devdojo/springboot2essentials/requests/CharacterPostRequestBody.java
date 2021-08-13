package academy.devdojo.springboot2essentials.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CharacterPostRequestBody {
    private String name;
    private int age;
    private Boolean isVillain;
    private Long anime;
}
