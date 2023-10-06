package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.sokolov.models.Item;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByStatus(String status);
    List<Item> findByCdateAndStatus(String cdate, String status);
    List<Item> findBySdateAndStatus(String sdate, String status);
}
