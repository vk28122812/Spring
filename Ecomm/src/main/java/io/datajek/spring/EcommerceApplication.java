package io.datajek.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class EcommerceApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(EcommerceApplication.class);
		DiscountService discountService = ctx.getBean(DiscountService.class);
        System.out.println("Applying discounts:");
        
        // Initialing the original price
        double originalPrice = 100.0;
        System.out.println("Original Price: $" + originalPrice);

        // Apply default discount
        double defaultDiscountedPrice = discountService.applyDefaultDiscount(originalPrice);
        System.out.println("Price after Default Discount: $" + defaultDiscountedPrice);
        
        // Apply membership discount
        double membershipDiscountedPrice = discountService.applyMembershipDiscount(originalPrice);
        System.out.println("Price after Membership Discount: $" + membershipDiscountedPrice);

        // Apply bundle discount
        double bundleDiscountedPrice = discountService.applyBundleDiscount(originalPrice);
        System.out.println("Price after Bundle Discount: $" + bundleDiscountedPrice);

        // Apply all discounts
        double fullyDiscountedPrice = discountService.applyAllDiscounts(originalPrice);
        System.out.println("Price after Full Discount: $" + fullyDiscountedPrice);

        ctx.close();
    }

}