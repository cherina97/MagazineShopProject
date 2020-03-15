package daos;


import entities.User;
import recources.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements CRUD<User> {
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getInt(1));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t create new user");
        }
    }

    @Override
    public User read(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return User.of(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Can`t read a user by id");
        }
    }
    public Optional<User> readByEmail(String email) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return Optional.of(User.of(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t read a user by email");
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Can`t update a user");
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can`t delete a user by id");
        }
    }

    @Override
    public List<User> readAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);

            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(User.of(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Can`t read all users");
        }
    }
}
