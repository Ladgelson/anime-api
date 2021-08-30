package academy.devdojo.springboot2essentials.repository;

import academy.devdojo.springboot2essentials.domain.Character;
import academy.devdojo.springboot2essentials.domain.MyUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    MyUser findByUsername(String name);
}
