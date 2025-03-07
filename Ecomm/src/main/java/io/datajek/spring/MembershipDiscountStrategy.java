package io.datajek.spring;

import org.springframework.stereotype.Component;

@Component
public class MembershipDiscountStrategy implements DiscountStrategy {
    @Override
    public double applyDiscount(double originalPrice) {
        // Apply discount based on user's membership level
        return originalPrice * 0.85; // 15% discount
    }
}
