package ru.ezatoloka.mediaservice.domain.dto;


import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.Optional;

public class MediaResponseDto {

	private String id;

	private MediaResponseDtoType type;

	private String url;

	@Nullable
	private Integer duration;

	public MediaResponseDto() {
	}

	public MediaResponseDto(String id, MediaResponseDtoType type, String url, Integer duration) {
		this.id = id;
		this.type = type;
		this.url = url;
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MediaResponseDtoType getType() {
		return type;
	}

	public void setType(MediaResponseDtoType type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Optional<Integer> getDuration() {
		return Optional.ofNullable(duration);
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MediaResponseDto that = (MediaResponseDto) o;
		return Objects.equals(duration, that.duration) && Objects.equals(id, that.id) && Objects.equals(type, that.type) && Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type, url, duration);
	}

	public enum MediaResponseDtoType {
		IMAGE, VIDEO
	}
}
