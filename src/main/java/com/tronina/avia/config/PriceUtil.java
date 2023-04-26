package com.tronina.avia.config;

import com.tronina.avia.model.entity.Promo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class PriceUtil {
    @Value("${ticket.comission}")
    private String ticketComission;

    public BigDecimal applyComission(BigDecimal base, boolean needApply) {
        double multiplyer = 1.0;
        if (needApply) {
            multiplyer += Double.parseDouble(ticketComission);
        }
        return base.multiply(BigDecimal.valueOf(multiplyer));
    }

    public BigDecimal applyPromo(BigDecimal base, Promo promo) {
        return base.multiply(BigDecimal.valueOf(promo.getPercent()));
    }
}
