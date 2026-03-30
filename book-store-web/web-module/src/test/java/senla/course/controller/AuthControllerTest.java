package senla.course.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import senla.course.LoginRequest;
import senla.course.security.JwtTokenProvider;
import senla.course.service.MyUserDetailsService;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@DisplayName("Test AuthController")
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    MyUserDetailsService myUserDetailsService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    MockMvc mockMvc;

    @InjectMocks
    AuthController authController;

    @Test
    @DisplayName("Given login When correct login Then get status ok")
    public void login_correctLogin_getStatusOk() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequest authLogin = new LoginRequest("loginTest", "passwordTest");
        String stringAuthLogin = objectMapper.writeValueAsString(authLogin);

        UserDetails user = new User("loginTest", "passwordTest", new HashSet<>());

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());

        when(authenticationManager.authenticate(any()))
                .thenReturn(usernamePasswordAuthenticationToken);

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(stringAuthLogin))
                .andExpect(status().isOk());
    }

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }
}