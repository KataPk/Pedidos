package com.example.pedidos.control.adm;

import com.example.pedidos.dtos.MesaRecordDto;
import com.example.pedidos.model.entity.Mesa;
import com.example.pedidos.model.repository.MesaRepository;
import com.example.pedidos.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmMesaController {

    @Autowired
    MesaRepository mesaRepository;
    private final MesaService mesaService;

    public AdmMesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }


    @GetMapping("/mesas")
    public String Mesas(Model model){
        List<MesaRecordDto> mesas = mesaService.findAll();
        model.addAttribute("mesas", mesas);
        return "Adm/MesasAdm";
    }

    @PostMapping("/Mesa")
    public ResponseEntity<Mesa> createMesa(@RequestBody @Valid MesaRecordDto MesaRecordDto) {
        var Mesa = new Mesa();
        BeanUtils.copyProperties(MesaRecordDto, Mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaRepository.save(Mesa));
    }


}
