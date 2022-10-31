package com.example.ranunstrgen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ranunstrgen.model.StringTask;
import com.example.ranunstrgen.service.StringTaskBaseService;

@RequestMapping("/taskdb")
@RestController
public class StringTaskBaseController {
	private final StringTaskBaseService service;
	
	@Autowired
	public StringTaskBaseController(StringTaskBaseService service) {
		this.service = service;
 	}
	
	@PostMapping("/tasks/newtask")
	public Long receiveNewJob(@RequestBody StringTask task) {
		return service.receiveNewJob(task);
	}
}
