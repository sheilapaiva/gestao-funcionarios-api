package com.api.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoApplication.class, args);
	}

}
