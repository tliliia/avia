package com.tronina.avia.config;

import com.tronina.avia.model.entity.Promo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

public class PriceUtil {
    @Value("${ticket.comission}")
    private static String ticketComission;

    public static BigDecimal applyComission(BigDecimal base, boolean needApply) {
        double multiplyer = 1.0;
        if (needApply) {
            multiplyer += Double.parseDouble(ticketComission);
        }
        return base.multiply(BigDecimal.valueOf(multiplyer));
    }

    public static BigDecimal applyPromo(BigDecimal base, Promo promo) {
        return base.multiply(BigDecimal.valueOf(promo.getPercent()));
    }
}
