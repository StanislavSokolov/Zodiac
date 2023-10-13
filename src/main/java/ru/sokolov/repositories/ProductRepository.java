package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Product;
import ru.sokolov.models.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNmId(String nmId);
    List<Product> findBySupplierArticle(String supplierArticle);
    List<Product> findBySubject(String subject);
}
