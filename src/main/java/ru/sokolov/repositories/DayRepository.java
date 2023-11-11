package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Day;
import ru.sokolov.models.Item;

import java.util.List;

public interface DayRepository extends JpaRepository<Day, Integer> {
    List<Day> findByCdate(String cdate);
    List<Day> findAll();
}
