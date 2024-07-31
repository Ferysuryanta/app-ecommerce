package com.backend.controller;

import com.backend.auth.AuthController;
import com.backend.dto.AuthenticationRequest;
import com.backend.dto.RefreshTokenRequest;
import com.backend.dto.UserDto;
import com.backend.model.User;
import com.backend.model.UserRoles;
import com.backend.repo.UserRepository;
import com.backend.repo.UserRoleRepository;
import com.backend.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private AuthController authController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test_Dummy")
    void testAuthenticate() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@gmail.com");
        request.setPassword("password123");

        var userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("mockAccessToken");
        when(jwtUtil.generateRefreshToken(any(UserDetails.class))).thenReturn("mockRefreshToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("mockAccessToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("mockRefreshToken"));

        verify(authenticationManager).authenticate(any());
        verify(userDetailsService).loadUserByUsername(anyString());
        verify(jwtUtil).generateAccessToken(any(UserDetails.class));
        verify(jwtUtil).generateRefreshToken(any(UserDetails.class));
    }

    @Test
    @DisplayName("TestRegisterSuccessfully")
    void testRegister() throws Exception {
        var userDto = new UserDto();
        userDto.setEmail("test@gmail.com");
        userDto.setPassword("password123");
        userDto.setFullName("Test User");
        userDto.setPhoneNumber("1234567890");

        var userRole = new UserRoles();
        userRole.setRoleName("Customer");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRoleRepository.findByRoleName(anyString())).thenReturn(userRole);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User registered successfully"));

        verify(passwordEncoder).encode(anyString());
        verify(userRoleRepository).findByRoleName(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRefreshToken() throws Exception {
        var refreshTokenRequest = new RefreshTokenRequest();
        refreshTokenRequest.setRefreshToken("mockRefreshToken");

        UserDetails userDetails = mock(UserDetails.class);
        when(jwtUtil.extractUsername(anyString())).thenReturn("test@example.com");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(jwtUtil.validateToken(anyString(), any(UserDetails.class))).thenReturn(true);
        when(jwtUtil.generateAccessToken(any(UserDetails.class))).thenReturn("newAccessToken");
        when(jwtUtil.generateRefreshToken(any(UserDetails.class))).thenReturn("newRefreshToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.refreshToken").value("newRefreshToken"));

        verify(jwtUtil).extractUsername(anyString());
        verify(userDetailsService).loadUserByUsername(anyString());
        verify(jwtUtil).validateToken(anyString(), any(UserDetails.class));
        verify(jwtUtil).generateAccessToken(any(UserDetails.class));
        verify(jwtUtil).generateRefreshToken(any(UserDetails.class));
    }
}
