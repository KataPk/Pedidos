package com.example.pedidos.control.adm;


import com.example.pedidos.dtos.CategoriaRecordDto;
import com.example.pedidos.model.entity.Categoria;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.service.CategoriaService;
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




    private final CategoriaService categoriaService;

    public AdmCategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
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
                    "ATIVA"
            );
            categoriaRepository.save(categoria);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new RedirectView("/api/admin/categorias");
    }






}
