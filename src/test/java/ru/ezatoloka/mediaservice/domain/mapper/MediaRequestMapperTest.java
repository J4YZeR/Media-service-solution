package ru.ezatoloka.mediaservice.domain.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaRequestMapperTest {

	private MediaRequestMapper mediaRequestMapper;

	private static String generateValidMediaId() {
		return "a".repeat(36);
	}

	@BeforeEach
	void setup() {
		mediaRequestMapper = Mappers.getMapper(MediaRequestMapper.class);
	}

	@Test
	void mediaToMediaDto() {
		String id = generateValidMediaId();
		String url = "url";
		Media.MediaType type = Media.MediaType.IMAGE;
		Media mediaToMap = new Media(id, type, url);

		MediaDto mappedMediaDto = mediaRequestMapper.mediaToMediaDto(mediaToMap);

		MediaDto expectedMediaDto = new MediaDto(id, MediaDto.MediaDtoType.valueOf(type.toString()), url);
		assertEquals(expectedMediaDto, mappedMediaDto);
	}

	@Test
	void mediaDtoToMedia() {
		String id = generateValidMediaId();
		String url = "url";
		Media.MediaType type = Media.MediaType.IMAGE;
		MediaDto mediaDtoToMap = new MediaDto(id, MediaDto.MediaDtoType.valueOf(type.toString()), url);

		Media mappedMedia = mediaRequestMapper.mediaDtoToMedia(mediaDtoToMap);

		Media expectedMedia = new Media(id, type, url);
		assertEquals(expectedMedia, mappedMedia);
	}
}