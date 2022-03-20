package ru.ezatoloka.mediaservice.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ezatoloka.mediaservice.domain.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
}
