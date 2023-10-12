package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.MesaRecordDto;
import cloudcode.pedidos.model.entity.Mesa;
import cloudcode.pedidos.model.repository.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
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

    public List<MesaRecordDto> findAllMesas() {
        List<Mesa> mesas = mesaRepository.findAllByMStatusNotOrderByNumMesa("DELETADA");
        return mesas.stream()
                .map(mesa -> new MesaRecordDto(mesa.getId(), mesa.getNumMesa(), mesa.getMStatus()))
                .collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 30000)
    public void updateMesasView() {

        mesaRepository.updateMesasView();
    }


}
