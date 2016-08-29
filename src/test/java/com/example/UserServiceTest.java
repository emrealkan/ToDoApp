package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.willReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.iyzico.todo.domain.Role;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.repository.UserRepository;
import com.iyzico.todo.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Test
	public void should_find_user_by_id() {
		// Given
		User user = prepareUserTestData();
		willReturn(user).given(userRepository).findOne(400L);

		// When
		User findUser = userServiceImpl.findById(400L);

		// Then
		assertThat(findUser).isEqualToComparingFieldByField(user);
		assertEquals("failure - expected id attribute match", findUser.getId(), user.getId());
		assertNotNull(findUser.getName());
		assertNotNull(findUser.getUserName());
	}
	
	@Test
	public void should_find_user_by_username() {
		// Given
		User user = prepareUserTestData();
		willReturn(user).given(userRepository).findByEmail("emre@test.com");
		
		// When
		User findUser = userServiceImpl.findByEmail("emre@test.com");

		// Then
		assertThat(findUser).isEqualToComparingFieldByField(user);
		assertEquals("failure - expected id attribute match", findUser.getId(), user.getId());
		assertNotNull(findUser.getName());
		assertNotNull(findUser.getUserName());
	}
	
	@Test
    public void should_create_user() {
        // Given
		User user = new User();
		user.setEmail("Testemre@test.com");
		user.setName("Testemre");
		user.setPassword("password");
		user.setUserName("Testemre");
		user.setId(263L);
		user.setRole(Role.USER);
		
    	willReturn(263L).given(userRepository).save(user);
    	willReturn(user).given(userRepository).findOne(263L);

        // When
        Boolean createUser = userServiceImpl.createUser(user.getName(), user.getEmail(), user.getPassword());
        
        // Then
        assertEquals(Boolean.TRUE, createUser);
        assertNotNull(user.getEmail());
    }
	
	private User prepareUserTestData(){
		User user = new User();
		user.setEmail("emre@test.com");
		user.setName("emre");
		user.setPassword("password");
		user.setUserName("emre");
		user.setId(26L);
		user.setRole(Role.USER);
		return user;
    }

}
