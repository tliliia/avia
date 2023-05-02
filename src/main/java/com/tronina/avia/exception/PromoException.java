package com.tronina.avia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ClientCode.PROMO_NOT_VALID)
public class PromoException extends RuntimeException {
    public PromoException(String title) {
        super(String.format("Promocode %s not valid", title));
    }

}
