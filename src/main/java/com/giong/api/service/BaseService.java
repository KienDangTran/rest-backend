package com.giong.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

@Service
public abstract class BaseService<T, ID extends Serializable, R extends JpaRepository<T, ID>>
		implements GenericService<T, ID> {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageService messageService;

	@Autowired
	protected R repository;

	@Resource
	public void setRepository(R repository) {
		this.repository = repository;
	}
}
