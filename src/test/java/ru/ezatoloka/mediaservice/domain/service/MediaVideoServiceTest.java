package ru.ezatoloka.mediaservice.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.ezatoloka.mediaservice.domain.entity.MediaVideo;
import ru.ezatoloka.mediaservice.domain.repository.MediaVideoRepository;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MediaVideoServiceTest {

	@Mock
	private ExecutorService executorService;

	@Mock
	private MediaVideoRepository mediaVideoRepository;

	@Mock
	private VideoDurationCalculationService videoDurationCalculationService;

	private MediaVideoService mediaVideoService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mediaVideoService = new MediaVideoServiceImpl(executorService, mediaVideoRepository, videoDurationCalculationService);
	}

	@Test
	void saveNewVideoById() throws InterruptedException {
		String id = generateValidId();
		Integer duration = 10;
		MediaVideo mediaVideo = new MediaVideo(id, duration);

		when(mediaVideoRepository.findById(id)).thenReturn(Optional.empty());
		when(mediaVideoRepository.saveAndFlush(mediaVideo)).thenReturn(mediaVideo);
		when(videoDurationCalculationService.calc()).thenReturn(duration);

		assertDoesNotThrow(() -> mediaVideoService.saveById(id));
		ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
		verify(executorService, times(1)).submit(runnableArgumentCaptor.capture());
		assertDoesNotThrow(() -> runnableArgumentCaptor.getValue().run());
		verify(mediaVideoRepository, times(1)).saveAndFlush(mediaVideo);
	}

	@Test
	void saveAlreadyExistingVideo() throws InterruptedException {
		String id = generateValidId();
		Integer duration = 10;
		MediaVideo mediaVideo = new MediaVideo(id, duration);

		when(mediaVideoRepository.findById(id)).thenReturn(Optional.of(mediaVideo));
		when(mediaVideoRepository.saveAndFlush(mediaVideo)).thenReturn(mediaVideo);
		when(videoDurationCalculationService.calc()).thenReturn(duration);

		assertDoesNotThrow(() -> mediaVideoService.saveById(id));
		ArgumentCaptor<Runnable> runnableArgumentCaptor = ArgumentCaptor.forClass(Runnable.class);
		verify(executorService, times(1)).submit(runnableArgumentCaptor.capture());
		assertDoesNotThrow(() -> runnableArgumentCaptor.getValue().run());
		verify(mediaVideoRepository, times(0)).saveAndFlush(mediaVideo);
	}

	@Test
	void getExistingVideoDuration() {
		String id = generateValidId();
		Integer duration = 10;
		MediaVideo mediaVideo = new MediaVideo(id, duration);

		when(mediaVideoRepository.findById(id)).thenReturn(Optional.of(mediaVideo));

		assertEquals(Optional.of(duration), mediaVideoService.getDurationById(id));
	}

	@Test
	void getDurationIfVideoNotExists() {
		String id = generateValidId();

		when(mediaVideoRepository.findById(id)).thenReturn(Optional.empty());

		assertEquals(Optional.empty(), mediaVideoService.getDurationById(id));
	}

	private String generateValidId() {
		return "a".repeat(36);
	}


}