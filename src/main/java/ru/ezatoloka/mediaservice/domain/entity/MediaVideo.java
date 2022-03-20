package ru.ezatoloka.mediaservice.domain.entity;

import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Optional;

@Entity
public class MediaVideo {

	@Id
	private String id;

	@Nullable
	private Integer duration;

	public MediaVideo() {
	}

	public MediaVideo(String id, Integer duration) {
		this.id = id;
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		MediaVideo that = (MediaVideo) o;
		return duration.equals(that.duration) && Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, duration);
	}
}
