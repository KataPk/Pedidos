package com.example.pedidos.control.user;

import com.example.pedidos.control.adm.ProdutoController;
import com.example.pedidos.dtos.ItemPedidoRecordDto;
import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.model.entity.*;
import com.example.pedidos.model.repository.*;
import com.example.pedidos.service.ItemPedidoService;
import com.example.pedidos.service.PedidoService;
import com.example.pedidos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


    public PedidoController(ProdutoService produtoService, PedidoService pedidoService, ItemPedidoService itemPedidoService) {
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
        this.itemPedidoService = itemPedidoService;
    }



    @GetMapping("/listPedidos")
    public String pedidos(Model model){
        List<PedidoRecordDTO> pedidos = pedidoService.findAbertos();
//        for (PedidoRecordDTO pedido : pedidos){
//            long pedidoId = pedido.id();
//            double somaItensPedido = calcularSomaItens(pedidoId);
//            pedido.subTotal(somaItensPedido);
//        }
        model.addAttribute("pedidos", pedidos);

        return "User/Comandas";
    }

//    private double calcularSomaItens(long pedidoId) {
//
//        List<ItemPedidoRecordDto> itensPedido = itemPedidoService.findItensByPedido(pedidoId); // Suponhamos que você tenha um método para buscar os itens de pedido por pedidoId
//
//        double total = 0.0;
//
//        for (ItemPedidoRecordDto item : itensPedido){
//         double valorProduto  = item.produto().getValor();
//         int quant = item.quant();
//         total += (valorProduto*quant);
//        }
//        return  total;
//            }

    @PostMapping("/createPedido")
    public RedirectView createPedido(@RequestParam("clientName") String cliente,
                                                @RequestParam ("button") int  button,
                                                @RequestParam("mesaNum") int mesaNum ,
                                                @AuthenticationPrincipal UserDetails userDetails) throws InterruptedException {



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
    mesa.setmMStatus("OCUPADA");
    mesaRepository.save(mesa);
    long pedidoId = pedido.getId();

    if (button == 1) {
        TimeUnit.SECONDS.sleep(1);
    return new RedirectView("/api/user/mesas");
    } else if (button == 2) {
        return new RedirectView("/api/user/" + pedidoId +"/categorias");
    } else {
        throw new RuntimeException("Botão inválido: " + button);
    }



    }


    @PostMapping("/openPedido")
    public RedirectView  openPedido(@RequestParam("mesaNum") int mesaNum){

        Mesa mesa = mesaRepository.findByNumMesa(mesaNum);
        Pedido pedido = pedidoRepository.findByMesa(mesa);
        long pedidoId = pedido.getId();


        return new RedirectView("/api/user/" + pedidoId +"/categorias");
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
                                @RequestParam("produtoNome") String produtoNome) throws InterruptedException {

    Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
    Produto produto = produtoRepository.findByNome(produtoNome);
    Categoria categoria = produto.getCategoria();
    String categoriaNome = categoria.getNome();
    ItemPedido itemPedido = new ItemPedido(
                produto,
                quant,
                observacoes,
                pedido
    );
    itemPedidoRepository.save(itemPedido);
        TimeUnit.SECONDS.sleep(1);
        return new RedirectView("/api/user/" + pedidoId + "/categoria/" + categoriaNome);



    }





}
