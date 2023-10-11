package cloudcode.pedidos.control;


import cloudcode.pedidos.model.entity.ERole;
import cloudcode.pedidos.model.entity.Role;
import cloudcode.pedidos.model.entity.User;
import cloudcode.pedidos.model.repository.RoleRepository;
import cloudcode.pedidos.model.repository.UserRepository;
import cloudcode.pedidos.response.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/api/auth")
public class AuthController {


    public static final Logger log = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;


    //    Para testes no PostMan
    @PostMapping("/signin")
    public RedirectView authenticateUser(
            @RequestParam("username") String username, @RequestParam("password") String password
    ) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, encoder.encode(password)));

        if (authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new RedirectView("/api/v1/user/mesas");

        } else {
            return new RedirectView("/api/v1/login-error");

        }


    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestParam("nome") String nome,
                                          @RequestParam("cpf") String cpf,
                                          @RequestParam("rg") String rg,
                                          @RequestParam("dataNasc") String dataNasc,
                                          @RequestParam("logradouro") String logradouro,
                                          @RequestParam("numResid") String numResid,
                                          @RequestParam("cep") String cep,
                                          @RequestParam("cidade") String cidade,
                                          @RequestParam("bairro") String bairro,
                                          @RequestParam("uf") String uf,
                                          @RequestParam("complemento") String complemento,
                                          @RequestParam("username") String username,
                                          @RequestParam("email") String email,
                                          @RequestParam("senha") String password,
                                          @RequestParam("roles") Set<String> strRole) {
        try {
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }
            if (userRepository.existsByCpf(cpf) || userRepository.existsByRg(rg)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: User is already in system!"));
            }
            // Create new user's account
            User user = new User(
                    nome,
                    cpf,
                    rg,
                    LocalDate.parse(dataNasc),
                    logradouro,
                    numResid,
                    cep,
                    bairro,
                    cidade,
                    uf,
                    complemento,
                    email,
                    username,
                    encoder.encode(password),
            "ACTIVE"
            );

            Set<Role> roles = new HashSet<>();

            if (strRole == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRole.forEach(role -> {
                    if (role.equals("admin")) {
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                    } else {
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/signupTest")
    public String test() {
        return "teste";
    }


}
