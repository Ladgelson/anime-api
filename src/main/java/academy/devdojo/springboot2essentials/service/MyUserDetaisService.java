package academy.devdojo.springboot2essentials.service;

import academy.devdojo.springboot2essentials.domain.MyUser;
import academy.devdojo.springboot2essentials.repository.MyUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetaisService implements UserDetailsService {
    private final MyUserRepository myUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return Optional.ofNullable(myUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
