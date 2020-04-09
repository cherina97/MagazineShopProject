package daos;

import entities.Bucket;
import entities.Product;
import org.apache.log4j.Logger;
import resources.ConnectionUtil;
import resources.EntityManagerUtils;

import javax.persistence.EntityManager;
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
    public static final String SELECT_BY_USER_ID = "SELECT b FROM Bucket b where b.user_id = :userId";
    public static final String DELETE = "DELETE FROM buckets where id = ?";
    public static final String DELETE_BY_USER_AND_PRODUCT_IDS =
            "DELETE FROM buckets where user_id = ? and product_id = ?";
    public static final String UPDATE = "UPDATE buckets SET user_id = ?, product_id = ?, purchase_date = ? where id = ?";
    public static final String SELECT_BY_ID = "SELECT * FROM buckets where id = ?";
    public static final String INSERT_INTO =
            "INSERT INTO buckets(user_id, product_id, purchase_date) values(?, ?, ?)";

    @Override
    public Bucket create(Bucket bucket) {
        LOG.info("Creating new bucket....");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(bucket);
        entityManager.getTransaction().commit();
        LOG.info("New bucket was created");

        return bucket;
    }

    private void setParametersForBucket(PreparedStatement preparedStatement, Bucket bucket) throws SQLException {
        preparedStatement.setInt(1, bucket.getUser_id());
        preparedStatement.setInt(2, bucket.getProduct_id());
        preparedStatement.setDate(3, new Date(bucket.getPurchase_date().getTime()));

    }

    @Override
    public Optional<Bucket> read(int id) {
        LOG.info("Reading bucket by id...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        Bucket bucket = entityManager.find(Bucket.class, id);
        entityManager.getTransaction().commit();

        return Optional.ofNullable(bucket);
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
        LOG.info("Deleting bucket by id...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        Bucket bucket = entityManager.find(Bucket.class, id);
        entityManager.remove(bucket);
        entityManager.flush();
        entityManager.getTransaction().commit();
        LOG.info("Bucket was deleted");
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

    @SuppressWarnings("unchecked")
    public List<Bucket> getAllByUserId(int userId) {
        LOG.info("Getting all buckets by user id...");
        EntityManager entityManager = EntityManagerUtils.getEntityManager();
        entityManager.getTransaction().begin();
        List <Bucket> buckets = entityManager.createQuery(SELECT_BY_USER_ID)
                .setParameter("userId", userId)
                .getResultList();
        entityManager.getTransaction().commit();

        return buckets;
    }
}
