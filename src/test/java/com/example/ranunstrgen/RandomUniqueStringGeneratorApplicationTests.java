package com.example.ranunstrgen;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.example.ranunstrgen.model.StringTask;
import com.example.ranunstrgen.service.StringTaskBaseService;
import com.example.ranunstrgen.service.StringTaskService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RandomUniqueStringGeneratorApplicationTests {

	@Mock
	RestTemplate restTemplate;

	@Mock
	ResponseEntity<?> response;
	
    @Mock
    EntityManager entityManager;
    
    @InjectMocks
    StringTaskService service;
    
    @InjectMocks
    StringTaskBaseService baseService;
    
	@BeforeEach
	public void init() {
		ReflectionTestUtils.setField(service, "uriReceiveNewJob", "http://localhost:8020/taskdb/tasks/newtask");
		ReflectionTestUtils.setField(service, "uriCheckRunningJobs", "http://localhost:8020/taskdb/tasks/");
		ReflectionTestUtils.setField(service, "uriGrabResults", "http://localhost:8020/taskdb/tasks/results/");
	}
	
	@Test
	void testReceiveNewJob() {
		when(restTemplate.postForObject(anyString(), Mockito.any(StringTask.class), Mockito.eq(Long.class))).thenReturn(1L);
		StringTask task = createTask();
		try {
			Long taskId = service.receiveNewJob(task);
			assertEquals(1L, taskId);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGrabResults() {
		when(restTemplate.getForObject(anyString(), Mockito.eq(StringTask.class))).thenReturn(createTask());
		try {
			ResponseEntity<Object> entity = service.grabResults(1L);
			assertEquals(ResponseEntity.status(HttpStatus.OK).build().getStatusCodeValue(), entity.getStatusCodeValue());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private StringTask createTask() {
		StringTask task = new StringTask();
		char[] cs = {'#', 'B', '2', 't'};
		task.setId(1L);
		task.setMinLength(1);
		task.setMaxLength(4);
		task.setNumberOfStrings(3L);
		task.setPossibleChars(cs);
		return task;
	}

}
