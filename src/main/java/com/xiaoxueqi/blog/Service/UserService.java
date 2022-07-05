package com.xiaoxueqi.blog.Service;

import com.xiaoxueqi.blog.DAO.UserDao;
import com.xiaoxueqi.blog.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDAO;
    @Autowired
    public void setUserDao(UserDao userDAO){
        this.userDAO = userDAO;
    }

    public boolean isExist(String username){
        User user=getByName(username);
        return null!=user;//True exist False not Exist
    }
    public User getByName(String username){
        return userDAO.findByUsername(username);
    }

    public User get(String username,String password){
        return userDAO.getByUsernameAndPassword(username,password);
    }
    public void add(User user){
        userDAO.save(user);
    }
}
