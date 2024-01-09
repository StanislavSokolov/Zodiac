package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.sokolov.models.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
