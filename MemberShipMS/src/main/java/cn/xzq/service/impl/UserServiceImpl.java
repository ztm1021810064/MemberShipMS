package cn.xzq.service.impl;


import cn.xzq.entity.Users;
import cn.xzq.dao.IUserDAO;
import cn.xzq.service.IUserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by FLC on 2018/4/18.
 */

@Service("userServices")
public class UserServiceImpl implements IUserService {
    @Resource(name = "IUserDAO")
    private IUserDAO userDAO;

    @Override
    public Users login(String name) {
        return userDAO.login(name);
    }

    @Override
    public PageInfo<Users> allUsersByTypeId(String userName,int pageNum, int pageSize) {
        Page<Users> page = PageHelper.startPage(pageNum, pageSize);
        List<Users> userss = userDAO.allUsersByTypeId(userName);
        for (Users user :userss) {
            System.out.println(user.getName());
        }
        return page.toPageInfo();
    }


}
