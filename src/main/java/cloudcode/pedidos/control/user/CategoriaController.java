package cloudcode.pedidos.control.user;


import cloudcode.pedidos.dtos.CategoriaRecordDto;
import cloudcode.pedidos.dtos.PedidoRecordDTO;
import cloudcode.pedidos.dtos.ProdutoRecordDto;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Pedido;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import cloudcode.pedidos.model.repository.PedidoRepository;
import cloudcode.pedidos.model.repository.ProdutoRepository;
import cloudcode.pedidos.service.CategoriaService;
import cloudcode.pedidos.service.PedidoService;
import cloudcode.pedidos.service.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String categorias(@PathVariable long pedidoId, Model model) {

        List<PedidoRecordDTO> clientes = pedidoService.findAbertos();

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);

        List<CategoriaRecordDto> categorias = categoriaService.findAllAtivos();
        model.addAttribute("categorias", categorias);
        model.addAttribute("clientes", clientes);
        model.addAttribute("pedido", pedido);
        return "User/Categorias";
    }

    @GetMapping("/{pedidoId}/categoria/{categoriaId}")
    public String produtoCategoria(@PathVariable long categoriaId,
                                   @PathVariable long pedidoId,
                                   Model model) {

        Pedido pedido = pedidoRepository.getReferenceById(pedidoId);
        Categoria categoria = categoriaRepository.getReferenceById(categoriaId);


        List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria);

        model.addAttribute("categoria", categoria);
        model.addAttribute("produtos", produtos);
        model.addAttribute("pedido", pedido);

        return "User/Produtos";
    }

}
















