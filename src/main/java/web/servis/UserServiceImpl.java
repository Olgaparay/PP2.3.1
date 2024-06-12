package web.servis;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override

    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override

    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override

    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override

    public void updateUser(Long id,User user) {
        userDao.updateUser(id,user);
    }
}
