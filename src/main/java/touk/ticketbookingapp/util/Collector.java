package touk.ticketbookingapp.util;

import java.util.stream.Collectors;

public class Collector {
    public static <T> java.util.stream.Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }
}
