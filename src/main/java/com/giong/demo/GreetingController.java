package com.giong.demo;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Kien on 14-Aug-16.
 */

@RestController
@RequestMapping("/demo")
public class GreetingController {
	private static final String template = "Greeting, %s!!!!!!!!!!!!!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Greeting sayHello(
		@RequestParam(value = "name", required = false, defaultValue = "Stranger") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
}

class Greeting {

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}

