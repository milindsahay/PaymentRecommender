package com.paymentrecommendation.service;

import com.google.common.collect.ImmutableMap;
import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;

import java.util.HashMap;
import java.util.Map;


//can read from a file or stream and build config
public class ConfigService {
    private Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>> paymentInstrumentPriority = new HashMap<>();
    private Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>> maxCartAmount = new HashMap<>();

    public void buildMaxCartAmountMap(){
        maxCartAmount.put(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, ImmutableMap.of(
                PaymentInstrumentType.UPI, 200000,
                PaymentInstrumentType.NETBANKING, 200000,
                PaymentInstrumentType.DEBIT_CARD, 200000
        ));


        maxCartAmount.put(LineOfBusiness.COMMERCE, ImmutableMap.of(
                PaymentInstrumentType.CREDIT_CARD, 250000,
                PaymentInstrumentType.UPI, 100000,
                PaymentInstrumentType.DEBIT_CARD, 200000
        ));

        maxCartAmount.put(LineOfBusiness.INVESTMENT, ImmutableMap.of(
                PaymentInstrumentType.UPI, 100000,
                PaymentInstrumentType.NETBANKING, 150000,
                PaymentInstrumentType.DEBIT_CARD, 150000
        ));

    }

    public void buildPaymentInstrumentPriority() {
        //  lower number = higher priority
        paymentInstrumentPriority.put(LineOfBusiness.CREDIT_CARD_BILL_PAYMENT, ImmutableMap.of(
                PaymentInstrumentType.UPI, 0,
                PaymentInstrumentType.NETBANKING, 1,
                PaymentInstrumentType.DEBIT_CARD, 2
        ));

        paymentInstrumentPriority.put(LineOfBusiness.COMMERCE, ImmutableMap.of(
                PaymentInstrumentType.CREDIT_CARD, 0,
                PaymentInstrumentType.UPI, 1,
                PaymentInstrumentType.DEBIT_CARD, 2
        ));

        paymentInstrumentPriority.put(LineOfBusiness.INVESTMENT, ImmutableMap.of(
                PaymentInstrumentType.UPI, 0,
                PaymentInstrumentType.NETBANKING, 1,
                PaymentInstrumentType.DEBIT_CARD, 2
        ));
    }



    public Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>> getMaxCartAmount() {
        return maxCartAmount;
    }

    public Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>> getPaymentInstrumentPriority() {
        return paymentInstrumentPriority;
    }






}
