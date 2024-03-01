package com.paymentrecommendation.service.validator;

import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.models.Cart;

import java.util.Objects;

public class CartValidatorService {
    public void validateCartLineOfBusiness(LineOfBusiness lineOfBusiness) {
        if (Objects.isNull(lineOfBusiness)) {
            throw new RuntimeException("The line of business is not supported");
        }
    }

    public boolean validateCartDetails(Cart cart) {
        return Objects.nonNull(cart.getCartDetail());
    }

    public boolean validateCart(Cart cart) {
        validateCartLineOfBusiness(cart.getLineOfBusiness());
        return validateCartDetails(cart);
    }

}
