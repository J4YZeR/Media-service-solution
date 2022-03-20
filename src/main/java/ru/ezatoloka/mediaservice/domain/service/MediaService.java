package ru.ezatoloka.mediaservice.domain.service;

import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;

public interface MediaService {

	void saveMedia(MediaDto mediaDto);

	MediaResponseDto getMedia(String id);
}
