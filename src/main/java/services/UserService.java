package services;

import daos.UserDao;
import entities.User;

import java.util.List;

public class UserService {
    private static UserService userService;
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public static UserService getInstance(){
        if (userService == null){
            userService = new UserService();
        }
        return userService;
    }

    public User create (User t){
        return userDao.create(t);
    }

    public User read (int id){
        return userDao.read(id);
    }

    public void update (User t){
        userDao.update(t);
    }

    public void delete (int id){

        userDao.delete(id);
    }

    public List<User> readAll (){
        return userDao.readAll();
    }
}
