package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Day;
import ru.sokolov.models.Item;
import ru.sokolov.repositories.DayRepository;
import ru.sokolov.repositories.ItemRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DayService {

    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public List<Day> findByCdate(String cdate) {
        return dayRepository.findByCdate(cdate);
    }
}
