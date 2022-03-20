package ru.ezatoloka.mediaservice.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.entity.Media;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MediaRequestMapper {

	@Mapping(source = "url", target = "url", qualifiedByName = "unwrap")
	MediaDto mediaToMediaDto(Media media);

	@Mapping(source = "url", target = "url", qualifiedByName = "unwrap")
	Media mediaDtoToMedia(MediaDto mediaDto);

	@Named("unwrap")
	default <T> T unwrap(Optional<T> optional) {
		return optional.orElse(null);
	}
}
