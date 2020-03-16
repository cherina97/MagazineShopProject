package daos;


import entities.User;
import org.apache.log4j.Logger;
import recources.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CRUD<User> {
    private static final Logger log = Logger.getLogger(UserDao.class);

    private Connection connection;

    public UserDao() {
        this.connection = ConnectionUtil.getConnection();
    }

    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String DELETE = "DELETE FROM users where id = ?";
    public static final String UPDATE = "UPDATE users SET first_name = ?, last_name = ?, email = ?, role = ?, password = ? where id = ?";
    public static final String SELECT_BY_ID = "SELECT * FROM users where id = ?";
    public static final String INSERT_INTO =
            "INSERT INTO users(first_name, last_name, email, role, password) values(?, ?, ?, ?, ?)";
    public static final String SELECT_BY_EMAIL = "SELECT * FROM users where email = ?";

    @Override
    public User create(User user) {
        log.trace("Creating new user...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();
            String infoCreate = String.format("Created a new user in database with id=%d, email=%s",
                    user.getId(), user.getEmail());
            log.info(infoCreate);

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getInt(1));

            return user;
        } catch (SQLException e) {
            log.error("Can`t create new user", e);
        }
        return null;
    }

    @Override
    public Optional<User> read(int id) {
        log.trace("Reading user by id...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return Optional.of(User.of(resultSet));
            }
        } catch (SQLException e) {
            String errorReadById = String.format("Can`t read user with id = %s", id);
            log.error(errorReadById, e);
        }
        return Optional.empty();
    }

    public Optional<User> readByEmail(String email) {
        log.trace("Reading user by email...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return Optional.of(User.of(resultSet));
            }
        } catch (SQLException e) {
            String errorReadByEmail = String.format("Can`t read user with email = %s", email);
            log.error(errorReadByEmail, e);
        }
        return Optional.empty();
    }

    @Override
    public void update(User user) {
        log.trace("Updating user...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();

            String infoUpdate = String.format("User with id = %d was updated to user with email = %d", user.getId(), user.getEmail());
            log.info(infoUpdate);

        } catch (SQLException e) {
            log.error("Can`t update user", e);
        }
    }

    @Override
    public void delete(int id) {
        log.trace("Deleting user...");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can`t delete user by id", e);
        }
    }

    @Override
    public Optional<List<User>> readAll() {
        log.trace("Reading all users from DB...");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.of(resultSet));
            }
            Optional<List<User>> optionalUsers = Optional.ofNullable(users);

            return optionalUsers;
        } catch (SQLException e) {
            log.error("Can`t read all users", e);
        }
        return Optional.empty();
    }
}
