package com.jseb23.NewPharmacie;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class NewPharmacieApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(NewPharmacieApplication.class, args);
	}
}

