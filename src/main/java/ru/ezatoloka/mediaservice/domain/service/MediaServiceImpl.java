package ru.ezatoloka.mediaservice.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;
import ru.ezatoloka.mediaservice.domain.mapper.MediaRequestMapper;
import ru.ezatoloka.mediaservice.domain.mapper.MediaResponseMapper;
import ru.ezatoloka.mediaservice.domain.repository.MediaRepository;

import java.util.Objects;
import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

	private final MediaRequestMapper mediaRequestMapper;
	private final MediaResponseMapper mediaResponseMapper;
	private final MediaRepository mediaRepository;
	private final MediaVideoService mediaVideoService;

	public MediaServiceImpl(MediaRequestMapper mediaRequestMapper, MediaResponseMapper mediaResponseMapper,
							MediaRepository mediaRepository, MediaVideoService mediaVideoService) {
		this.mediaRequestMapper = mediaRequestMapper;
		this.mediaResponseMapper = mediaResponseMapper;
		this.mediaRepository = mediaRepository;
		this.mediaVideoService = mediaVideoService;
	}

	@Override
	public void saveMedia(MediaDto mediaDto) {
		Media media = mediaRequestMapper.mediaDtoToMedia(mediaDto);
		mediaRepository.saveAndFlush(media);

		if (media.getType().equals(Media.MediaType.VIDEO) && !Objects.isNull(media.getUrl()) && !media.getUrl().isEmpty()) {
			mediaVideoService.saveById(media.getId());
		}
	}

	@Override
	public MediaResponseDto getMedia(String id) {
		MediaResponseDto mediaResponseDto = new MediaResponseDto();
		Optional<Media> mediaOptional = mediaRepository.findById(id);
		if (mediaOptional.isPresent()) {
			mediaResponseDto = mediaResponseMapper.mediaToMediaResponseDto(mediaOptional.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find media with given id");
		}

		mediaResponseDto.setDuration(mediaVideoService.getDurationById(id).orElse(null));

		return mediaResponseDto;
	}
}
