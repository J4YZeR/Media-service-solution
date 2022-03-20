package ru.ezatoloka.mediaservice.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.ezatoloka.mediaservice.domain.entity.MediaVideo;
import ru.ezatoloka.mediaservice.domain.repository.MediaVideoRepository;

import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Service
public class MediaVideoServiceImpl implements MediaVideoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediaVideoServiceImpl.class);

	private final ExecutorService executorService;
	private final MediaVideoRepository mediaVideoRepository;
	private final VideoDurationCalculationService videoDurationCalculationService;


	public MediaVideoServiceImpl(ExecutorService executorService, MediaVideoRepository mediaVideoRepository,
								 VideoDurationCalculationService videoDurationCalculationService) {
		this.mediaVideoRepository = mediaVideoRepository;
		this.videoDurationCalculationService = videoDurationCalculationService;
		this.executorService = executorService;
	}

	@Override
	public void saveById(String id) {
		executorService.submit(() -> {
			try {
				if (mediaVideoRepository.findById(id).isEmpty()) {
					MediaVideo mediaVideo = new MediaVideo(id, videoDurationCalculationService.calc());
					mediaVideoRepository.saveAndFlush(mediaVideo);
				}
			} catch (InterruptedException e) {
				System.err.println(e.toString());
				LOGGER.error("Unexpected error while trying to calculate duration");
			}
		});
	}

	@Override
	public Optional<Integer> getDurationById(String id) {
		Optional<MediaVideo> optionalMediaVideo = mediaVideoRepository.findById(id);
		return optionalMediaVideo.flatMap(MediaVideo::getDuration);
	}
}
