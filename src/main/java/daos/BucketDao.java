package daos;

import entities.Bucket;
import entities.Product;
import org.apache.log4j.Logger;
import recources.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BucketDao implements CRUD<Bucket> {
    private static final Logger LOG = Logger.getLogger(BucketDao.class);
    private Connection connection;

    public BucketDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    public static final String SELECT_ALL = "SELECT * FROM buckets";
    public static final String SELECT_BY_USER_ID = "SELECT * FROM buckets where user_id = ?";
    public static final String DELETE = "DELETE FROM buckets where id = ?";
    public static final String DELETE_BY_USER_AND_PRODUCT_IDS =
            "DELETE FROM buckets where user_id = ? and product_id = ?";
    public static final String UPDATE = "UPDATE buckets SET user_id = ?, product_id = ?, purchase_date = ? where id = ?";
    public static final String SELECT_BY_ID = "SELECT * FROM buckets where id = ?";
    public static final String INSERT_INTO =
            "INSERT INTO buckets(user_id, product_id, purchase_date) values(?, ?, ?)";

    @Override
    public Bucket create(Bucket bucket) {
        LOG.trace("Creating new bucket...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            setParametersForBucket(preparedStatement, bucket);
            preparedStatement.executeUpdate();

            String infoCreate = String.format("Created a new bucket in database with user_id=%d, product_id=%d",
                    bucket.getUser_id(), bucket.getProduct_id());
            LOG.info(infoCreate);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            bucket.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            LOG.error("Can`t create new user", e);
        }
        return bucket;
    }

    private void setParametersForBucket(PreparedStatement preparedStatement, Bucket bucket) throws SQLException {
        preparedStatement.setInt(1, bucket.getUser_id());
        preparedStatement.setInt(2, bucket.getProduct_id());
        preparedStatement.setDate(3, new Date(bucket.getPurchase_date().getTime()));

    }

    @Override
    public Optional<Bucket> read(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Bucket.of(resultSet));
            }
        } catch (SQLException e) {
            String errorReadById = String.format("Can`t read bucket with id = %s", id);
            LOG.error(errorReadById, e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Bucket bucket) {
        LOG.trace("Updating bucket...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            setParametersForBucket(preparedStatement, bucket);
            preparedStatement.setInt(4, bucket.getId());
            preparedStatement.executeUpdate();

            String infoUpdate = String.format("Bucket with id = %d was updated to bucket with user_id=%d, product_id=%d",
                    bucket.getUser_id(), bucket.getProduct_id());
            LOG.info(infoUpdate);

        } catch (SQLException e) {
            LOG.error("Can`t update user", e);
        }
    }

    @Override
    public void delete(int id) {
        LOG.trace("Deleting bucket...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Can`t delete user by id", e);
        }
    }

    @Override
    public List<Bucket> readAll() {
        List<Bucket> buckets = new ArrayList<>();
        LOG.trace("Reading all buckets from DB...");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                buckets.add(Bucket.of(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Can`t read all buckets", e);
        }
        return buckets;
    }

    public List<Bucket> getAllByUserId(int userId) {
        List<Bucket> buckets = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_USER_ID);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                buckets.add(Bucket.of(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Can`t read buckets by userID", e);
        }
        return buckets;
    }

    public void deleteBucketByUserAndProductIds(int productId, int userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_USER_AND_PRODUCT_IDS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            LOG.error("Can`t delete bucket by product and user ids", e);
        }
    }
}
