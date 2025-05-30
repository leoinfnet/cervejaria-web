package br.com.acme.cervejariaacme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CervejariaAcmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CervejariaAcmeApplication.class, args);
	}

}
