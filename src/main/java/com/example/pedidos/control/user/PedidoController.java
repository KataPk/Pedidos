package com.example.pedidos.control.user;

import com.example.pedidos.dtos.ItemPedidoRecordDto;
import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.dtos.PedidoSubTotalRecordDTO;
import com.example.pedidos.model.entity.*;
import com.example.pedidos.model.repository.*;
import com.example.pedidos.service.ItemPedidoService;
import com.example.pedidos.service.PedidoService;
import com.example.pedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user/pedido")
public class PedidoController {


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

    private final ProdutoService produtoService;

    private final PedidoService pedidoService;
    private final ItemPedidoService itemPedidoService;



    PedidoRecordDTO pedidoRecordDTO;

    ItemPedidoRecordDto itemPedidoRecordDto;

    public PedidoController(ProdutoService produtoService, PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }



    @GetMapping("/Pedidos")
    public String pedidos(Model model){
        List<PedidoSubTotalRecordDTO> pedidos = pedidoService.findPedidosAbertosWithSubtotal();

        model.addAttribute("pedidos", pedidos);

        return "User/Comandas";
    }


    @PostMapping("/createPedido")
    public RedirectView createPedido(@RequestParam("clientName") String cliente,
                                                @RequestParam("mesaId") long mesaId,
                                                @RequestParam ("button") String  button,
                                                @AuthenticationPrincipal UserDetails userDetails) throws Exception {


        try {

            Mesa mesa = mesaRepository.getReferenceById(mesaId);

            User user = userRepository.findByUsername(userDetails.getUsername());

            LocalDateTime dtRegistro = LocalDateTime.now();

            if (mesa.getMStatus().equals("OCUPADA")){
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
                return new RedirectView("/api/user/" + pedidoId +"/categorias");

            } else {
                throw new RuntimeException("Botão inválido: " + button);
            }


        } catch (Exception e) {

            throw new Exception("Error:", e);
        }
    }

    @PostMapping("/openPedido")
    public RedirectView  openPedido(@RequestParam("mesaNum") int mesaNum){

        Mesa mesa = mesaRepository.findByNumMesa(mesaNum);
        Pedido pedido = pedidoRepository.findByMesa(mesa);
        long pedidoId = pedido.getId();


        return new RedirectView("/api/user/" + pedidoId +"/categorias");
    }

    @GetMapping("/{pedidoId}/finalizarPedido")
    public String finalizarPedido(@PathVariable long pedidoId, Model model){

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);


        List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);




        String subTotal = itemPedidoService.getSubTotal(pedidoId);
        model.addAttribute("pedido", pedido);
        model.addAttribute("itens", itens);
        model.addAttribute("subtotal", subTotal);


        return "User/Editar";

    }


    @PostMapping("{pedidoId}/alterarQuant/{id}/{acao}")
    public ResponseEntity<?> alterarQaunt(
                                       @PathVariable("pedidoId") long pedidoId,
                                       @PathVariable ("acao") int acao,
                                       @PathVariable("id") long produtoId

    ) throws InterruptedException {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);

        for (ItemPedidoRecordDto it : itens ) {
            if (it.produto().getId() == produtoId){
                ItemPedido item = itemPedidoRepository.getReferenceById(it.id());
                if (acao == 0) {
                    item.setQuantProduto(it.quant() - 1);
                    itemPedidoRepository.save(item);
                }  else if (acao == 1){
                    item.setQuantProduto(it.quant() + 1);
                    itemPedidoRepository.save(item);
                } else if (acao == 2) {
                    itemPedidoRepository.delete(item);
                }
                break;

            }

        }
        return ResponseEntity.ok().body("preencher");



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
//        mesa.setmMStatus("ABERTA");

        return new RedirectView("/api/user/mesas");
    }



    @PostMapping("/addItem")
    public RedirectView addItem(@RequestParam("quant") int quant,
                                @RequestParam("observacoes") String observacoes,
                                @RequestParam("pedidoId") long pedidoId,
                                @RequestParam("produtoId") long produtoId) throws InterruptedException {

    Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        Produto produto = produtoRepository.getReferenceById(produtoId);
        List<ItemPedidoRecordDto> itens = itemPedidoService.findAllByPedido(pedidoId);

    int controle = 0;
    for (ItemPedidoRecordDto it : itens ) {
        if (it.produto().getId() == produto.getId()){
            ItemPedido item = itemPedidoRepository.getReferenceById(it.id());
            item.setQuantProduto(it.quant() + quant);
            itemPedidoRepository.save(item);
            controle = 1;
            break;
        }
    }
        Categoria categoria = produto.getCategoria();
        String categoriaNome = categoria.getNome();

    if (controle == 0) {



        ItemPedido itemPedido = new ItemPedido(
                produto,
                quant,
                observacoes,
                pedido
        );


        itemPedidoRepository.save(itemPedido);
        TimeUnit.SECONDS.sleep(1);
    }
        return new RedirectView("/api/user/" + pedidoId + "/categoria/" + categoriaNome);



    }





}
