package senla.course.service;

import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.course.dao.IUserDao;
import senla.course.entitys.UserEntity;


import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final IUserDao userDao;

    public MyUserDetailsService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String login) {

        UserEntity user = userDao.findUserByLogin(login);

        if (user == null) {
            throw new UsernameNotFoundException("User with login: " + login + " not found.");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new User(
                user.getLogin(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }

}
