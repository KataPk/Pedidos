package com.example.pedidos.control.adm;

import com.example.pedidos.dtos.MesaRecordDto;
import com.example.pedidos.model.entity.Mesa;
import com.example.pedidos.model.repository.MesaRepository;
import com.example.pedidos.payload.response.MessageResponse;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<MesaRecordDto> mesas = mesaService.findAllAtivos();
        List<MesaRecordDto> mesasInativas = mesaService.findAllInativos();
        model.addAttribute("mesas", mesas);
        model.addAttribute("mesasInativas", mesasInativas);
        return "Adm/MesasAdm";
    }


    @PostMapping("/createMesa")
    public ResponseEntity<?> createMesa(
            @RequestParam ("numMesa") int numMesa
    ){
        Map<String, Object> responseData = new HashMap<>();

        try {

        if (mesaRepository.existsByNumMesa(numMesa)){

            return ResponseEntity.badRequest().body(new MessageResponse("Error: Número já registrado!"));
        }

        Mesa mesa = new Mesa(
                numMesa,
                "ACTIVE"
        );

        mesaRepository.save(mesa);


            responseData.put("success", true);
            responseData.put("message", "Mesa registrada com sucesso.");

            return ResponseEntity.ok(responseData);
        }catch (Exception e){


            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }


    }






}
