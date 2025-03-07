package io.datajek.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DiscountService {

    private final DiscountStrategy discountDEF, discountBDS, discountMDS;

    private final List<DiscountStrategy> discountStrategies;

    @Autowired
    public DiscountService(@Qualifier("bundleDiscountStrategy") DiscountStrategy discountBDS,
                           @Qualifier("defaultDiscountStrategy") DiscountStrategy discountDEF,
                           @Qualifier("membershipDiscountStrategy") DiscountStrategy discountMDS) {
        this.discountDEF = discountDEF;
        this.discountBDS = discountBDS;
        this.discountMDS = discountMDS;
        this.discountStrategies = Arrays.asList(discountDEF, discountBDS, discountMDS);
    }

    public double applyDefaultDiscount(double originalPrice) {
        return discountDEF.applyDiscount(originalPrice);
    }

    public double applyBundleDiscount(double originalPrice) {
        return discountBDS.applyDiscount(originalPrice);
    }

    public double applyMembershipDiscount(double originalPrice) {
        return discountMDS.applyDiscount(originalPrice);
    }

    public double applyAllDiscounts(double originalPrice) {
        double discountedPrice = originalPrice;
        for (DiscountStrategy strategy : discountStrategies) {
            discountedPrice = strategy.applyDiscount(discountedPrice);
        }
        return discountedPrice;
    }
}