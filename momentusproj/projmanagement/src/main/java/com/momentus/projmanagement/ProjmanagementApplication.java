package com.momentus.projmanagement;

import com.momentus.corefw.auth.SecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.momentus", // your main app
                "com.momentus.corefw",
                "com.momentus.foundation" // package inside your JAR
        })
@EntityScan(basePackages = {"com.momentus.projmanagement", "com.momentus.foundation"})
@EnableJpaRepositories(basePackages = {"com.momentus.projmanagement", "com.momentus.foundation"})
@Import(SecurityConfig.class)
public class ProjmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjmanagementApplication.class, args);
	}

}
