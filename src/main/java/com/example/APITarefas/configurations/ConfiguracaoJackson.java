package com.example.APITarefas.configurations;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class ConfiguracaoJackson {
	
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		return jacksonObjectMapperBuilder -> {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			jacksonObjectMapperBuilder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));
			jacksonObjectMapperBuilder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
		};
	}

}
