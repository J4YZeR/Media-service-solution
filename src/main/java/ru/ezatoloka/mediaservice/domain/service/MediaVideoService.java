package ru.ezatoloka.mediaservice.domain.service;

import java.util.Optional;

public interface MediaVideoService {

	void saveById(String id);

	Optional<Integer> getDurationById(String id);
}
