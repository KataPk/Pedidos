package com.example.pedidos.service;


import com.example.pedidos.dtos.MesaRecordDto;
import com.example.pedidos.model.entity.Mesa;
import com.example.pedidos.model.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    @Autowired
    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<MesaRecordDto> findAll(){
        List<Mesa> mesas = mesaRepository.findAll();
    return mesas.stream()
            .map(mesa -> new MesaRecordDto(mesa.getNumMesa(), mesa.getMStatus()))
            .collect(Collectors.toList());
    }






}
