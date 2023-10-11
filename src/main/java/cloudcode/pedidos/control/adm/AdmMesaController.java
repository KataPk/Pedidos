package cloudcode.pedidos.control.adm;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.repository.MesaRepository;
import cloudcode.pedidos.response.MessageResponse;
import cloudcode.pedidos.service.MesaService;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmMesaController {

    private final MesaService mesaService;
    @Autowired
    MesaRepository mesaRepository;

    public AdmMesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }


    @GetMapping("/mesas")
    public String mesas(Model model) {
        List<MesaRecordDto> mesas = mesaService.findAllMesas();
        model.addAttribute("mesas", mesas);
        return "Adm/MesasAdm";
    }

    @Transactional
    @PostMapping("/createMesa")
    public ResponseEntity<?> createMesa(
            @RequestParam("numMesa") int numMesa
    ) {
        Map<String, Object> responseData = new HashMap<>();

        try {

            if (mesaRepository.existsByNumMesa(numMesa)) {
                Mesa mesaExist = mesaRepository.findByNumMesa(numMesa);
                if (mesaExist.getMStatus().equals("DELETADA")) {
                    mesaExist.setMStatus("ACTIVE");
                    mesaRepository.save(mesaExist);
                    responseData.put("success", true);
                    responseData.put("message", "Mesa registrada com sucesso.");
                    return ResponseEntity.ok().body(responseData);
                }
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
        } catch (Exception e) {


            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }


    }


    @Transactional
    @PostMapping("/editMesa")
    public ResponseEntity<?> editMesa(
            @RequestParam("mesaId") long mesaId,
            @RequestParam("numMesa") int numMesa
    ) {
        Map<String, Object> responseData = new HashMap<>();

        try {

            Mesa mesa = mesaRepository.getReferenceById(mesaId);
            if (mesaRepository.existsByNumMesa(numMesa)) {
                Mesa mesaExist = mesaRepository.findByNumMesa(numMesa);
                if (mesaExist.getMStatus().equals("DELETADA")) {
                    int newNum = mesaExist.getNumMesa();
                    int oldNUm = mesa.getNumMesa();
                    mesaExist.setNumMesa(oldNUm);
                    mesa.setNumMesa(newNum);
                    mesaRepository.save(mesaExist);
                    mesaRepository.save(mesa);
                    responseData.put("success", true);
                    responseData.put("message", "Mesa registrada com sucesso.");
                    return ResponseEntity.ok().body(responseData);
                }
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Número já registrado!"));
            }

            mesa.setNumMesa(numMesa);
            mesaRepository.save(mesa);

            responseData.put("success", true);
            responseData.put("message", "Mesa registrada com sucesso.");

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {


            responseData.put("success", false);
            responseData.put("message", "Erro ao processar a solicitação.");


            return ResponseEntity.badRequest().body(responseData);
        }
    }


    @PostMapping("/deleteMesa")
    public RedirectView deleteMesa(@RequestParam("mesaId") long id) {

        try {

            Mesa mesa = mesaRepository.getReferenceById(id);

            mesa.setMStatus("DELETADA");
            mesaRepository.save(mesa);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return new RedirectView("/api/admin/mesas");
    }

    @PostMapping("/reAtivarMesa")
    public RedirectView reAtivarMesa(@RequestParam("mesaId") long id) {

        try {

            Mesa mesa = mesaRepository.getReferenceById(id);
            if (!mesa.getMStatus().equals("OCUPADA")) {

                mesa.setMStatus("ACTIVE");
                mesaRepository.save(mesa);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return new RedirectView("/api/admin/mesas");

    }


}
