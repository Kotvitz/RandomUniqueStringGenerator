package com.example.ranunstrgen.service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

	@Transactional
	public Long receiveNewJob(StringTask task) {
		try {
			entityManager.persist(task);
			logger.info("String permutation was correctly recorded in the database.");
			return task.getId();
		} catch (Exception e) {
			logger.error("An error occurred while trying to record the string permutation"
					+ " to the database.\n" + e.getMessage());
			return null;
		}
	}
}
