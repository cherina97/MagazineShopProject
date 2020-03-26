package services;

import daos.ProductDao;
import entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ProductService {
    private static ProductService productService;
    private ProductDao productDao;

    public ProductService() {
        this.productDao = new ProductDao();
    }

    public static ProductService getInstance(){
        if (productService == null){
            productService = new ProductService();
        }
        return productService;
    }

    public Product create (Product t){
        return productDao.create(t);
    }

    public Optional<Product> read (int id){
        return productDao.read(id);
    }

    public void update (Product t){
        productDao.update(t);
    }

    public void delete (int id){
        productDao.delete(id);
    }

    public List<Product> readAll (){
        return productDao.readAll();
    }

    public List<Product> readAllByIds(Set<Integer> productIds) {
        return productDao.readAllByIds(productIds);
    }
}
