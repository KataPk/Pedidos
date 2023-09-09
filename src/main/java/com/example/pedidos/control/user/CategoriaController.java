package com.example.pedidos.control.user;


import com.example.pedidos.dtos.CategoriaRecordDto;
import com.example.pedidos.dtos.PedidoRecordDTO;
import com.example.pedidos.dtos.ProdutoRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.entity.Pedido;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.model.repository.PedidoRepository;
import com.example.pedidos.model.repository.ProdutoRepository;
import com.example.pedidos.service.CategoriaService;
import com.example.pedidos.service.PedidoService;
import com.example.pedidos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user")
public class CategoriaController {

    public static final Logger log = LoggerFactory.getLogger(CategoriaController.class);

   private final CategoriaService categoriaService;

   private final ProdutoService produtoService;

   private final PedidoService pedidoService;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    public CategoriaController(CategoriaService categoriaService, ProdutoService produtoService, PedidoService pedidoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.pedidoService = pedidoService;
    }


    @GetMapping("/{pedidoId}/categorias")
    public String categorias(@PathVariable long pedidoId ,Model model){

        List<PedidoRecordDTO> clientes = pedidoService.findAll();

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);

        List<CategoriaRecordDto> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedido", pedido);
        return "User/Categorias";
    }

    @GetMapping("/{pedidoId}/categoria/{categoriaNome}")
    public String produtoCategoria(@PathVariable String categoriaNome,
                                   @PathVariable long pedidoId,
                                   Model model){

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        Categoria categoria = categoriaRepository.findByNome(categoriaNome);


            List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria.getNome());

            model.addAttribute("categoria", categoria);
            model.addAttribute("produtos", produtos);
            model.addAttribute("pedido", pedido);

            return "User/Produtos";
        }

    }
















