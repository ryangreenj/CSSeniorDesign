package com.education.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackageClasses={
		com.education.education.web.TestController.class,
		com.education.education.user.User.class,
		com.education.education.authentication.SecurityConfigurer.class
})
@EnableMongoRepositories(basePackageClasses = {
		com.education.education.user.repositories.UserRepository.class})
public class EDucationApplication {

	public static void main(String[] args) {
		SpringApplication.run(EDucationApplication.class, args);
	}
}
