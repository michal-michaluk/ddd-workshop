package io.dddbyexamples.delivery.planning.delivery.capacity;

import io.dddbyexamples.delivery.planning.Amounts;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StorageUnitsAmount extends Amounts {

    public StorageUnitsAmount(Map<String, Long> storageUnitsAmount) {
        super(storageUnitsAmount);
    }

    public static StorageUnitsAmount empty() {
        return new StorageUnitsAmount(Collections.emptyMap());
    }

    public static Builder builder() {
        return new Builder();
    }

    public long getPalette() {
        return get("palette");
    }

    public long getTrailers() {
        return get("trailers");
    }

    public long getCages() {
        return get("cages");
    }

    public static class Builder {
        private Map<String, Long> amounts = new HashMap<>();

        public StorageUnitsAmount build() {
            return new StorageUnitsAmount(amounts);
        }

        public Builder addPalettes(long amount) {
            add("palette", amount);
            return this;
        }

        public Builder addTrailers(long amount) {
            add("trailers", amount);
            return this;
        }

        public Builder addCages(long amount) {
            add("cages", amount);
            return this;
        }

        public Builder addPalettes(long amount, long limit) {
            add("palette", Math.min(amount, limit));
            return this;
        }

        public Builder addTrailers(long amount, long limit) {
            add("trailers", Math.min(amount, limit));
            return this;
        }

        public Builder addCages(long amount, long limit) {
            add("cages", Math.min(amount, limit));
            return this;
        }

        private void add(String unit, long amount) {
            amounts.put(unit,
                    amounts.getOrDefault(unit, 0L) + Math.max(amount, 0));
        }
    }
}
