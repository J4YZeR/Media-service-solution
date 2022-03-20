package ru.ezatoloka.mediaservice.domain.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaResponseMapperTest {

	private MediaResponseMapper mediaResponseMapper;

	private static String generateValidMediaId() {
		return "a".repeat(36);
	}

	@BeforeEach
	void setup() {
		mediaResponseMapper = Mappers.getMapper(MediaResponseMapper.class);
	}

	@Test
	void mediaToMediaResponseDto() {
		String id = generateValidMediaId();
		String url = "url";
		Media.MediaType type = Media.MediaType.IMAGE;
		Media mediaToMap = new Media(id, type, url);

		MediaResponseDto mappedMediaResponseDto = mediaResponseMapper.mediaToMediaResponseDto(mediaToMap);

		MediaResponseDto expectedMediaResponseDto = new MediaResponseDto(id,
				MediaResponseDto.MediaResponseDtoType.valueOf(type.toString()), url, null);
		assertEquals(expectedMediaResponseDto, mappedMediaResponseDto);
	}

	@Test
	void mediaResponseDtoToMedia() {
		String id = generateValidMediaId();
		String url = "url";
		Media.MediaType type = Media.MediaType.IMAGE;
		MediaResponseDto mediaResponseDtoToMap = new MediaResponseDto(id,
				MediaResponseDto.MediaResponseDtoType.valueOf(type.toString()), url, null);

		Media mappedMedia = mediaResponseMapper.mediaResponseDtoToMedia(mediaResponseDtoToMap);

		Media expectedMedia = new Media(id, type, url);
		assertEquals(expectedMedia, mappedMedia);
	}
}