package solid.ocp;

import java.util.Arrays;

public record AndSpecification<T>(Specification<T>... specs) implements Specification<T> {

    @Override
    public boolean isSatisfied(T item) {
        return Arrays.stream(specs).allMatch(s -> s.isSatisfied(item));
    }
}
