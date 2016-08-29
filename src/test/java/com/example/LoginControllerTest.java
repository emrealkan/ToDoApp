package com.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iyzico.todo.StartApplication;
import com.iyzico.todo.controller.LoginController;
import com.iyzico.todo.model.UserSignUpFormModel;
import com.iyzico.todo.repository.UserRepository;
import com.iyzico.todo.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {StartApplication.class})
@WebAppConfiguration
public class LoginControllerTest {
	
	private MockMvc mockMvc;

    @InjectMocks
    LoginController loginController;
    
    @Mock
	private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }
    
    @Test
    public void register_user() throws Exception {
        mockMvc.perform(
                post("/signup")
                        .param("userName", "test_emre")
                        .param("email", "test_emre@gmail.com")
                        .param("password", "emre1234")
                        .param("passwordRepeated", "john123")
        )
                .andExpect(redirectedUrl("redirect:/user/todolist.html"));
        
        ArgumentCaptor<UserSignUpFormModel> registrationInfoArgumentCaptor = ArgumentCaptor.forClass(UserSignUpFormModel.class);
        verify(userServiceImpl).createUser("test_emre","test_emre@gmail.com","emre1234");

        UserSignUpFormModel registrationInfo = registrationInfoArgumentCaptor.getValue();
        assertEquals(registrationInfo.getUserName(),"test_emre");
        assertEquals(registrationInfo.getEmail(), "test_emre@gmail.com");
        assertEquals(registrationInfo.getPassword(), "emre1234");
    }

}
