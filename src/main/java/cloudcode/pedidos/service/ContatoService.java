package cloudcode.pedidos.service;


import cloudcode.pedidos.model.entity.Contato;
import cloudcode.pedidos.model.entity.User;
import cloudcode.pedidos.model.repository.ContatoRepository;
import cloudcode.pedidos.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
