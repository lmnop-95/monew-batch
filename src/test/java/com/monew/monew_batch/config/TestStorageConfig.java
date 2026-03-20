package com.monew.monew_batch.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.monew.monew_batch.storage.BinaryStorage;

@Configuration
@ConditionalOnProperty(name = "storage.type", havingValue = "local")
public class TestStorageConfig {

	@Bean
	public BinaryStorage binaryStorage() {
		return new BinaryStorage() {
			@Override
			public UUID put(UUID id, Instant date, String interest, byte[] data) {
				return id;
			}

			@Override
			public InputStream get(UUID id, String interest, Instant date) {
				return new ByteArrayInputStream(new byte[0]);
			}

			@Override
			public Boolean exists(UUID id, String interest, Instant date) {
				return false;
			}
		};
	}
}
