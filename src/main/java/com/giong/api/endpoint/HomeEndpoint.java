package com.giong.api.endpoint;

import com.giong.api.constant.Endpoint;
import com.giong.api.dto.ResponseWrapper;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = Endpoint.HOME)
public class HomeEndpoint extends BaseEndpoint {

	@RequestMapping(method = RequestMethod.GET)
	protected @ResponseBody ResponseWrapper handleRequestInternal() throws Exception {

		final Date date = new Date();
		final DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

		final String formattedDate = dateFormat.format(date);
		final ResponseWrapper response = new ResponseWrapper();
		response.setResult(formattedDate);
		response.setStatus(HttpStatus.OK.value());

		return response;
	}

}
