package cloudcode.pedidos.control.user;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.entity.Pedido;
import cloudcode.pedidos.model.repository.MesaRepository;
import cloudcode.pedidos.model.repository.PedidoRepository;
import cloudcode.pedidos.service.MesaService;
import cloudcode.pedidos.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user")
public class MesaController {

    public static final Logger log = LoggerFactory.getLogger(MesaController.class);
    private final MesaService mesaService;

    private final PedidoService pedidoService;

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    public MesaController(MesaService mesaService, PedidoService pedidoService) {
        this.mesaService = mesaService;
        this.pedidoService = pedidoService;
    }

    @GetMapping("/mesas")
    public String mesas(Model model) {
        List<MesaRecordDto> mesas = mesaService.findAll();
        model.addAttribute("mesas", mesas);
        return "User/Mesas";

    }
    @Transactional
    @PostMapping("/inativarMesa")
    public RedirectView inativarMesa(@RequestParam("mesaId") long id) {


        try {

            Mesa mesa = mesaRepository.getReferenceById(id);
            if (!mesa.getMStatus().equals("OCUPADA")) {
                mesa.setMStatus("INACTIVE");
                mesaRepository.save(mesa);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return new RedirectView("/api/user/mesas");
    }
    @Transactional
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
        return new RedirectView("/api/user/mesas");
    }

    @Transactional
    @PostMapping("/changeClientMesa")
    public RedirectView mudarMesaClient(@RequestParam("mesaAtualId") long mesaAtualId,
                                        @RequestParam("mesaDestinoId") long mesaDestinoId) {

        try {


            Mesa mesaAtual = mesaRepository.getReferenceById(mesaAtualId);
            Mesa mesaDestino = mesaRepository.getReferenceById(mesaDestinoId);
            Pedido pedido = pedidoRepository.findByMesa(mesaAtual);
            if (mesaAtual.getMStatus().equals("OCUPADA") && !mesaDestino.getMStatus().equals("OCUPADA")) {

                mesaAtual.setMStatus("ACTIVE");
                mesaDestino.setMStatus("OCUPADA");
                mesaRepository.save(mesaAtual);
                mesaRepository.save(mesaDestino);

                pedido.setMesa(mesaDestino);
                pedidoRepository.save(pedido);


            }
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
        return new RedirectView("/api/user/mesas");
    }
}
