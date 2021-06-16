package com.ahmad.springit;

import com.ahmad.springit.domain.Comment;
import com.ahmad.springit.domain.Link;
import com.ahmad.springit.repository.CommentRepository;
import com.ahmad.springit.repository.LinkRepository;
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
// COMMANDLINERUNNER DOES NOT WORK WITHOUT @BEAN... CURRENTLY COMMENTED OUT SO IT DOES NOT WORK
//	@Bean
	CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
		return args -> {
			Link link = new Link("This is my link", "https://google.com");
			linkRepository.save(link);

			Comment comment = new Comment("First comment", link);
			commentRepository.save(comment);

			link.addComment(comment);

		};
	}

}
