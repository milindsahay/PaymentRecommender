package com.paymentrecommendation.service.filter;


import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.enums.PaymentInstrumentType;
import com.paymentrecommendation.models.*;
import com.paymentrecommendation.service.ConfigService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PiFilter {
    //can be fetched from config
    ConfigService configService;

    public PiFilter() {
        this.configService = new ConfigService();
        configService.buildPaymentInstrumentPriority();
        configService.buildMaxCartAmountMap();
    }


    //filtering of customer capability
    public List<PaymentInstrument> filterOnCustomerCapability(User user) {
        boolean isUpiEnabled = Optional.ofNullable(user.getUserContext())
                .map(UserContext::getDeviceContext)
                .map(DeviceContext::isUpiEnabled)
                .orElse(false);

        if (!isUpiEnabled) {
            return user.getUserPaymentInstrument().getPaymentInstruments().stream()
                    .filter(paymentInstrument -> !PaymentInstrumentType.UPI.equals(paymentInstrument.getPaymentInstrumentType()))
                    .collect(Collectors.toList());
        }

        return user.getUserPaymentInstrument().getPaymentInstruments();
    }

    //filtering of cart amount
    public List<PaymentInstrument> filterOnCartAmount(Cart cart, List<PaymentInstrument> paymentInstruments) {
        Double cartAmount = cart.getCartDetail().getCartAmount();
        LineOfBusiness lineOfBusiness = cart.getLineOfBusiness();
        Map<PaymentInstrumentType, Integer> maxAmountPermitted = configService.getMaxCartAmount().get(lineOfBusiness);
        return paymentInstruments.stream()
                .filter(paymentInstrument -> maxAmountPermitted.containsKey(paymentInstrument.getPaymentInstrumentType()))
                .filter(paymentInstrument -> maxAmountPermitted.get(paymentInstrument.getPaymentInstrumentType()) > cartAmount)
                .collect(Collectors.toList());
    }


    //filtering of instruments allowed for that line of business
    public List<PaymentInstrument> filterOnLineOfBusiness(Cart cart, List<PaymentInstrument> paymentInstruments) {
        LineOfBusiness lineOfBusiness = cart.getLineOfBusiness();
        Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>>  paymentInstrumentPriority = configService.getPaymentInstrumentPriority();
        return paymentInstruments.stream()
                .filter(paymentInstrument -> paymentInstrumentPriority.containsKey(lineOfBusiness))
                .collect(Collectors.toList());
    }

    //sortation on preffered payment instrument for a line of business and then on relevance score
    public List<PaymentInstrument> sortPaymentInstrument(LineOfBusiness lineOfBusiness, List<PaymentInstrument> paymentInstruments) {
        Map<LineOfBusiness, Map<PaymentInstrumentType, Integer>>  paymentInstrumentPriority = configService.getPaymentInstrumentPriority();
        Map<PaymentInstrumentType, Integer> instrumentTypePriority = paymentInstrumentPriority.get(lineOfBusiness);
        paymentInstruments.sort(
                Comparator.comparing((PaymentInstrument paymentInstrument) -> instrumentTypePriority.get(paymentInstrument.getPaymentInstrumentType()))
                        .thenComparing((PaymentInstrument paymentInstrument) -> -1 * paymentInstrument.getRelevanceScore())
        );

        return paymentInstruments;
    }
}
