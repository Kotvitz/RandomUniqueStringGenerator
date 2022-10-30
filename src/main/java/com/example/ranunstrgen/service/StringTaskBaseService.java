package com.example.ranunstrgen.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ranunstrgen.model.StringTask;

@Service
public class StringTaskBaseService {
	
	private final EntityManager entityManager;

	@Autowired
	public StringTaskBaseService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public StringTask getTask(Long id) {
		StringTask stringTask =  entityManager.createQuery("FROM string_task st WHERE st.id = :id", StringTask.class)
				.setParameter("id", id).getSingleResult();
		return stringTask;
	}
	
	public List<StringTask> getAllTasks() {
		List<StringTask> taskList = entityManager.createQuery("FROM string_task st WHERE", StringTask.class).getResultList();
		return taskList;
	}
}
