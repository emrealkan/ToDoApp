package com.example;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.iyzico.todo.controller.ToDoController;
import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.domain.User;
import com.iyzico.todo.service.ToDoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class TodoControllerTest {

	private MockMvc mockMvc;
	
    @InjectMocks
    ToDoController toDoController;

    @Mock
    ToDoServiceImpl toDoServiceImpl;
    
    @Mock
    Principal principal;

	@LocalServerPort
	private int port;

    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }
    
    @Test
    public void getToDoListView() throws Exception {
    	
    	List<ToDo> todos = new ArrayList<>();
        when(toDoServiceImpl.list(any(User.class))).thenReturn(todos);

        mockMvc.perform(
                get("/user/todolist")
        )
                .andExpect(model().attribute("todos", equalTo(todos)))
                .andExpect(view().name("web/content/todolist"));
    }
    
}
