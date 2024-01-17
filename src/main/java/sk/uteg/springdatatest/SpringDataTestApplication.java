package sk.uteg.springdatatest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = {"sk.uteg.springdatatest.api" , "sk.uteg.springdatatest.db" , "sk.uteg.springdatatest.service"})
public class SpringDataTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataTestApplication.class, args);
    }

}
