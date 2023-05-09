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
    private final PriceUtil priceUtil;

    public void makeOrder(Ticket ticket, Customer customer, String title) {
        Promo promo = promoService.getByTitle(title);
        BigDecimal price =
                priceUtil.applyPromo(
                    priceUtil.applyComission(
                            ticket.getPrice(),
                            ticket.isCommission()),
                    promo);
        promoService.changeActualUsage(promo.getTitle());
        repository.save(TicketOrder.builder()
                .ticket(ticket)
                .totalPrice(price)
                .build());
    }

    public boolean wasSold(Ticket ticket) {
        return repository.findByTicketId(ticket.getId()).isPresent();
    }
}
