package com.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.iyzico.todo.domain.ToDo;
import com.iyzico.todo.repository.ToDoRepository;
import com.iyzico.todo.service.ToDoServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TodoServiceTest {
	
	@Mock
    private ToDoRepository todoRepository;

    @InjectMocks
    private ToDoServiceImpl toDoServiceImpl;

    @Test
    public void should_find_todo_by_id() {
    	//Given
    	ToDo todo = prepareTodoTestData();
    	willReturn(todo).given(todoRepository).findOne(30L);

        // When
    	ToDo findTodo = toDoServiceImpl.findOne(30L);
        
        // Then
        assertThat(findTodo).isEqualToComparingFieldByField(todo);
    	
    }
    
    @Test
    public void should_create_a_todo() {
        // Given
    	ToDo todo = prepareTodoTestData();
    	willReturn(30L).given(todoRepository).save(todo);
    	willReturn(todo).given(todoRepository).findOne(30L);

        // When
        toDoServiceImpl.createToDo(todo);
        ToDo createdTodo = toDoServiceImpl.findOne(todo.getId());

        // Then
        assertThat(createdTodo).isEqualToComparingFieldByField(todo);
        verify(todoRepository).save(todo);
    }
    
    @Test
    public void should_delete_todo() {
        // Given
    	willReturn(true).given(todoRepository).exists(30L);
        
        // When
        toDoServiceImpl.deleteToDoById(30L);

        // Then
        verify(todoRepository).delete(30L);
    }
    
   
    private ToDo prepareTodoTestData(){
    	ToDo todo = new ToDo();
    	todo.setTitle("title");
    	todo.setId(30L);
    	todo.setSubTitle("subtitle");
    	todo.setContent("content");
    	todo.setStartDate(null);
    	todo.setEndDate(null);
    	return todo;
    }
        

}
