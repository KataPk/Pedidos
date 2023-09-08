package com.example.pedidos.control.user;


import com.example.pedidos.dtos.CategoriaRecordDto;
import com.example.pedidos.model.repository.CategoriaRepository;
import com.example.pedidos.service.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600, allowCredentials = "false")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping("/api/user")
public class CategoriaController {

    public static final Logger log = LoggerFactory.getLogger(CategoriaController.class);

   private final CategoriaService categoriaService;

    @Autowired
    CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }


    @GetMapping("/categorias")
    public String categorias(Model model){
        List<CategoriaRecordDto> categorias = categoriaService.findAll();
        model.addAttribute("categorias", categorias);
       String  pageName = "Mesas";
        model.addAttribute("pageName", pageName);
        return "User/Categorias";
    }













}


