package com.paymentrecommendation.service.filter;

import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.PaymentInstrument;

import java.util.List;

public interface JobFilter {
    List<PaymentInstrument> filter(List<PaymentInstrument> paymentInstruments, Cart cart);
    List<PaymentInstrument> sortOnPriority(List<PaymentInstrument> paymentInstruments);

}
