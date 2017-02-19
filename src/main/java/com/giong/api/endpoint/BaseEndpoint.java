package com.giong.api.endpoint;

import com.giong.api.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public abstract class BaseEndpoint {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MessageService messageService;
}
