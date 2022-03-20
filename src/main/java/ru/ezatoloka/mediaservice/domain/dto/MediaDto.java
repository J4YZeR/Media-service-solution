package ru.ezatoloka.mediaservice.domain.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;

public class MediaDto {

	@NotNull
	@Size(min = 36, max = 36)
	private String id;

	@NotNull
	private MediaDtoType type;

	@Nullable
	@Size(max = 255)
	private String url;

	public MediaDto() {
	}

	public MediaDto(String id, MediaDtoType type, String url) {
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

	public MediaDtoType getType() {
		return type;
	}

	public void setType(MediaDtoType type) {
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
		MediaDto mediaDto = (MediaDto) o;
		return Objects.equals(id, mediaDto.id) && Objects.equals(type, mediaDto.type) && Objects.equals(url, mediaDto.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, url);
	}

	public enum MediaDtoType {
		IMAGE, VIDEO
	}
}
