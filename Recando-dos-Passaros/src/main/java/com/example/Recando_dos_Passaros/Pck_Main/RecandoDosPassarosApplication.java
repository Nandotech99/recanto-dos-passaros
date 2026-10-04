package com.example.Recando_dos_Passaros.Pck_Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.Recando_dos_Passaros")
public class RecandoDosPassarosApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecandoDosPassarosApplication.class, args);
	}
}