package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Day;
import ru.sokolov.models.Year;

import java.util.List;

public interface YearRepository extends JpaRepository<Year, Integer> {
    List<Year> findByCdate(String cdate);
    List<Year> findAll();
}
