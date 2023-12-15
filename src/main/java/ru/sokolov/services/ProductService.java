package ru.sokolov.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sokolov.models.Product;
import ru.sokolov.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public List<Product> findBySupplierArticleNotLike(String supplierArticle) {
        return productRepository.findBySupplierArticleNotLike(supplierArticle);
    }
    public List<Product> findBySupplierArticle(String supplierArticle) {
        return productRepository.findBySupplierArticle(supplierArticle);
    }
    public List<Product> findBySubject(String subject) {
        return productRepository.findBySubject(subject);
    }
}
