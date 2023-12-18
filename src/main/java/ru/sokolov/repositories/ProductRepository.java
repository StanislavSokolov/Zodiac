package ru.sokolov.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sokolov.models.Product;
import ru.sokolov.models.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByNmId(String nmId);
    List<Product> findBySupplierArticle(String supplierArticle);
    List<Product> findBySupplierArticleAndShopName(String supplierArticle, String shopName);
    List<Product> findBySubject(String subject);
    List<Product> findBySubjectAndShopName(String subject, String shopName);
    List<Product> findBySupplierArticleNotLike(String supplierArticle);
}
