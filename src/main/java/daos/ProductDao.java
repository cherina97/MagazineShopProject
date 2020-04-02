package daos;

import entities.Product;
import org.apache.log4j.Logger;
import resources.ConnectionUtil;
import resources.EntityManagerUtils;

import javax.persistence.EntityManager;
import java.nio.channels.SelectableChannel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductDao implements CRUD<Product> {
    private static final Logger LOG = Logger.getLogger(ProductDao.class);
    private Connection connection;

    public ProductDao() {
        this.connection = ConnectionUtil.getConnection();
    }


    private static String SELECT_ALL = "select p from Product p";
    private static String CREATE = "insert into products(`name`, `description`, `price`) values (?,?,?)";
    private static String READ_BY_ID = "select * from products where id =?";
    private static String UPDATE_BY_ID = "update products set name=?, description = ?, price = ? where id = ?";
    private static String DELETE_BY_ID = "delete from products where id=?";
    private static String READ_ALL_IN = "select p from Product p where p.id in (:productIds)";


    @Override
    public Product create(Product product) {
        LOG.info("Creating new product....");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(product);
        entityManager.getTransaction().commit();
        LOG.info("New product was created");

        return product;
    }

    private void setParametersForProduct(PreparedStatement preparedStatement, Product product) throws SQLException {
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getDescription());
        preparedStatement.setFloat(3, product.getPrice());

    }

    @Override
    public Optional<Product> read(int id) {
        LOG.info("Reading product by id...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        Product product = entityManager.find(Product.class, id);
        entityManager.getTransaction().commit();

        return Optional.ofNullable(product);
    }

    @Override
    public void update(Product product) {
        LOG.trace("Updating зкщвгсе...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            setParametersForProduct(preparedStatement, product);
            preparedStatement.setInt(4, product.getId());
            preparedStatement.executeUpdate();

            String infoUpdate = String.format("Product with id = %d was updated to product with name = %d",
                    product.getId(), product.getName());
            LOG.info(infoUpdate);

        } catch (SQLException e) {
            LOG.error("Can`t update product", e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.trace("Deleting product...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Can`t delete user by id", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Product> readAll() {
        LOG.info("Reading all products from DB...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        List<Product> products = entityManager.createQuery(SELECT_ALL)
                .getResultList();
        entityManager.getTransaction().commit();

        return products;
    }

    @SuppressWarnings("unchecked")
    public List<Product> readAllByIds(Set<Integer> productIds) {
        LOG.info("Reading products by ids...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        return entityManager.createQuery(READ_ALL_IN)
                .setParameter("productIds", productIds)
                .getResultList();
    }
}
