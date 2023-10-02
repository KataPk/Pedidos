package com.example.pedidos.control.adm;


import com.example.pedidos.dtos.CategoriaRecordDto;
import com.example.pedidos.dtos.ProdutoRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.entity.Produto;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.model.repository.ProdutoRepository;
import com.example.pedidos.service.CategoriaService;
import com.example.pedidos.service.ProdutoService;
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
public class AdmProdutoController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;


    private final CategoriaService categoriaService;

    private final ProdutoService produtoService;

    public AdmProdutoController(CategoriaService categoriaService, ProdutoService produtoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }


    @GetMapping("/produtos")
    public String produtos(Model model){
        List<ProdutoRecordDto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        List<CategoriaRecordDto>  categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);


        return "Adm/ProdutosAdm";
    }
    @PostMapping("/createProduto")
    public RedirectView createProduto(@RequestParam("nome") String nome,
                                      @RequestParam("descricao") String descricao,
                                      @RequestParam("valor") String valor,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("categoria") String  categoriaId){

        try {
            // tratativa da imagem
            byte[] image = Base64.getEncoder().encode(file.getBytes());
            String imageBase64 = new String(image);

            // converter o valor para BigDecimal
            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);

            // busca a categoria
            Categoria categoria = categoriaRepository.getReferenceById(Long.valueOf(categoriaId));
            // criação do Produto
            Produto produto = new Produto(
                    nome,
                    descricao,
                    valorDouble,
                    imageBase64,
                    categoria
            );
            produtoRepository.save(produto);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/produtos");
    }

}
