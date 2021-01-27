package ru.interview.controllsers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.interview.errors.BalanceError;
import ru.interview.models.Balance;
import ru.interview.models.User;
import ru.interview.services.BalanceService;
import ru.interview.services.UserService;

import java.math.BigDecimal;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;
    private BalanceService balanceService;

    @Autowired
    public ProfileController(UserService userService,
                             BalanceService balanceService) {
        this.userService = userService;
        this.balanceService = balanceService;
    }

    @GetMapping
    @RequestMapping("/userinfo")
    public String getProfile(Principal principal, Model model){
        String name = principal.getName();
        User user = userService.getUser(name);
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping
    @RequestMapping("/balance")
    public String getBalance(Principal principal, Model model){
        String name = principal.getName();
        Balance balance = balanceService.getBalance(name);
        model.addAttribute("balance",balance);
        return "balance";
    }

    @PostMapping
    @RequestMapping("/addBalance")
    public String addBalance(Principal principal, Model model, @RequestParam("addBalance") String add){
        String name = principal.getName();
        String addNew = add.replaceAll(",",".");
        try {
            balanceService.addBalance(name, new BigDecimal(addNew));
        } catch (NumberFormatException e){
            model.addAttribute("error", BalanceError.FORMAT_ERROR.getDescription());
        }
        Balance balance = balanceService.getBalance(name);
        model.addAttribute("balance", balance);
        return "balance";
    }
}
