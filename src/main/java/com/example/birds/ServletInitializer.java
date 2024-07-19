package com.example.birds;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ServletInitializer class.
 */
@SuppressWarnings("unused")
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * configure method.
	 * @param application {@link SpringApplicationBuilder}
	 * @return a {@link SpringApplicationBuilder} object
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BirdsApplication.class);
	}

}
