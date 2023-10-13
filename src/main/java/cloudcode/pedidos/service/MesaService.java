package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.repository.MesaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }


    public List<MesaRecordDto> findAllAtivos() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusIsOrderByNumMesa("ACTIVE");
        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }

    public List<MesaRecordDto> findAllMesas() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusNotOrderByNumMesa("DELETADA");
        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }


}
