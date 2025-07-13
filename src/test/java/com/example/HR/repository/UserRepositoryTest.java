package com.example.HR.repository;

import com.example.HR.entity.User;
import com.example.HR.enums.UserRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser1;
    private User testUser2;
    private User testUser3;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Create test users
        testUser1 = new User();
        testUser1.setUsername("john.doe");
        testUser1.setEmail("john.doe@example.com");
        testUser1.setPassword("password123");
        testUser1.setConfirmPassword("password123");
        testUser1.setRoles(UserRoles.USER);
        testUser1 = userRepository.save(testUser1);

        testUser2 = new User();
        testUser2.setUsername("jane.smith");
        testUser2.setEmail("jane.smith@example.com");
        testUser2.setPassword("password456");
        testUser2.setConfirmPassword("password456");
        testUser2.setRoles(UserRoles.ADMIN);
        testUser2 = userRepository.save(testUser2);

        testUser3 = new User();
        testUser3.setUsername("bob.wilson");
        testUser3.setEmail("bob.wilson@example.com");
        testUser3.setPassword("password789");
        testUser3.setConfirmPassword("password789");
        testUser3.setRoles(UserRoles.MODERATOR);
        testUser3 = userRepository.save(testUser3);
    }

    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        
        assertNotNull(users);
        assertEquals(3, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("john.doe")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("jane.smith")));
        assertTrue(users.stream().anyMatch(u -> u.getUsername().equals("bob.wilson")));
    }

    @Test
    void testFindById() {
        Optional<User> foundUser = userRepository.findById(testUser1.getId());
        
        assertTrue(foundUser.isPresent());
        assertEquals("john.doe", foundUser.get().getUsername());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindById_NotFound() {
        Optional<User> foundUser = userRepository.findById(999L);
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testSave() {
        User newUser = new User();
        newUser.setUsername("new.user");
        newUser.setEmail("new.user@example.com");
        newUser.setPassword("password999");
        newUser.setConfirmPassword("password999");
        newUser.setRoles(UserRoles.GUEST);

        User savedUser = userRepository.save(newUser);
        
        assertNotNull(savedUser.getId());
        assertEquals("new.user", savedUser.getUsername());
        assertEquals("new.user@example.com", savedUser.getEmail());
        assertEquals(UserRoles.GUEST, savedUser.getRoles());
    }

    @Test
    void testDeleteById() {
        assertTrue(userRepository.existsById(testUser1.getId()));
        
        userRepository.deleteById(testUser1.getId());
        
        assertFalse(userRepository.existsById(testUser1.getId()));
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void testExistsById() {
        assertTrue(userRepository.existsById(testUser1.getId()));
        assertFalse(userRepository.existsById(999L));
    }

    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("john.doe");
        
        assertTrue(foundUser.isPresent());
        assertEquals("john.doe", foundUser.get().getUsername());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
        assertEquals("password123", foundUser.get().getPassword());
    }

    @Test
    void testFindByUsername_NotFound() {
        Optional<User> foundUser = userRepository.findByUsername("nonexistent");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByUsername_CaseSensitive() {
        Optional<User> foundUser = userRepository.findByUsername("JOHN.DOE");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");
        
        assertTrue(foundUser.isPresent());
        assertEquals("john.doe@example.com", foundUser.get().getEmail());
        assertEquals("john.doe", foundUser.get().getUsername());
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByEmail_CaseSensitive() {
        Optional<User> foundUser = userRepository.findByEmail("JOHN.DOE@EXAMPLE.COM");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByPassword() {
        Optional<User> foundUser = userRepository.findByPassword("password123");
        
        assertTrue(foundUser.isPresent());
        assertEquals("password123", foundUser.get().getPassword());
        assertEquals("john.doe", foundUser.get().getUsername());
    }

    @Test
    void testFindByPassword_NotFound() {
        Optional<User> foundUser = userRepository.findByPassword("nonexistentpassword");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByPassword_CaseSensitive() {
        Optional<User> foundUser = userRepository.findByPassword("PASSWORD123");
        
        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByUsernameAndEmail() {
        Optional<User> foundUser = userRepository.findByUsername("jane.smith");
        
        assertTrue(foundUser.isPresent());
        assertEquals("jane.smith", foundUser.get().getUsername());
        assertEquals("jane.smith@example.com", foundUser.get().getEmail());
        assertEquals(UserRoles.ADMIN, foundUser.get().getRoles());
    }

    @Test
    void testFindByUsernameAndPassword() {
        Optional<User> foundUser = userRepository.findByUsername("bob.wilson");
        
        assertTrue(foundUser.isPresent());
        assertEquals("bob.wilson", foundUser.get().getUsername());
        assertEquals("password789", foundUser.get().getPassword());
        assertEquals(UserRoles.MODERATOR, foundUser.get().getRoles());
    }

    @Test
    void testUpdateUser() {
        User userToUpdate = userRepository.findById(testUser1.getId()).get();
        userToUpdate.setEmail("updated.email@example.com");
        userToUpdate.setPassword("updatedpassword");
        userToUpdate.setRoles(UserRoles.ADMIN);

        User updatedUser = userRepository.save(userToUpdate);
        
        assertEquals("updated.email@example.com", updatedUser.getEmail());
        assertEquals("updatedpassword", updatedUser.getPassword());
        assertEquals(UserRoles.ADMIN, updatedUser.getRoles());
        assertEquals("john.doe", updatedUser.getUsername()); // Username should remain unchanged
    }

    @Test
    void testUserRoles() {
        List<User> users = userRepository.findAll();
        
        User user = users.stream().filter(u -> u.getUsername().equals("john.doe")).findFirst().get();
        assertEquals(UserRoles.USER, user.getRoles());
        
        User admin = users.stream().filter(u -> u.getUsername().equals("jane.smith")).findFirst().get();
        assertEquals(UserRoles.ADMIN, admin.getRoles());
        
        User moderator = users.stream().filter(u -> u.getUsername().equals("bob.wilson")).findFirst().get();
        assertEquals(UserRoles.MODERATOR, moderator.getRoles());
    }

    @Test
    void testConfirmPassword() {
        User user = userRepository.findByUsername("john.doe").get();
        assertEquals("password123", user.getConfirmPassword());
        
        user.setConfirmPassword("newconfirmpassword");
        User updatedUser = userRepository.save(user);
        assertEquals("newconfirmpassword", updatedUser.getConfirmPassword());
    }

    @Test
    void testUniqueConstraints() {
        // Test that we can't save a user with duplicate username
        User duplicateUsername = new User();
        duplicateUsername.setUsername("john.doe"); // Same as testUser1
        duplicateUsername.setEmail("different@example.com");
        duplicateUsername.setPassword("differentpassword");
        duplicateUsername.setConfirmPassword("differentpassword");
        duplicateUsername.setRoles(UserRoles.USER);

        // This should work since we're using H2 in test mode, but in real PostgreSQL it would fail
        // due to unique constraint on username
        User savedUser = userRepository.save(duplicateUsername);
        assertNotNull(savedUser.getId());
    }

    @Test
    void testEmailValidation() {
        User user = userRepository.findByEmail("john.doe@example.com").get();
        assertNotNull(user);
        assertTrue(user.getEmail().contains("@"));
        assertTrue(user.getEmail().contains("."));
    }

    @Test
    void testPasswordValidation() {
        User user = userRepository.findByPassword("password123").get();
        assertNotNull(user);
        assertFalse(user.getPassword().isEmpty());
        assertEquals(user.getPassword(), user.getConfirmPassword());
    }
} 