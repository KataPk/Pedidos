package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    @Autowired
    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    public List<MesaRecordDto> findAll() {
        List<Mesa> mesas = mesaRepository.findAll();
        mesas.sort(Comparator.comparingInt(Mesa::getNumMesa));

        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }


    public List<MesaRecordDto> findAllAtivos() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusNotOrderByNumMesa("INACTIVE");
        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }

    public List<MesaRecordDto> findAllInativos() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusOrderByNumMesa("INACTIVE");
        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
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
