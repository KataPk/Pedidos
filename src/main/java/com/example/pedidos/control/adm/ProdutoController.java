package com.example.pedidos.control.adm;


import com.example.pedidos.dtos.ProdutoRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.entity.Produto;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.model.repository.ProdutoRepository;
import com.example.pedidos.service.CategoriaService;
import com.example.pedidos.service.ProdutoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class ProdutoController {

    public static final Logger log = LoggerFactory.getLogger(ProdutoController.class);
    CategoriaService categoriaService;

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }


//    @PostMapping("/createProduto")
//    public RedirectView createProduct(@RequestParam ("nome") String nome,
//                                      @RequestParam ("descricao") String descricao,
//                                      @RequestParam ("valor") String valor,
//                                      @RequestParam("file") MultipartFile file,
//                                      @RequestParam("categoria") long categoria1
//                                ){
//
//
//
//        try {
//            // tratativa da imagem
//            byte[] image = Base64.getEncoder().encode(file.getBytes());
//            String imageBase64 = new String(image);
//
//            // conversão do valor
//            BigDecimal valorDecimal = BigDecimal.valueOf(Double.parseDouble(valor));
//
//            // busca da categoria
//            //                      getReference é indicado para situações de relação onde não puxa dados do banco
//            Categoria categoria = categoriaRepository.getReferenceById(categoria1);
//
//            // criação do produto
//            Produto produto = new Produto(
//                    nome,
//                    descricao,
//                    valorDecimal,
//                    imageBase64,
//                    categoria
//                    );
//            produtoRepository.save(produto);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    return new RedirectView();
//    }

//    @GetMapping("/produtos")
//    public ResponseEntity<List<Produto>> getAllProdutos() {
//        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.findAll());
//    }

    @GetMapping("/produto/{id}")
    public ResponseEntity<Object> getOneProduto(@PathVariable(value = "id") long id) {

        Optional<Produto> produto0 = produtoRepository.findById(id);
        return produto0.<ResponseEntity<Object>>map(produto -> ResponseEntity.status(HttpStatus.OK).body(produto)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("produto não encontrado"));
    }


    @PutMapping("/produto/{id}")
    public ResponseEntity<Object> updateproduto(@PathVariable(value = "id") long id,
                                                    @RequestBody @Valid ProdutoRecordDto produtoRecordDto) {

        Optional<Produto> produto0 = produtoRepository.findById(id);
        if (produto0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        var produtoModel = produto0.get();
        BeanUtils.copyProperties(produtoRecordDto, produtoModel);
        return ResponseEntity.status(HttpStatus.OK).body(produtoRepository.save(produtoModel));
    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<Object> deleteproduto(@PathVariable(value = "id") long id) {
        Optional<Produto> produto0 = produtoRepository.findById(id);
        if (produto0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
        produtoRepository.delete(produto0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Produto deletado");
    }








}


