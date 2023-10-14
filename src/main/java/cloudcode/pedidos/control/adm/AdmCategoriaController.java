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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmCategoriaController {


    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    private final FileUploadUtil fileUploadUtil;

    public AdmCategoriaController(CategoriaService categoriaService, ProdutoService produtoService, FileUploadUtil fileUploadUtil) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.fileUploadUtil = fileUploadUtil;
    }


    @GetMapping("/categorias")
    public String categorias(Model model) {
        List<CategoriaRecordDto> categorias = categoriaService.findAllAtivos();
        model.addAttribute("categorias", categorias);
        return "Adm/CategoriasAdm";
    }

    @PostMapping("/createCategoria")
    public RedirectView createCategoria(@RequestParam("nome") String nome,
                                        @RequestParam("file") MultipartFile multipartFile) {


        try {

            File file = fileUploadUtil.convertMultiPartFileToFile(multipartFile);

            ResponseEntity<String> response = fileUploadUtil.upload(file);

            if (response.getStatusCode() == HttpStatus.OK) {
                String imageUrl = fileUploadUtil.getImageUrl(response);

                // criação da Categoria
                Categoria categoria = new Categoria(
                        nome,
                        imageUrl,
                        "ACTIVE"
                );
                categoriaRepository.save(categoria);

                return new RedirectView("/api/admin/categorias");
            } else {
                throw new RuntimeException("Algo deu errado no upload para o host");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/editCategoria")
    public RedirectView editCategoria(
            @RequestParam("categoriaId") long categoriaId,
            @RequestParam("nome") String nome,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile) {

        try {

            Categoria categoria = categoriaRepository.getReferenceById(categoriaId);

            if (multipartFile != null && !multipartFile.isEmpty()) {
                File file = fileUploadUtil.convertMultiPartFileToFile(multipartFile);

                ResponseEntity<String> response = fileUploadUtil.upload(file);

                if (response.getStatusCode() == HttpStatus.OK) {
                    String imageUrl = fileUploadUtil.getImageUrl(response);


                    categoria.setImagem(imageUrl);
                } else {
                    throw new RuntimeException("Algo deu errado no upload para o host");
                }

            }

            categoria.setNome(nome);
            categoriaRepository.save(categoria);
            return new RedirectView("/api/admin/categorias");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/disableCategoria")
    public RedirectView desativarCategoria(@RequestParam("categoriaId") long categoriaId) {

        Categoria categoria = categoriaRepository.getReferenceById(categoriaId);
        List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria);

        for (ProdutoRecordDto produto : produtos) {
            Produto produto1 = produtoRepository.getReferenceById(produto.id());
            produto1.setStatusProduto("INACTIVE");
            produtoRepository.save(produto1);

        }


        categoria.setStatusCategoria("INACTIVE");
        categoriaRepository.save(categoria);


        return new RedirectView("/api/admin/categorias");

    }


}
