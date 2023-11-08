package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Day;
import ru.sokolov.models.Year;
import ru.sokolov.repositories.DayRepository;
import ru.sokolov.repositories.YearRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class YearService {

    private final YearRepository yearRepository;

    @Autowired
    public YearService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public List<Year> findByCdate(String cdate) {
        return yearRepository.findByCdate(cdate);
    }
}
