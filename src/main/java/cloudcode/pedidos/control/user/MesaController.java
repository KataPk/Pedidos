package cloudcode.pedidos.control.user;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.repository.MesaRepository;
import cloudcode.pedidos.service.MesaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user")
public class MesaController {

    public static final Logger log = LoggerFactory.getLogger(MesaController.class);

    @Autowired
    MesaRepository mesaRepository;


    private final MesaService mesaService;


    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping("/mesas")
    public String mesas(Model model) {
        List<MesaRecordDto> mesas = mesaService.findAllAtivos();
        model.addAttribute("mesas", mesas);
        return "User/Mesas";

    }


}
