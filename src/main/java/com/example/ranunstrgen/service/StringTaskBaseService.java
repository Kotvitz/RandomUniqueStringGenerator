package com.example.ranunstrgen.service;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ranunstrgen.model.StringTask;

@Service
public class StringTaskBaseService {
	
	private static Logger logger = LogManager.getLogger(StringTaskBaseService.class);

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
}
