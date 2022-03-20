package ru.ezatoloka.mediaservice;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class MediaserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaserviceApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newSingleThreadExecutor();
	}

	@Bean
	public Jdk8Module jdk8Module() {
		return new Jdk8Module();
	}
}
