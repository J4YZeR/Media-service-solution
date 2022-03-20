package ru.ezatoloka.mediaservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MediaResponseMapper {

	@Mapping(source = "url", target = "url", qualifiedByName = "unwrap")
	MediaResponseDto mediaToMediaResponseDto(Media media);

	Media mediaResponseDtoToMedia(MediaResponseDto mediaResponseDto);

	@Named("unwrap")
	default <T> T unwrap(Optional<T> optional) {
		return optional.orElse(null);
	}
}
