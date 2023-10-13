package cloudcode.pedidos.control.adm;

import cloudcode.pedidos.dtos.CategoriaRecordDto;
import cloudcode.pedidos.dtos.ProdutoRecordDto;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import cloudcode.pedidos.service.CategoriaService;
import cloudcode.pedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//            CLASSE PARA TESTES DE NÍVEL DE ACESSO
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/test")
public class TestController {

//    public static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final CategoriaService categoriaService;

    private final ProdutoService produtoService;

    @Autowired
    CategoriaRepository categoriaRepository;

    public TestController(CategoriaService categoriaService, ProdutoService produtoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @GetMapping("/categoria")
    public String test() {

        List<CategoriaRecordDto> categorias = categoriaService.findAllAtivos();
        long id = 20;
        Categoria categoria = categoriaRepository.getReferenceById(id);
        List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria);

        return "teste";


    }

}
