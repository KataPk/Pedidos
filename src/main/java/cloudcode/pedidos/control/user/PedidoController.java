package cloudcode.pedidos.control.user;


import cloudcode.pedidos.dtos.ItemPedidoRecordDto;
import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.dtos.PedidoSubTotalRecordDTO;
import cloudcode.pedidos.model.entity.*;
import cloudcode.pedidos.model.repository.*;
import cloudcode.pedidos.service.ItemPedidoService;
import cloudcode.pedidos.service.MesaService;
import cloudcode.pedidos.service.PedidoService;
import cloudcode.pedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user/pedido")
public class PedidoController {


    private final MesaService mesaService;
    private final ProdutoService produtoService;
    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    MesaRepository mesaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProdutoRepository produtoRepository;
    @Autowired
    ItemPedidoRepository itemPedidoRepository;
    @Autowired
    CategoriaRepository categoriaRepository;


    public PedidoController(MesaService mesaService, ProdutoService produtoService,
                            PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.mesaService = mesaService;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }



    @GetMapping("/Pedidos")
    public String pedidos(Model model) {
        List<PedidoSubTotalRecordDTO> pedidos = pedidoService.findPedidosAbertosWithSubtotal();
        List<MesaRecordDto> mesas = mesaService.findAll();
        model.addAttribute("mesas", mesas);
        model.addAttribute("pedidos", pedidos);

        return "User/Comandas";
    }


    @Transactional
    @PostMapping("/createPedido")
    public RedirectView createPedido(@RequestParam("clientName") String cliente,
                                     @RequestParam("mesaId") long mesaId,
                                     @RequestParam("button") String button,
                                     @AuthenticationPrincipal UserDetails userDetails) throws Exception {


        try {

            Mesa mesa = mesaRepository.getReferenceById(mesaId);

            User user = userRepository.findByUsername(userDetails.getUsername());

            LocalDateTime dtRegistro = LocalDateTime.now();

            if (mesa.getMStatus().equals("OCUPADA")) {
                throw new RuntimeException("Mesa Ocupada, recarregue a página e tente novamente");
            }

            Pedido pedido = new Pedido(
                    cliente,
                    dtRegistro,
                    null,
                    user,
                    mesa,
                    "ABERTO"

            );
            pedidoRepository.save(pedido);

//        muda o status da mesa para ocupada
            if (mesa.getMStatus().equals("ACTIVE")) {
                mesa.setMStatus("OCUPADA");
                mesaRepository.save(mesa);
            }
            long pedidoId = pedido.getId();
            int buttonValue = Integer.parseInt(button);
            if (buttonValue == 1) {
                return new RedirectView("/api/user/mesas");
            }
            if (buttonValue == 2) {
                return new RedirectView("/api/user/" + pedidoId + "/categorias");

            } else {
                throw new RuntimeException("Botão inválido: " + button);
            }


        } catch (Exception e) {

            throw new Exception("Error:", e);
        }
    }

    @PostMapping("/openPedido")
    public RedirectView openPedido(@RequestParam("mesaNum") int mesaNum) {

        Mesa mesa = mesaRepository.findByNumMesa(mesaNum);
        Pedido pedido = pedidoRepository.findByMesaAndStatusPedido(mesa, "ABERTO");
        long pedidoId = pedido.getId();

        if (pedido.getStatusPedido().equals("ABERTO")) {
            return new RedirectView("/api/user/" + pedidoId + "/categorias");
        }
        return new RedirectView("/api/user/mesas");
    }

    @GetMapping("/{pedidoId}/finalizarPedido")
    public String finalizarPedido(@PathVariable long pedidoId, Model model) {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);


        List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);


        String subTotal = itemPedidoService.getSubTotal(pedidoId);
        model.addAttribute("pedido", pedido);
        model.addAttribute("itens", itens);
        model.addAttribute("subtotal", subTotal);


        return "User/Editar";

    }


    @PostMapping("/alterarQuant")
    public ResponseEntity<?> alterarQuant(
            @RequestParam("itemId") long itemId,
            @RequestParam("acao") int acao

    ) {


        ItemPedido item = itemPedidoRepository.findById(itemId).orElseThrow(null);
        Pedido pedido = item.getPedido();
        if (!pedido.getStatusPedido().equals("FECHADO")) {

            if (item != null) {
                if (acao == 0) {
                    item.setQuantProduto(item.getQuantProduto() - 1);
                } else if (acao == 1) {
                    item.setQuantProduto(item.getQuantProduto() + 1);

                } else {
                    return ResponseEntity.badRequest().build();
                }

                itemPedidoRepository.save(item);
                return ResponseEntity.ok().build();

            }


            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/removeItem")
    public ResponseEntity<?> removeItem(
            @RequestParam("ItemId") long itemId

    ) {


        ItemPedido item = itemPedidoRepository.findById(itemId).orElseThrow(null);
        Pedido pedido = item.getPedido();
        if (!pedido.getStatusPedido().equals("FECHADO")) {
            if (item != null) {
                itemPedidoRepository.delete(item);

                return ResponseEntity.ok().build();
            }


            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Transactional
    @PostMapping("/addItem")
    public RedirectView addItem(@RequestParam("quant") int quant,
                                @RequestParam("pedidoId") long pedidoId,
                                @RequestParam("produtoId") long produtoId) {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        Produto produto = produtoRepository.getReferenceById(produtoId);
        List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);

        int controle = 0;
        for (ItemPedidoRecordDto it : itens) {
            if (it.produto().getId() == produto.getId()) {
                ItemPedido item = itemPedidoRepository.getReferenceById(it.id());
                item.setQuantProduto(it.quant() + quant);
                itemPedidoRepository.save(item);
                controle = 1;
                break;
            }
        }
        Categoria categoria = produto.getCategoria();

        if (controle == 0) {


            ItemPedido itemPedido = new ItemPedido(
                    produto,
                    quant,
                    pedido
            );


            itemPedidoRepository.save(itemPedido);

        }
        return new RedirectView("/api/user/" + pedidoId + "/categoria/" + categoria.getId());


    }

    @Transactional
    @PostMapping("/closePedido")
    public RedirectView closePedido(@RequestParam("pedidoId") long pedidoId) {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        Mesa mesa = pedido.getMesa();

        LocalDateTime dateTime = LocalDateTime.now();
        pedido.setStatusPedido("FECHADO");
        pedido.setDtFechamento(dateTime);
        if (mesa.getMStatus().equals("OCUPADA")) {
           mesa.setMStatus("ACTIVE");
            mesaRepository.save(mesa);

        }
        pedidoRepository.save(pedido);

        return new RedirectView("/api/user/mesas");
    }

    @Transactional
    @PostMapping("/removePedido")
    public ResponseEntity<?> deletePedido(@RequestParam("pedidoId") long pedidoId){

try {
    Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
    List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);
    for (ItemPedidoRecordDto it : itens) {
        ItemPedido item = itemPedidoRepository.getReferenceById(it.id());
        itemPedidoRepository.delete(item);
    }

    Mesa mesa = pedido.getMesa();
    mesa.setMStatus("ACTIVE");
    mesaRepository.save(mesa);

    pedidoRepository.delete(pedido);


    return ResponseEntity.ok().build();
} catch (Exception e){
    return ResponseEntity.badRequest().body("Error: " + e);

}



    }


}
