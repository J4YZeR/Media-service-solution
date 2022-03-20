package ru.ezatoloka.mediaservice.application.web;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ezatoloka.mediaservice.domain.dto.MediaDto;
import ru.ezatoloka.mediaservice.domain.dto.MediaResponseDto;
import ru.ezatoloka.mediaservice.domain.service.MediaService;

import javax.validation.constraints.Size;

@RestController
@RequestMapping("/medias")
public class MediaController {

	private final MediaService mediaService;

	public MediaController(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	@PostMapping
	public void postMedia(@Validated @RequestBody MediaDto mediaDto) {
		mediaService.saveMedia(mediaDto);
	}

	@GetMapping("/{id}")
	public MediaResponseDto getMedia(@Validated @Size(min = 36, max = 36) @PathVariable("id") String id) {
		return mediaService.getMedia(id);
	}

}
