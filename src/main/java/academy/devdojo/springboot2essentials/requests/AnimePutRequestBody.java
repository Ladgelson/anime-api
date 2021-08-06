package academy.devdojo.springboot2essentials.requests;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AnimePutRequestBody {
    private Long id;
    private String name;
}
