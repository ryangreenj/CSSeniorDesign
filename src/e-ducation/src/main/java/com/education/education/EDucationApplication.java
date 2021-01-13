package com.education.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses={
		com.education.education.web.TestController.class,
		com.education.education.user.User.class
})
public class EDucationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EDucationApplication.class, args);
	}
}
