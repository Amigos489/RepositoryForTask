package senla.course.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import senla.course.dao.UserDaoImpl;
import senla.course.entitys.RoleEntity;
import senla.course.entitys.UserEntity;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test MyUserDetailService")
public class MyUserDetailsServiceTest {

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    MyUserDetailsService userDetailsService;

    @Test
    @DisplayName("Given load user by username When correct login Then not throw exception")
    public void loadUserByUsername_correctLogin_notThrowException() {

        UserEntity userForTest = new UserEntity();

        String correctLogin = "CorrectLogin";

        userForTest.setLogin(correctLogin);

        Set<RoleEntity> roleEntities = new HashSet<>();
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName("ADMIN");
        roleEntities.add(roleAdmin);

        Set<UserEntity> userEntities = new HashSet<>();
        userEntities.add(userForTest);

        userForTest.setRoles(roleEntities);

        when(userDao.findUserByLogin("CorrectLogin")).thenReturn(userForTest);

        Assertions.assertDoesNotThrow(() -> userDetailsService.loadUserByUsername(correctLogin));
    }

    @Test
    @DisplayName("Given load user by username When user with specified id not found Then throw exception UsernameNotFoundException")
    public void loadUserByUsername_withSpecifiedIdNotFound_throwUsernameNotFoundException() {

        when(userDao.findUserByLogin("IncorrectLogin")).thenReturn(null);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("IncorrectLogin"));
    }

}
