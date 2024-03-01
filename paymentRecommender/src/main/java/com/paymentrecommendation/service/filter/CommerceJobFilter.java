package com.paymentrecommendation.service.filter;

import com.google.common.collect.ImmutableMap;
import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.Cart;
import com.paymentrecommendation.models.PaymentInstrument;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommerceJobFilter implements JobFilter {
    private final ImmutableMap<PaymentInstrumentType, Integer> maximumPermittedAmount;

    private ImmutableMap<PaymentInstrumentType, Integer> paymentInstrumentPriority;

    public CommerceJobFilter(){
        maximumPermittedAmount = ImmutableMap.of(
                PaymentInstrumentType.CREDIT_CARD, 250000,
                PaymentInstrumentType.UPI, 100000,
                PaymentInstrumentType.DEBIT_CARD, 200000
        );

        paymentInstrumentPriority = ImmutableMap.of(
                PaymentInstrumentType.CREDIT_CARD, 0,
                PaymentInstrumentType.UPI, 1,
                PaymentInstrumentType.DEBIT_CARD, 2
        );
    }
    @Override
    public List<PaymentInstrument> filter(List<PaymentInstrument> paymentInstruments, Cart cart) {
        Double cartAmount = cart.getCartDetail().getCartAmount();
        return paymentInstruments.stream()
                .filter(paymentInstrument -> maximumPermittedAmount.containsKey(paymentInstrument.getPaymentInstrumentType()))
                .filter(paymentInstrument -> maximumPermittedAmount.get(paymentInstrument.getPaymentInstrumentType()) > cartAmount)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentInstrument> sortOnPriority(List<PaymentInstrument> paymentInstruments) {
        paymentInstruments.sort(
                Comparator.comparing((PaymentInstrument paymentInstrument) -> paymentInstrumentPriority.get(paymentInstrument.getPaymentInstrumentType()))
                        .thenComparing((PaymentInstrument paymentInstrument) -> -1 * paymentInstrument.getRelevanceScore())
        );

        return paymentInstruments;
    }

}
