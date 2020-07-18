package br.com.haagsma.productcontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"br.com.haagsma.productcontrol"})
@SpringBootApplication
public class ProductControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductControlApplication.class, args);
	}

}
