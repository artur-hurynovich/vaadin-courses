package com.gpsolutions.vaadincourses;

import com.gpsolutions.vaadincourses.util.StringListField;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VaadinCoursesApplication {

	@Bean
	public StringListField getStringListField() {
		return new StringListField();
	}

	public static void main(String[] args) {
		SpringApplication.run(VaadinCoursesApplication.class, args);
	}

}

