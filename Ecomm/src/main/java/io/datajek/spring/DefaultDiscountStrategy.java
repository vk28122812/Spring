package io.datajek.spring;

import org.springframework.stereotype.Component;

@Component
public class DefaultDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // Apply discount for online purchase
        return originalPrice * 0.95; // 5% discount
    }
}
