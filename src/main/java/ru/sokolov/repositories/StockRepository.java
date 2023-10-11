package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {
//    List<Item> findByNmId(String nmId);
}
