package com.example.ranunstrgen.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ranunstrgen.model.StringTask;
import com.example.ranunstrgen.service.StringTaskBaseService;
import com.example.ranunstrgen.service.StringTaskService;

@RestController
public class StringTaskController {
	private final StringTaskService service;
	private final StringTaskBaseService baseService;

	@Autowired
	public StringTaskController(StringTaskService service, StringTaskBaseService baseService) {
		this.service = service;
		this.baseService = baseService;
	}

	@PostMapping("/tasks/newtask")
	ResponseEntity<Object> receiveNewJob(@RequestBody StringTask task) {
		if (service.areParametersCorrect(task)) {
			try {
				service.receiveNewJob(task);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		service.printJobRecevingMessage(task);
		return new ResponseEntity<Object>(service.getHttpStatus(task));
	}

	@GetMapping("/tasks")
	List<StringTask> getAllTasks() {
		return baseService.getAllTasks();
	}

	@GetMapping("/tasks/{id}")
	@ResponseBody
	ResponseEntity<Object> grabResults(@PathVariable Long id) throws FileNotFoundException {
		return service.grabResults(id);
	}
}
