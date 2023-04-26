package com.tronina.avia.service.impl;

import com.tronina.avia.config.PriceUtil;
import com.tronina.avia.model.entity.Customer;
import com.tronina.avia.model.entity.Promo;
import com.tronina.avia.model.entity.Ticket;
import com.tronina.avia.model.entity.TicketOrder;
import com.tronina.avia.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository repository;
    private final PromoService promoService;

    public Optional<TicketOrder> findByid(Long id) {
        return repository.findById(id);
    }

    public void makeOrder(Ticket ticket, Customer customer, String title) {
        Optional<Promo> optionalPromo = promoService.findByTitle(title);
        BigDecimal price = PriceUtil.applyPromo(
                PriceUtil.applyComission(ticket.getPrice(), ticket.isCommission()),
                optionalPromo);

        repository.save(TicketOrder.builder()
                .ticket(ticket)
                .customer(customer)
                .totalPrice(price)
                .build());
    }
}
