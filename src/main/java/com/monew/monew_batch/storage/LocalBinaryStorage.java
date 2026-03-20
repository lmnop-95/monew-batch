package com.monew.monew_batch.storage;

import static com.monew.monew_batch.util.DataTimeParser.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.UUID;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "storage.type", havingValue = "local")
public class LocalBinaryStorage implements BinaryStorage {

	private static final Path BASE_DIR = Paths.get("backup/article");

	@Override
	public UUID put(UUID id, Instant date, String interest, byte[] data) {
		String formattedDate = getDateFromInstant(date);
		Path path = BASE_DIR.resolve(interest).resolve(formattedDate).resolve(id.toString());
		try {
			Files.createDirectories(path.getParent());
			Files.write(path, data);
		} catch (IOException e) {
			log.error("로컬 백업 저장 실패: {}", path, e);
		}
		return id;
	}

	@Override
	public InputStream get(UUID id, String interest, Instant date) {
		String formattedDate = getDateFromInstant(date);
		Path path = BASE_DIR.resolve(interest).resolve(formattedDate).resolve(id.toString());
		try {
			return Files.newInputStream(path);
		} catch (IOException e) {
			log.error("로컬 백업 읽기 실패: {}", path, e);
			return new ByteArrayInputStream(new byte[0]);
		}
	}

	@Override
	public Boolean exists(UUID id, String interest, Instant date) {
		String formattedDate = getDateFromInstant(date);
		Path path = BASE_DIR.resolve(interest).resolve(formattedDate).resolve(id.toString());
		return Files.exists(path);
	}
}
