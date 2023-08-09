package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sokolov.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
