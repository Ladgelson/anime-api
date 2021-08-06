package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.Anime;
import academy.devdojo.springboot2essentials.exception.BadRequestException;
import academy.devdojo.springboot2essentials.mapper.AnimeInterfaceMapper;
import academy.devdojo.springboot2essentials.repository.AnimeRepository;
import academy.devdojo.springboot2essentials.requests.AnimePostRequestBody;
import academy.devdojo.springboot2essentials.requests.AnimePutRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findById(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime ID not found"));
    }

    @Transactional
    public Anime save(AnimePostRequestBody body) {
        return animeRepository.save(AnimeInterfaceMapper.INSTANCE.postAnimeToAnime(body));
    }

    public void delete(long id) {
        animeRepository.delete(findById(id));
    }

    public void replace(AnimePutRequestBody body) {
        findById(body.getId());
        animeRepository.save(AnimeInterfaceMapper.INSTANCE.putAnimeToAnime(body));
    }
}
