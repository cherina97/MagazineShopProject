package daos;

import entities.Product;
import org.apache.log4j.Logger;
import recources.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements CRUD<Product> {
    private static final Logger LOG = Logger.getLogger(ProductDao.class);
    private Connection connection;

    public ProductDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    private static String SELECT_ALL = "select * from products";
    private static String CREATE = "insert into products(`name`, `description`, `price`) values (?,?,?)";
    private static String READ_BY_ID = "select * from products where id =?";
    private static String UPDATE_BY_ID = "update products set name=?, description = ?, price = ? where id = ?";
    private static String DELETE_BY_ID = "delete from products where id=?";


    @Override
    public Product create(Product product) {
        LOG.trace("Creating new product...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
            preparedStatement.executeUpdate();

            String infoCreate = String.format("Created a new product in database with id=%d, name=%s",
                    product.getId(), product.getName());
            LOG.info(infoCreate);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            product.setId(generatedKeys.getInt(1));

            return product;
        } catch (SQLException e) {
            LOG.error("Can`t create new product", e);
        }
        return null;
    }

    @Override
    public Optional<Product> read(int id) {
        LOG.trace("Reading product by id...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(READ_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Optional.of(Product.of(resultSet));
            }
        } catch (SQLException e) {
            String errorReadById = String.format("Can`t read product with id = %s", id);
            LOG.error(errorReadById, e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        LOG.trace("Updating зкщвгсе...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BY_ID);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setFloat(3, product.getPrice());
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
    public List<Product> readAll() {
        List<Product> products = new ArrayList<>();
        LOG.trace("Reading all products from DB...");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                products.add(Product.of(resultSet));
            }
            if(!products.isEmpty()){
                return products;
            }
        } catch (SQLException e) {
            LOG.error("Can`t read all products", e);
        }
        return null;
    }
}
