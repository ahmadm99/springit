package com.ahmad.springit;

import com.ahmad.springit.domain.Comment;
import com.ahmad.springit.domain.Link;
import com.ahmad.springit.repository.CommentRepository;
import com.ahmad.springit.repository.LinkRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
	}

	@Bean
	PrettyTime prettyTime(){
		return new PrettyTime();
	}
// COMMANDLINERUNNER DOES NOT WORK WITHOUT @BEAN...
}
