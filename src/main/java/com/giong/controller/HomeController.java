package com.giong.controller;

import com.giong.dto.AbstractResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.util.Date;

/**
 * Handles requests for the application home page.
 */
@RestController
@RequestMapping(value = "/")
public class HomeController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	protected @ResponseBody AbstractResponse handleRequestInternal() throws Exception {

		final Date date = new Date();
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

		final String formattedDate = dateFormat.format(date);
		final AbstractResponse response = new AbstractResponse();
		response.setResult(formattedDate);
		response.setStatus(AbstractResponse.RESPONSE_STATUS_SUCCESS);

		return response;
	}

}
