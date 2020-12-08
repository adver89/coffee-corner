package com.epam.coffeecorner;

import com.epam.coffeecorner.model.Product;
import com.epam.coffeecorner.model.ProductType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OrderService {

    protected static final Map<Integer, Product> MENU;
    protected static final Map<Integer, Product> EXTRAS;
    private static final List<Product> ORDER = new ArrayList<>();

    static {
        MENU = new HashMap<>();
        MENU.put(1, new Product("Coffe - small", BigDecimal.valueOf(2.5), ProductType.COFFEE));
        MENU.put(2, new Product("Coffe - medium", BigDecimal.valueOf(3), ProductType.COFFEE));
        MENU.put(3, new Product("Coffe - large", BigDecimal.valueOf(3.5), ProductType.COFFEE));
        MENU.put(4, new Product("Bacon roll", BigDecimal.valueOf(4.5), ProductType.SNACK));
        MENU.put(5, new Product("Freshly squeezed orange juice - 0.25l", BigDecimal.valueOf(3.95), ProductType.SOFT_DRINK));

        EXTRAS = new HashMap<>();
        EXTRAS.put(1, new Product("Extra milk", BigDecimal.valueOf(.3), ProductType.EXTRA));
        EXTRAS.put(2, new Product("Foamed milk", BigDecimal.valueOf(.5), ProductType.EXTRA));
        EXTRAS.put(3, new Product("Special roast coffee", BigDecimal.valueOf(.9), ProductType.EXTRA));
    }

    protected void addProductToOrder(Product product) {
        ORDER.add(product);
    }

    protected void printReceipt() {
        if (ORDER.isEmpty()) {
            throw new IllegalArgumentException("Order should contain at least one product to print receipt");
        }
        AtomicInteger beverageCounter = new AtomicInteger();
        System.out.println("Your receipt:");
        BigDecimal total = ORDER.stream().filter(Objects::nonNull).map(product -> {
            if (isBeverage(product)) {
                beverageCounter.addAndGet(1);
            }
            BigDecimal roundedPrice;
            if (beverageCounter.get() % 5 == 0) {
                roundedPrice = BigDecimal.ZERO;
            } else {
                 roundedPrice = product.getPrice().setScale(2, RoundingMode.HALF_EVEN);
            }
            System.out.format("%-20s --- %2.2f CHF \n", product.getName(), roundedPrice);
            return roundedPrice;
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("---------------");
        System.out.format("Total: %2.2f CHF", total.setScale(2, RoundingMode.HALF_EVEN));
        ORDER.clear();
    }

    private boolean isBeverage(Product product) {
        return ProductType.COFFEE.equals(product.getType()) || ProductType.SOFT_DRINK.equals(product.getType());
    }
}
