package com.giong.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class MessageService implements MessageSourceAware {

	@Autowired
	private MessageSource messageSource;

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessages(String code) {
		return this.getMessages(code, new Object[]{});
	}

	public String getMessages(String code, Object... param) {
		final String msg = this.messageSource.getMessage(code, param, LocaleContextHolder.getLocale());
		return msg;
	}
}
