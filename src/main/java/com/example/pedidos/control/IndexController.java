package com.example.pedidos.control;

import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@CrossOrigin(origins = "", maxAge = 3600, allowCredentials = "false")
@RequestMapping(value = "/api/v1")
public class IndexController {

    public static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    UserRepository userRepository;

    private final UserService userService;



    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/index")
    public String index() {

        return "/index";
    }



//    Código do professor Leandro:
//    private String serverMessage = null;
//    @PostMapping("/acessar")
//    public ModelAndView acessar(
//            @RequestParam String funLogin,
//            @RequestParam String senha,
//            ModelMap model) {
//
//        int acesso = funcionarioService.acessar(funLogin, senha);
//
//        if (acesso == 1) {
//            return new ModelAndView("redirect:/api/v1/mesas");
//        } else if (acesso == 2) {
//            return new ModelAndView("redirect:/api/v1/categorias");
//        }
//
//        serverMessage = "Email ou senha incorreto!";
//        model.addAttribute("serverMessage", serverMessage);
//
//        return new ModelAndView("redirect:/api/v1/login", model);
//    }

    @GetMapping("/login")

    public String login(Model model) {

        return "/login";
    }
    @GetMapping("/login-error")

    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "/login";
    }
}
