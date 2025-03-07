package io.datajek.spring;

public interface DiscountStrategy {
    double applyDiscount(double originalPrice);
}