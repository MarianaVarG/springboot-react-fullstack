package com.solera.backendcursojava;

import com.solera.backendcursojava.models.reponses.UserRest;
import com.solera.backendcursojava.security.AppProperties;
import com.solera.backendcursojava.share.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing // to create Created Date
@ComponentScan
public class BackendcursojavaApplication {

	static final Logger logger = LoggerFactory.getLogger(BackendcursojavaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BackendcursojavaApplication.class, args);
		logger.info("Working c:");
	}

	/**
	* A single instance is created for the ENTIRE project.
	*/
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	public AppProperties getAppProperties() {
		return new AppProperties();
	}

	/***
	 * In order not to instantiate several mappers
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper (){
		ModelMapper mapper = new ModelMapper();
		// In order to not mapping posts and not create a buckle
		mapper.typeMap(UserDto.class, UserRest.class).addMappings(m -> m.skip(UserRest::setPosts));
		return mapper;
	}

}
