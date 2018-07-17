package cn.xzq.service;


import cn.xzq.entity.Users;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by FLC on 2018/4/18.
 */

public interface IUserService {
    Users login(String name);

    PageInfo<Users> allUsersByTypeId(String userName,int pageNum, int pageSize);

}
