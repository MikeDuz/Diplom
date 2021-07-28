package skillbox.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skillbox.entity.User;
import skillbox.mapping.UserDetailsMapper;
import skillbox.repository.UserRepository;

@Service("userDetailServiceImpl")
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRep;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRep.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return UserDetailsMapper.userDetailMapper(user);
    }
}
