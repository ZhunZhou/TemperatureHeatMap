package com.temperature_mapping.votage_reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class VotageReaderApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(VotageReaderApplication.class, args);
		ArduinoDataController myProcess = applicationContext.getBean(ArduinoDataController.class);
		myProcess.run();

	}

}
