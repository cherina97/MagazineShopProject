package daos;

import entities.Bucket;
import org.apache.log4j.Logger;
import recources.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BucketDao implements CRUD <Bucket> {
    private static final Logger LOG = Logger.getLogger(BucketDao.class);
    private Connection connection;

    public BucketDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    public static final String SELECT_ALL = "SELECT * FROM buckets";
    public static final String DELETE = "DELETE FROM buckets where id = ?";
    public static final String UPDATE = "UPDATE buckets SET user_id = ?, product_id = ?, purchase_date = ? where id = ?";
    public static final String SELECT_BY_ID = "SELECT * FROM buckets where id = ?";
    public static final String INSERT_INTO =
            "INSERT INTO buckets(user_id, product_id, purchase_date) values(?, ?, ?)";
    @Override
    public Bucket create(Bucket bucket) {
        LOG.trace("Creating new bucket...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bucket.getUser_id());
            preparedStatement.setInt(2, bucket.getProduct_id());
            preparedStatement.setDate(3, (Date) bucket.getPurchase_date());
            preparedStatement.executeUpdate();

            String infoCreate = String.format("Created a new bucket in database with user_id=%d, product_id=%d",
                    bucket.getUser_id(), bucket.getProduct_id());
            LOG.info(infoCreate);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            bucket.setId(generatedKeys.getInt(1));

            return bucket;
        } catch (SQLException e) {
            LOG.error("Can`t create new user", e);
        }
        return null;
    }

    @Override
    public Optional<Bucket> read(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
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
            preparedStatement.setInt(1, bucket.getUser_id());
            preparedStatement.setInt(2, bucket.getProduct_id());
            preparedStatement.setDate(3, (Date) bucket.getPurchase_date());
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
        LOG.trace("Deleting backet...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Can`t delete user by id", e);
        }
    }

    //todo isEmpty
    @Override
    public List<Bucket> readAll() {
        LOG.trace("Reading all buckets from DB...");
        List<Bucket> buckets = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            while (resultSet.next()) {
                buckets.add(Bucket.of(resultSet));
            }
            return buckets;
        } catch (SQLException e) {
            LOG.error("Can`t read all buckets", e);
        }

        return null;
    }
}
