//package com.example.HR.serviceTest;
//
//import com.example.HR.converter.UserConverter;
//import com.example.HR.dto.user.UserRequestDTO;
//import com.example.HR.entity.user.User;
//import com.example.HR.enums.UserRoles;
//import com.example.HR.exception.NotFoundException;
//import com.example.HR.repository.User.UserRepository;
//import com.example.HR.service.implement.UserServiceImpl;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//    @Mock
//    private UserConverter userConverter;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    private User user;
//    private UserRequestDTO userDTO;
//
//    @BeforeEach
//    public void init(){
//        user = new User();
//        user.setId(1L);
//        user.setEmail("ilkin2006@gmail.com");
//        user.setPassword("123456");
//        user.setUsername("ilkin6666");
//        user.setConfirmPassword("123456");
//        user.setRoles(UserRoles.USER);
//
//        userDTO = new UserRequestDTO();
//        userDTO.setId(1L);
//        userDTO.setEmail("ilkin2006@gmail.com");
//        userDTO.setPassword("123456");
//        userDTO.setUsername("ilkin6666");
//        userDTO.setConfirmPassword("123456");
//        userDTO.setRoles(UserRoles.USER);
//    }
//
//    @Test
//    public void testSaveUser() throws IOException {
//
//        when(userConverter.dtoToEntity(any(UserRequestDTO.class))).thenReturn(user);
//        when(userConverter.entityToDto(any(User.class))).thenReturn(userDTO);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserRequestDTO result = userService.save(userDTO);
//
//        assertNotNull(result);
//        assertEquals("ilkin6666", result.getUsername());
//        assertEquals("ilkin2006@gmail.com", result.getEmail());
//
//        verify(userConverter, times(1)).dtoToEntity(userDTO);
//        verify(userConverter, times(1)).entityToDto(user);
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    public void testFindByUsername() {
//
//        when(userRepository.findByUsername("ilkin6666")).thenReturn(Optional.of(user));
//        when(userConverter.entityToDto(user)).thenReturn(userDTO);
//
//        Optional<UserRequestDTO> result = userService.getByUsername("ilkin6666");
//
//        assertTrue(result.isPresent());
//        assertEquals("ilkin6666", result.get().getUsername());
//        assertEquals("ilkin2006@gmail.com", result.get().getEmail());
//
//        verify(userRepository, times(1)).findByUsername("ilkin6666");
//        verify(userConverter, times(1)).entityToDto(user);
//    }
//
//    @Test
//    public void testGetAllUsers() throws Exception {
//        User user1 = new User();
//        user1.setId(1L);
//        user1.setEmail("ilkin2006@gmail.com");
//        user1.setPassword("123456");
//        user1.setUsername("ilkin6666");
//        user1.setConfirmPassword("123456");
//        user1.setRoles(UserRoles.USER);
//
//        User user2 = new User();
//        user2.setId(2L);
//        user2.setEmail("test2@gmail.com");
//        user2.setPassword("abcdef");
//        user2.setUsername("testuser2");
//        user2.setConfirmPassword("abcdef");
//        user2.setRoles(UserRoles.ADMIN);
//
//        UserRequestDTO userDTO1 = new UserRequestDTO();
//        userDTO1.setId(1L);
//        userDTO1.setEmail("ilkin2006@gmail.com");
//        userDTO1.setPassword("123456");
//        userDTO1.setUsername("ilkin6666");
//        userDTO1.setConfirmPassword("123456");
//        userDTO1.setRoles(UserRoles.USER);
//
//        UserRequestDTO userDTO2 = new UserRequestDTO();
//        userDTO2.setId(2L);
//        userDTO2.setEmail("test2@gmail.com");
//        userDTO2.setPassword("abcdef");
//        userDTO2.setUsername("testuser2");
//        userDTO2.setConfirmPassword("abcdef");
//        userDTO2.setRoles(UserRoles.ADMIN);
//
//        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
//        when(userConverter.entityListToDtoList(List.of(user1, user2))).thenReturn(List.of(userDTO1, userDTO2));
//
//        var result = userService.getAll();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals("ilkin6666", result.get(0).getUsername());
//        assertEquals("testuser2", result.get(1).getUsername());
//
//        verify(userRepository, times(1)).findAll();
//        verify(userConverter, times(1)).entityListToDtoList(List.of(user1, user2));
//    }
//
//    @Test
//    public void userUpdateTest(){
//        UserRequestDTO newUser = new UserRequestDTO();
//        newUser.setUsername("huseyn");
//        newUser.setPassword("hus123");
//
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(userConverter.entityToDto(user)).thenReturn(userDTO);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserRequestDTO savedUserDto = userService.update(1L,newUser);
//
//        Assertions.assertThat(savedUserDto).isNotNull();
//    }
//
//    @Test
//    public void deleteUserByIdTest(){
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//
//        assertAll(() -> userService.deleteById(1L));
//    }
//
//    @Test
//    public void getUserByEmailTest(){
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
//
//        assertAll(() -> userService.getByEmail("ilkin2006@gmail.com"));
//    }
//
//    @Test
//    public void getUserByEmailTest_ReturnsNotFound(){
//        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class,() -> userService.getByEmail(user.getEmail()));
//    }
//
//    @Test
//    public void getUserByUsernameTest(){
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
//
//        assertAll(() -> userService.getByUsername("ilkin6666"));
//    }
//
//    @Test
//    public void getUserByUsernameTest_ReturnsNotFound(){
//        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class,() -> userService.getByUsername(user.getUsername()));
//
//    }
//
//    @Test
//    public void getUserByPasswordTest(){
//        when(userRepository.findByPassword(user.getPassword())).thenReturn(Optional.of(user));
//
//        assertAll(() -> userService.getByPassword("123456"));
//    }
//
//    @Test
//    public void getUserByPasswordTest_ReturnsNotFound(){
//        when(userRepository.findByPassword(user.getPassword())).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class,() -> userService.getByPassword(user.getPassword()));
//    }
//}
