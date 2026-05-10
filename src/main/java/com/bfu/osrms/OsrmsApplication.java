package com.bfu.osrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MAIN APPLICATION CLASS
 * Online Student Result Management System (OSRMS)
 * Bright Future University - Kigali, Rwanda
 *
 * Architecture: MVC (Model-View-Controller)
 * - Model: Entity classes in model package
 * - View: Thymeleaf templates in resources/templates
 * - Controller: Request handlers in controller package
 */
@SpringBootApplication
public class OsrmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(OsrmsApplication.class, args);
    }
}
