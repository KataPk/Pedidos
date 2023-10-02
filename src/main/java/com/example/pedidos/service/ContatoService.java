package com.example.pedidos.service;

import com.example.pedidos.model.entity.Contato;
import com.example.pedidos.model.entity.User;
import com.example.pedidos.model.repository.ContatoRepository;
import com.example.pedidos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContatoService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ContatoRepository contatoRepository;
    public List<Contato> contatoList(User user){
       return contatoRepository.findByUser(user);
    }




}
