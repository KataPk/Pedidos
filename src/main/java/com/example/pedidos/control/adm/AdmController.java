package com.example.pedidos.control.adm;


import com.example.pedidos.dtos.*;
import com.example.pedidos.model.entity.*;
import com.example.pedidos.model.repository.*;
import com.example.pedidos.service.CategoriaService;
import com.example.pedidos.service.MesaService;
import com.example.pedidos.service.ProdutoService;
import com.example.pedidos.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/admin")
public class AdmController {

    public static final Logger log = LoggerFactory.getLogger(AdmController.class);

    @Autowired
    MesaRepository mesaRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ContatoRepository contatoRepository;
    private final MesaService mesaService;

    private final UserService userService;
    private final CategoriaService categoriaService;

    private final ProdutoService produtoService;

    public AdmController(MesaService mesaService, UserService userService, CategoriaService categoriaService, ProdutoService produtoService) {
        this.mesaService = mesaService;
        this.userService = userService;

        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
    }


    @GetMapping("/mesas")
    public String Mesas(Model model){
        List<MesaRecordDto> mesas = mesaService.findAll();
        model.addAttribute("mesas", mesas);
    return "Adm/MesasAdm";
    }

    @PostMapping("/Mesa")
    public ResponseEntity<Mesa> createMesa(@RequestBody @Valid MesaRecordDto MesaRecordDto) {
        var Mesa = new Mesa();
        BeanUtils.copyProperties(MesaRecordDto, Mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaRepository.save(Mesa));
    }




    @GetMapping("/funcionarios")
    public String funcionarios(Model model,
                               @AuthenticationPrincipal UserDetails userDetails){
       List<UserRecordDto> users = userService.findAll();

        ERole roleUser = ERole.ROLE_USER;
        ERole roleAdmin = ERole.ROLE_ADMIN;

        String currentUsername = userDetails.getUsername();


        model.addAttribute("currentUser", currentUsername);
        model.addAttribute("users", users);
        model.addAttribute("RoleUser", roleUser);
        model.addAttribute("RoleAdmin", roleAdmin);

        return "Adm/Funcionarios";
    }



    @GetMapping("/categorias")
    public String categorias(Model model){
        List<CategoriaRecordDto>  categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
        return "Adm/CategoriasAdm";
    }

    @PostMapping("/createCategoria")
    public RedirectView createCategoria(@RequestParam ("nome") String nome,
                                        @RequestParam("file") MultipartFile file){

        try {
            // tratativa da imagem
            byte[] image = Base64.getEncoder().encode(file.getBytes());
            String imageBase64 = new String(image);

            // criação da Categoria
            Categoria categoria = new Categoria(
                    nome,
                    imageBase64,
                    "ATIVA"
            );
            categoriaRepository.save(categoria);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/categorias");
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
    public RedirectView createProduto(@RequestParam ("nome") String nome,
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
