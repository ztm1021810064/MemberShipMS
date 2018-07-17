package cn.xzq.controller;


import cn.xzq.entity.Users;
import cn.xzq.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by FLC on 2018/4/18.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource(name = "userServices")
    private IUserService userServices;

    @RequestMapping("/dologin")
    public String dologin() {
        return "login";
    }

    @RequestMapping("/login")
    public String login(String userName, String password, HttpServletResponse response, HttpServletRequest request, Model model) throws IOException {

        System.out.println("接收的值为：" + userName + ":" + password);
        Users users = userServices.login(userName);
        if (users == null) {
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = response.getWriter();
            out.flush();
            out.println("<script>");
            out.println("alert('用户名或者密码错误！');");
            out.println("</script>");
            return "login";
        }
        System.out.println("查询出来的密码为：" + users.getPassword());
        if (users.getPassword().equals(password) || users.getPassword() == password) {
            /**
             * 登陆成功跳转main.html
             */
            request.getSession().setAttribute("user", users);


            return "main";
        } else {
            /**
             * 登陆失败跳转login.html
             */
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = response.getWriter();
            out.flush();
            out.println("<script>");
            out.println("alert('登录失败！用户名或者密码错误！');");
            out.println("</script>");
            return "login";
        }

    }

    @RequestMapping("/allUsers")
    @ResponseBody
    public Object saleList(@RequestParam(value="userName", defaultValue="123")String userName, @RequestParam(required = false, defaultValue="1")int pageNum, @RequestParam(required = false, defaultValue="5")int pageSize)
    {
        System.out.println(userName+"===========>userName");
        System.out.println(pageNum+"============>"+pageNum);
        PageInfo<Users> saleList = userServices.allUsersByTypeId(userName,(pageNum+1), pageSize);

        System.out.println("当前页数"+saleList.getPageNum());
        System.out.println("记录数："+saleList.getSize());
        for(Users u : saleList.getList()){
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String format1 = format.format(u.getLast_modify_time());
            int status = u.getStatus();
            if (status==1){
                u.setNewStatus("正常");
            }else{
                u.setNewStatus("冻结");
            }
            System.out.println(u.getNewStatus());
            u.setDateTime(format1);
            System.out.println(u.getDateTime());
        }
        return saleList;
    }


    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "login";
    }
}
