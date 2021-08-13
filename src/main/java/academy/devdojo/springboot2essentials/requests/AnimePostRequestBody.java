package academy.devdojo.springboot2essentials.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePostRequestBody {
    private Long id;
    private String name;
}

