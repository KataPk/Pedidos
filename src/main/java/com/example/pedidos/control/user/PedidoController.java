package com.example.pedidos.control.user;

import com.example.pedidos.control.adm.ProdutoController;
import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.model.entity.Mesa;
import com.example.pedidos.model.entity.Pedido;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.MesaRepository;
import com.example.pedidos.model.repository.PedidoRepository;
import com.example.pedidos.model.repository.UserRepository;
import com.example.pedidos.service.PedidoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;

@Controller
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user/pedido")
public class PedidoController {

    public static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    UserRepository userRepository;



    private final PedidoService pedidoService;



    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }


    @PostMapping("/createPedido")
    public RedirectView createPedido(@RequestParam("clientName") String cliente,
                                     @RequestParam("mesaNum") int mesaNum ,
                                     @AuthenticationPrincipal UserDetails userDetails){



        PedidoRecordDTO pedidoDTO;


        Mesa mesa = mesaRepository.findByNumMesa(mesaNum);

       User funcionario = userRepository.findByUsername(userDetails.getUsername());

        LocalDateTime dtRegistro = LocalDateTime.now();

        Pedido pedido = new Pedido(
                cliente,
                dtRegistro,
                null,
                funcionario,
                mesa,
                "ABERTO"

        );
        pedidoRepository.save(pedido);

//        muda o status da mesa para ocupada
        mesa.setmStatus("OCUPADA");
        mesaRepository.save(mesa);
        long pedidoId = pedido.getId();

        return new RedirectView("/api/user/" + pedidoId + "/categorias");
    }


    @PostMapping("/closePedido")
    public RedirectView closePedido(){

//    Pesquisar a edição
//        PedidoRecordDTO pedidoDTO;
//
//        Mesa mesa = mesaRepository.findByNumMesa(mesaNum);
//
//        User funcionario = userRepository.findByUsername(userDetails.getUsername());
//
//        LocalDateTime dtRegistro = LocalDateTime.now();
//
//        Pedido pedido = new Pedido(
//                cliente,
//                dtRegistro,
//                null,
//                funcionario,
//                mesa,
//                "ABERTO"
//
//        );
//
//        pedidoRepository.save(pedido);
//
//        mesa.setmStatus("ABERTA");

        return new RedirectView("/api/user/mesas");
    }


}
