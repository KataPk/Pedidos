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
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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


    public AdmCategoriaController(CategoriaService categoriaService, ProdutoService produtoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }


    @GetMapping("/categorias")
    public String categorias(Model model) {
        List<CategoriaRecordDto> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "Adm/CategoriasAdm";
    }

    @Transactional
    @PostMapping("/createCategoria")
    public RedirectView createCategoria(@RequestParam("nome") String nome,
                                        @RequestParam("file") MultipartFile file) throws IOException {

        try {
            // tratativa da imagem
            String uniqueFileName = UUID.randomUUID().toString();
            String fileName = "";
            // Obtém a extensão do arquivo original (se necessário)
            String originalFileName = file.getOriginalFilename();
            String fileExtension = "";

            if (originalFileName != null) {
                int lastDotIndex = originalFileName.lastIndexOf(".");
                if (lastDotIndex != -1) {
                    fileExtension = originalFileName.substring(lastDotIndex);
                }
                fileName = uniqueFileName + fileExtension;
            } else {
                fileName = StringUtils.cleanPath(file.getOriginalFilename());
            }

//            método com base 64
//            byte[] image = Base64.getEncoder().encode(file.getBytes());
//            String imageBase64 = new String(image);

            // criação da Categoria
            Categoria categoria = new Categoria(
                    nome,
//                    imageBase64,
                    fileName,
                    "ACTIVE"
            );
            categoriaRepository.save(categoria);
            String uploadDir = "uploads/images/categorias/" + categoria.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/categorias");
    }

    @Transactional
    @PostMapping("/editCategoria")
    public RedirectView editCategoria(
            @RequestParam("categoriaId") long categoriaId,
            @RequestParam("nome") String nome,
            @Param("file") MultipartFile file) throws IOException {

        try {

            Categoria categoria = categoriaRepository.getReferenceById(categoriaId);

            byte[] image = Base64.getEncoder().encode(file.getBytes());
            String imageBase64 = new String(image);

//            String imagem = categoria.getImagem();
//            String fileExtension = "";
//            String fileName = "";
//            if (file != null) {
//                // tratativa da imagem
//                String uniqueFileName = UUID.randomUUID().toString();
//                // Obtém a extensão do arquivo original (se necessário)
//                String originalFileName = file.getOriginalFilename();
//
//                if (originalFileName != null) {
//                    int lastDotIndex = originalFileName.lastIndexOf(".");
//                    if (lastDotIndex != -1) {
//                        fileExtension = originalFileName.substring(lastDotIndex);
//                    }
//                    fileName = uniqueFileName + fileExtension;
//                } else {
//                    fileName = StringUtils.cleanPath(file.getOriginalFilename());
//                }
//                categoria.setImagem(fileName);
//            }


            categoria.setNome(nome);
            categoria.setImagem(imageBase64);

            categoriaRepository.save(categoria);

//            if (imagem != null && !imagem.isEmpty()) {
//                String uploadDirAnterior = "uploads/images/categorias/" + categoria.getId();
//                FileUploadUtil.deleteFile(uploadDirAnterior, imagem);
//            }
//
//            String uploadDir = "/static/uploads/images/categorias/" + categoria.getId();
//            FileUploadUtil.saveFile(uploadDir, fileName, file);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/categorias");
    }

    @Transactional
    @PostMapping("/disableCategoria")
    public RedirectView desativarCategoria(@RequestParam("categoriaId") long categoriaId) {

        Categoria categoria = categoriaRepository.getReferenceById(categoriaId);
        List<ProdutoRecordDto> produtos = produtoService.findByCategoria(categoria);

        for (ProdutoRecordDto produto : produtos) {
            Produto produto1 = produtoRepository.getReferenceById(produto.id());
            produto1.setStatusProduto("INACTIVE");
            produtoRepository.save(produto1);

            String imagem = produto.imagem();
            if (imagem != null && !imagem.isEmpty()) {
                String uploadDirAnterior = "uploads/images/produtos/" + produto.categoria().getId();
                FileUploadUtil.deleteFile(uploadDirAnterior, imagem);
            }
        }

        categoria.setStatusCategoria("INACTIVE");
        categoriaRepository.save(categoria);

        String imagem = categoria.getImagem();
        if (imagem != null && !imagem.isEmpty()) {
            String uploadDirAnterior = "uploads/images/categorias/" + categoria.getId();
            FileUploadUtil.deleteFile(uploadDirAnterior, imagem);
        }

        return new RedirectView("/api/admin/categorias");

    }


}
