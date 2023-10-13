package cloudcode.pedidos.control.adm;


import cloudcode.pedidos.dtos.CategoriaRecordDto;
import cloudcode.pedidos.dtos.ProdutoRecordDto;
import cloudcode.pedidos.imageUtils.FileUploadUtil;
import cloudcode.pedidos.model.entity.Categoria;
import cloudcode.pedidos.model.entity.Produto;
import cloudcode.pedidos.model.repository.CategoriaRepository;
import cloudcode.pedidos.model.repository.ProdutoRepository;
import cloudcode.pedidos.service.CategoriaService;
import cloudcode.pedidos.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@Transactional
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmProdutoController {

    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;

    private final FileUploadUtil fileUploadUtil;

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    public AdmProdutoController(CategoriaService categoriaService, ProdutoService produtoService, FileUploadUtil fileUploadUtil) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.fileUploadUtil = fileUploadUtil;
    }


    @GetMapping("/produtos")
    public String produtos(Model model) {
        List<ProdutoRecordDto> produtos = produtoService.findAtivos();
        model.addAttribute("produtos", produtos);
        List<CategoriaRecordDto> categorias = categoriaService.findAllAtivos();
        model.addAttribute("categorias", categorias);

        return "Adm/ProdutosAdm";
    }


    @PostMapping("/createProduto")
    public RedirectView createProduto(@RequestParam("nome") String nome,
                                      @RequestParam("descricao") String descricao,
                                      @RequestParam("valor") String valor,
                                      @RequestParam("file") MultipartFile multipartFile,
                                      @RequestParam("categoria") String categoriaId) {

        try {

            File file = fileUploadUtil.convertMultiPartFileToFile(multipartFile);

            ResponseEntity<String> response = fileUploadUtil.upload(file);

            if (response.getStatusCode() == HttpStatus.OK) {
                String imageUrl = fileUploadUtil.getImageUrl(response);

                // Arruma o valor para salvar
                valor = valor.replace(",", ".");
                double valorDouble = Double.parseDouble(valor);

                // busca a categoria
                Categoria categoria = categoriaRepository.getReferenceById(Long.valueOf(categoriaId));
                // criação do Produto
                Produto produto = new Produto(
                        nome,
                        descricao,
                        valorDouble,
                        imageUrl,
                        categoria,
                        "ACTIVE"
                );
                produtoRepository.save(produto);

                return new RedirectView("/api/admin/produtos");
            } else {
                throw new RuntimeException("Algo deu errado no upload para o host");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/editProduto")
    public RedirectView editProduto(
            @RequestParam("produtoId") long produtoId,
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("valor") String valor,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam("categoria") long categoriaId) {

        try {
            Produto produto = produtoRepository.getReferenceById(produtoId);

            // tratativa da imagem
            if (!multipartFile.isEmpty()) {
                File file = fileUploadUtil.convertMultiPartFileToFile(multipartFile);

                ResponseEntity<String> response = fileUploadUtil.upload(file);

                if (response.getStatusCode() == HttpStatus.OK) {
                    String imageUrl = fileUploadUtil.getImageUrl(response);

                    produto.setImagem(imageUrl);
                } else {
                    throw new RuntimeException("Algo deu errado no upload para o host");
                }

            }

            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);

            // busca a categoria
            Categoria categoria = categoriaRepository.getReferenceById(categoriaId);
            // edição do Produto
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setValor(valorDouble);

            produto.setCategoria(categoria);

            produtoRepository.save(produto);

            return new RedirectView("/api/admin/produtos");

        } catch (IOException e) {
            throw new RuntimeException("Algo deu errado no upload para o host");
        }

    }

    @PostMapping("/disableProduto")
    public RedirectView disableProduto(
            @RequestParam("produtoId") long produtoId
    ) {
        try {
            Produto produto = produtoRepository.getReferenceById(produtoId);
            produto.setStatusProduto("INACTIVE");
            produtoRepository.save(produto);

            return new RedirectView("/api/admin/produtos");

        } catch (Exception e) {
            throw new RuntimeException("Algo deu errado no upload para o host");
        }

    }
}
