package cn.leo.leoblog.web.admin;

import cn.leo.leoblog.pojo.User;
import cn.leo.leoblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class loginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage(){
        return  "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession httpSession, RedirectAttributes attributes){
        User user=userService.checkUser(username,password);
        if (user!=null){
            user.setPassword(null);
            httpSession.setAttribute("user",user);
            return "admin/admin-index";
        }else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logOut(HttpSession httpSession){
        httpSession.removeAttribute("user");
        return "redirect:/admin";
    }
}
