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

//            REVER DPS

//    public void ativarMesaInativa(){
//        List<Mesa> mesasInativas = mesaRepository.findByMStatus("INACTIVE");
//
//        for (Mesa mesa : mesasInativas){
//            // Verifique se há mesas ativas com o mesmo número
//            List<Mesa> mesasAtivasComMesmoNumero = mesaRepository.findByNumMesaAndMStatusNot(mesa.getNumMesa(), "INATIVA");
//
//            if (mesasAtivasComMesmoNumero.isEmpty()){
//                mesa.setMStatus("ATIVADA");
//            } else {
//                Integer novoNum = obterNovNum();
//                mesa.setNumMesa(novoNum);
//                mesa.setMStatus("ATIVADA");
//
//            }
//            mesaRepository.save(mesa);
//
//        }
//
//
//    }


    public List<MesaRecordDto> findAllAtivos() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusIsNot("INACTIVE");
         return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }

//private Integer obterNovNum(){
//
//        List<Integer> numeroMesas = mesaRepository.findNumeroMesasAtivas();
//
//        for (int i = 1; i<= 1000; i++){
//            if (!numeroMesas.contains(i)){
//                return i;
//            }
//        }
//        throw new RuntimeException("Erro ao encontrar número da mesa");
//}


}
