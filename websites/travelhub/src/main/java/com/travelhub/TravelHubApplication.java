package com.travelhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Entry point. Extends SpringBootServletInitializer so the packaged WAR
 * boots correctly under an external Tomcat 9 (Servlet 4.0 / javax.*)
 * instead of only via `java -jar`.
 */
@SpringBootApplication
public class TravelHubApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TravelHubApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TravelHubApplication.class, args);
    }
}
