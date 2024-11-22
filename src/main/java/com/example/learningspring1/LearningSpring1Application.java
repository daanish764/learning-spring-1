package com.example.learningspring1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearningSpring1Application {

    public static void main(String[] args) {
        SpringApplication.run(LearningSpring1Application.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext applicationContext) {
//        return args -> {
//            //System.out.println("Inspecting Spring Boot beans");
//            String[] beans = applicationContext.getBeanDefinitionNames();
//
//            for(String b : beans) {
//               //System.out.println(b);
//            }
//
//
//        };
//
//    }

}



/*

docker run --hostname=ba84ad96b7c2 --user=pgadmin --mac-address=02:42:ac:11:00:02 --env=PGADMIN_DEFAULT_EMAIL=admin@gmail.com --env=PGADMIN_DEFAULT_PASSWORD=admin --env=PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin --env=PYTHONPATH=/pgadmin4 --volume=/var/lib/pgadmin --network=bridge --workdir=/pgadmin4 -p 5050:443 --restart=no --runtime=runc -d dpage/pgadmin4:latest

 */