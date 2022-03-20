package ru.ezatoloka.mediaservice.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;
import ru.ezatoloka.mediaservice.domain.mapper.MediaRequestMapper;
import ru.ezatoloka.mediaservice.domain.mapper.MediaResponseMapper;
import ru.ezatoloka.mediaservice.domain.repository.MediaRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MediaServiceTest {

	@Mock
	private MediaRequestMapper mediaRequestMapper;

	@Mock
	private MediaRepository mediaRepository;

	@Mock
	private MediaVideoService mediaVideoService;

	@Mock
	private MediaResponseMapper mediaResponseMapper;

	private MediaService mediaService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mediaService = new MediaServiceImpl(mediaRequestMapper, mediaResponseMapper, mediaRepository, mediaVideoService);
	}

	@Test
	void saveNewImageMedia() {
		String id = generateValidId();
		Media.MediaType type = Media.MediaType.IMAGE;
		String url = "url";
		MediaDto mediaDto = new MediaDto(id, MediaDto.MediaDtoType.valueOf(type.toString()), url);
		Media media = new Media(id, type, url);

		when(mediaRequestMapper.mediaDtoToMedia(mediaDto)).thenReturn(media);
		when(mediaRepository.saveAndFlush(media)).thenReturn(media);

		assertDoesNotThrow(() -> mediaService.saveMedia(mediaDto));
		verify(mediaRepository, times(1)).saveAndFlush(media);
	}

	@Test
	void updateAlreadyExistingMedia() {
		String id = generateValidId();
		Media.MediaType type = Media.MediaType.IMAGE;
		String url = "url";
		MediaDto mediaDto = new MediaDto(id, MediaDto.MediaDtoType.valueOf(type.toString()), url);
		Media media = new Media(id, type, url);

		when(mediaRequestMapper.mediaDtoToMedia(mediaDto)).thenReturn(media);
		when(mediaRepository.saveAndFlush(media))
				.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Media with such id already exists"));

		assertThrows(ResponseStatusException.class, () -> mediaService.saveMedia(mediaDto));
		verify(mediaRepository, times(1)).saveAndFlush(media);
	}

	@Test
	void saveNewVideoMedia() {
		String id = generateValidId();
		Media.MediaType type = Media.MediaType.VIDEO;
		String url = "url";
		MediaDto mediaDto = new MediaDto(id, MediaDto.MediaDtoType.valueOf(type.toString()), url);
		Media media = new Media(id, type, url);

		when(mediaRequestMapper.mediaDtoToMedia(mediaDto)).thenReturn(media);
		when(mediaRepository.saveAndFlush(media)).thenReturn(media);

		assertDoesNotThrow(() -> mediaService.saveMedia(mediaDto));
		verify(mediaRepository, times(1)).saveAndFlush(media);
		verify(mediaVideoService, times(1)).saveById(media.getId());
	}

	private String generateValidId() {
		return "a".repeat(36);
	}


	@Test
	void getExistingMedia() {
		String id = generateValidId();
		Media.MediaType type = Media.MediaType.VIDEO;
		String url = "url";
		Integer duration = 10;
		Media media = new Media(id, type, url);
		MediaResponseDto mappedMediaResponseDto = new MediaResponseDto(id,
				MediaResponseDto.MediaResponseDtoType.valueOf(type.toString()), url, null);

		when(mediaRepository.findById(id)).thenReturn(Optional.of(media));
		when(mediaResponseMapper.mediaToMediaResponseDto(media)).thenReturn(mappedMediaResponseDto);
		when(mediaVideoService.getDurationById(id)).thenReturn(Optional.of(duration));

		MediaResponseDto expectedMediaResponseDto = new MediaResponseDto(id,
				MediaResponseDto.MediaResponseDtoType.valueOf(type.toString()), url, duration);
		assertEquals(expectedMediaResponseDto, mediaService.getMedia(id));
	}

	@Test
	void getNonExistingMedia() {
		String id = generateValidId();

		when(mediaRepository.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class, () -> mediaService.getMedia(id));
	}
}