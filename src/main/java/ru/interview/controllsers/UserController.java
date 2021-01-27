package ru.interview.controllsers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.interview.errors.ErrorUser;
import ru.interview.models.User;
import ru.interview.services.UserService;
import ru.interview.utils.CodingPassword;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/addUser")
    public String addUser(){
        return "addUser";
    }

    @PostMapping("/addUserMethod")
    public String addUser(Model model,@RequestParam Map<String,String> request){

        if(request.containsKey("name") && !request.get("name").isEmpty()
                && request.containsKey("password") && !request.get("password").isEmpty()
                && request.containsKey("email") && !request.get("email").isEmpty()
                && request.containsKey("first_name")&& !request.get("first_name").isEmpty()
                && request.containsKey("second_name")&& !request.get("second_name").isEmpty()) {
            ErrorUser messageBack;

            User newUser = new User(request.get("name"),CodingPassword.encodePassword(request.get("password")),
                    request.get("first_name"),request.get("second_name"),request.get("email"));
            try {
                messageBack  = userService.addNewUser(newUser);
            } catch (SQLException e) {
                e.printStackTrace();
                messageBack = ErrorUser.TECHNICAL_ERROR;
            }
            model.addAttribute("error", messageBack.getDescription());
            return messageBack == ErrorUser.OK ? "redirect:/": "addUser";
        } else {
            model.addAttribute("error", ErrorUser.NO_INFORMATION.getDescription());
        }
            return "addUser";
    }
}
