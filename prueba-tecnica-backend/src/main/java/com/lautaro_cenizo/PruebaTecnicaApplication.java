package com.lautaro_cenizo;

import com.lautaro_cenizo.entity.rol.Role;
import com.lautaro_cenizo.entity.rol.RoleRepository;
import com.lautaro_cenizo.security.auth.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PruebaTecnicaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaTecnicaApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(
			RoleRepository roleRepository,
			AuthenticationService authenticationService // Agregar esto
	) {
		return args -> {
			// Crear roles
			String[] roles = {"USER", "ADMIN", "MODERATOR"};
			for (String role : roles) {
				if (roleRepository.findByName(role).isEmpty()) {
					roleRepository.save(Role.builder().name(role).build());
				}
			}

			// Crear el admin inicial
			authenticationService.createInitialAdmin();
		};
	}
}
