package com.example.demo_ibm_mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo_ibm_mq")
public class DemoIbmMqApplication implements CommandLineRunner{

	@Autowired
	IBMMQProduer producer;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoIbmMqApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		producer.send("Hi From Rohan");
	}
	
}
