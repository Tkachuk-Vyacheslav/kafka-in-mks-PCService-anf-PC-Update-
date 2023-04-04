package org.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
/**
 * ClientProfileService!
 *
 */
@SpringBootApplication
@EnableWebMvc
@Slf4j
public class MainApp
{
    public static void main(String[] args) {
        log.debug("starting ClientProfileService");
        SpringApplication.run(MainApp.class, args);
    }
}
