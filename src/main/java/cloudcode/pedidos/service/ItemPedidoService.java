package cloudcode.pedidos.service;


import cloudcode.pedidos.dtos.ItemPedidoRecordDto;
import cloudcode.pedidos.model.entity.ItemPedido;
import cloudcode.pedidos.model.repository.ItemPedidoRepository;
import cloudcode.pedidos.model.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    private final PedidoRepository pedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository, PedidoRepository pedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<ItemPedidoRecordDto> findAllByPedido(long pedidoId) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedidoRepository.getReferenceById(pedidoId));
        return itens.stream()
                .map(item -> new ItemPedidoRecordDto(item.getId(), item.getProduto(), item.getQuantProduto(), item.getPedido()))
                .collect(Collectors.toList());
    }

    public String getSubTotal(long pedidoId) {
        double subTotal = 0;
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedidoRepository.getReferenceById(pedidoId));
        for (ItemPedido item : itens) {

            int quant = item.getQuantProduto();
            double productValue = item.getProduto().getValor();
            double valorItem = quant * productValue;

            subTotal += valorItem;

        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(subTotal).replace(".", ",");
    }


}
