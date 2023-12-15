package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Media;
import ru.sokolov.models.Product;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
