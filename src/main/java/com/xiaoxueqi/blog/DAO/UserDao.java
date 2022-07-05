package com.xiaoxueqi.blog.DAO;

import com.xiaoxueqi.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserDao extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    User getByUsernameAndPassword(String username,String password);
}
