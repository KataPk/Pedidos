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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmProdutoController {

    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    ProdutoRepository produtoRepository;

    public AdmProdutoController(CategoriaService categoriaService, ProdutoService produtoService) {
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }


    @GetMapping("/produtos")
    public String produtos(Model model) {
        List<ProdutoRecordDto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        List<CategoriaRecordDto> categorias = categoriaService.findAllAtivos();
        model.addAttribute("categorias", categorias);


        return "Adm/ProdutosAdm";
    }

    @Transactional
    @PostMapping("/createProduto")
    public RedirectView createProduto(@RequestParam("nome") String nome,
                                      @RequestParam("descricao") String descricao,
                                      @RequestParam("valor") String valor,
                                      @RequestParam("file") MultipartFile file,
                                      @RequestParam("categoria") String categoriaId) {

        try {
            // tratativa da imagem
//            byte[] image = Base64.getEncoder().encode(file.getBytes());
//            String imageBase64 = new String(image);

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
//                    imageBase64,
                    fileName,
                    categoria,
                    "ACTIVE"
            );
            produtoRepository.save(produto);

            String uploadDir = "uploads/images/produtos/" + produto.getCategoria().getId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/produtos");
    }

    @Transactional
    @PostMapping("/editProduto")
    public RedirectView editProduto(
            @RequestParam("produtoId") long produtoId,
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("valor") String valor,
            @RequestParam("file") MultipartFile file,
            @RequestParam("categoria") String categoriaId) {

        try {
            // tratativa da imagem
//            byte[] image = Base64.getEncoder().encode(file.getBytes());
//            String imageBase64 = new String(image);
            Produto produto = produtoRepository.getReferenceById(produtoId);
            String imagem = produto.getImagem();

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

            // converter o valor para BigDecimal
            valor = valor.replace(",", ".");
            double valorDouble = Double.parseDouble(valor);

            // busca a categoria
            Categoria categoria = categoriaRepository.getReferenceById(Long.valueOf(categoriaId));
            // criação do Produto
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setValor(valorDouble);
            produto.setImagem(fileName);
            produto.setCategoria(categoria);

            produtoRepository.save(produto);

//            Criar um metodo de deletar a imagem anterior
            if (imagem != null && !imagem.isEmpty()) {
                String uploadDirAnterior = "uploads/images/produtos/" + produto.getCategoria().getId();
                FileUploadUtil.deleteFile(uploadDirAnterior, imagem);
            }
            String uploadDir = "/uploads/images/produtos/" + categoria.getId();
            FileUploadUtil.saveFile(uploadDir, fileName, file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/produtos");
    }

    @Transactional
    @PostMapping("/disableProduto")
    public RedirectView disableProduto(
            @RequestParam("id") long produtoId
    ) {
        Produto produto = produtoRepository.getReferenceById(produtoId);
        produto.setStatusProduto("INACTIVE");
        produtoRepository.save(produto);
        String imagem = produto.getImagem();
        if (imagem != null && !imagem.isEmpty()) {
            String uploadDirAnterior = "uploads/images/produtos/" + produto.getCategoria().getId();
            FileUploadUtil.deleteFile(uploadDirAnterior, imagem);
        }


        return new RedirectView("/api/admin/produtos");

    }
}
