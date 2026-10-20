package com.example.RecantoDosPassaros.Pck_Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Recando_dos_Passaros")
public class RecantoDosPassarosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecantoDosPassarosApplication.class, args);
	}
}