package ru.ezatoloka.mediaservice.domain.entity;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PreUpdate;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Media {

	@Id
	private String id;

	@Enumerated(EnumType.STRING)
	private MediaType type;

	@Nullable
	private String url;

	public Media() {
	}

	public Media(String id, MediaType type, String url) {
		this.id = id;
		this.type = type;
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@PreUpdate
	public void preventMediaUpdate() {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media with such id already exists");
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public Optional<String> getUrl() {
		return Optional.ofNullable(url);
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Media media = (Media) o;
		return Objects.equals(id, media.id) && type == media.type && Objects.equals(url, media.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, url);
	}

	public enum MediaType {
		IMAGE, VIDEO
	}
}
