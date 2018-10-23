package io.dddbyexamples.delivery.planning.plan;

import io.dddbyexamples.delivery.planning.Amounts;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class ProductAmount {
    String product;
    Long amount;

    public static Amounts createAmounts(List<ProductAmount> amounts) {
        return new Amounts(amounts.stream().collect(
                Collectors.groupingBy(
                        ProductAmount::getProduct,
                        Collectors.summingLong(ProductAmount::getAmount)
                )
        ));
    }
}
