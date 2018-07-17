package cn.xzq.dao;

/**
 * Created by FLC on 2018/4/18.
 */


import cn.xzq.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户Dao
 */
@Repository
public interface IUserDAO {
    //01.登陆的方法
    Users login(String name);

    List<Users> allUsersByTypeId(@Param("userName")String userName);

}
