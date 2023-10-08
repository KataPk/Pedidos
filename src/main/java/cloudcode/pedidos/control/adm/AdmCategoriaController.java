package cloudcode.pedidos.control.adm;


import cloudcode.pedidos.dtos.CategoriaRecordDto;
import cloudcode.pedidos.dtos.ProdutoRecordDto;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Produto;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import cloudcode.pedidos.model.repository.ProdutoRepository;
import cloudcode.pedidos.service.CategoriaService;
import cloudcode.pedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmCategoriaController {



    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;



    private final CategoriaService categoriaService;

    private final ProdutoService produtoService;


    public AdmCategoriaController(CategoriaService categoriaService, ProdutoService produtoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }


    @GetMapping("/categorias")
    public String categorias(Model model){
        List<CategoriaRecordDto> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "Adm/CategoriasAdm";
    }

    @PostMapping("/createCategoria")
    public RedirectView createCategoria(@RequestParam("nome") String nome,
                                        @RequestParam("file") MultipartFile file){

        try {
            // tratativa da imagem
            byte[] image = Base64.getEncoder().encode(file.getBytes());
            String imageBase64 = new String(image);

            // criação da Categoria
            Categoria categoria = new Categoria(
                    nome,
                    imageBase64,
                    "ACTIVE"
            );
            categoriaRepository.save(categoria);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/categorias");
    }

    @PostMapping("/disableCategoria")
    public RedirectView desativarCategoria(@RequestParam("id") long categoriaId){

        Categoria categoria = categoriaRepository.getReferenceById(categoriaId);
        List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria);

        for (ProdutoRecordDto produto: produtos) {
            Produto produto1 = produtoRepository.getReferenceById(produto.id());
            produto1.setStatusProduto("INACTIVE");
            produtoRepository.save(produto1);
        }





        return new RedirectView("/api/admin/categorias");

    }







}
