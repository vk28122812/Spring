package io.datajek.spring;

import org.springframework.stereotype.Component;

@Component
public class BundleDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // Apply discount based on bundled purchases
        return originalPrice * 0.9; // 10% discount
    }
}