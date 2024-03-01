package com.paymentrecommendation.factory;

import com.paymentrecommendation.enums.LineOfBusiness;
import com.paymentrecommendation.service.filter.CommerceJobFilter;
import com.paymentrecommendation.service.filter.CreditCardJobFilter;
import com.paymentrecommendation.service.filter.InvestmentJobFilter;
import com.paymentrecommendation.service.filter.JobFilter;

import java.util.Objects;

public class FilterFactory {

    private static FilterFactory filterFactory = null;
    private FilterFactory(){
    }

    public static FilterFactory getInstance(){
        if (Objects.isNull(filterFactory)){
            filterFactory = new FilterFactory();
        }
        return filterFactory;
    }

    public JobFilter getFilterInstance(LineOfBusiness lineOfBusiness) {
        if (LineOfBusiness.INVESTMENT.equals(lineOfBusiness)) {
            return new InvestmentJobFilter();
        } else if (LineOfBusiness.COMMERCE.equals(lineOfBusiness)) {
            return new CommerceJobFilter();
        } else if (LineOfBusiness.CREDIT_CARD_BILL_PAYMENT.equals(lineOfBusiness)) {
            return new CreditCardJobFilter();
        }
        throw new RuntimeException("Instance not implemented");
    }
}
