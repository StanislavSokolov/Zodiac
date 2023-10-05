package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Item;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
//    List<Item> findByNmId(String nmId);
}
