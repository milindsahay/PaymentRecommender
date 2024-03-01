package com.paymentrecommendation.service;

import com.paymentrecommendation.factory.FilterFactory;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.PaymentInstrument;
import com.paymentrecommendation.models.User;
import com.paymentrecommendation.service.filter.JobFilter;
import com.paymentrecommendation.service.filter.PiFilter;
import com.paymentrecommendation.service.validator.CartValidatorService;

import java.util.ArrayList;
import java.util.List;

public class MyPaymentRecommendor implements PaymentRecommender {
    private PiFilter piFilter;
    private CartValidatorService cartValidatorService;

    private FilterFactory filterFactory;
    MyPaymentRecommendor(){
        this.piFilter = new PiFilter();
        this.cartValidatorService = new CartValidatorService();
        filterFactory = FilterFactory.getInstance();
    }
    public List<PaymentInstrument> recommendPaymentInstruments(final User user, final Cart cart){
        if(cartValidatorService.validateCart(cart)) {
            List<PaymentInstrument> paymentInstruments = piFilter
                    .filterOnCustomerCapability(user);
            JobFilter jobFilter = filterFactory.getFilterInstance(cart.getLineOfBusiness());

            paymentInstruments = jobFilter.filter(paymentInstruments, cart);
            return jobFilter.sortOnPriority(paymentInstruments);
        }

        return new ArrayList<>();
    };
}