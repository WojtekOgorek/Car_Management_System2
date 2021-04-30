package ogorek.wojciech.persistence.domain.configs.validator;

import java.util.Map;
import java.util.stream.Collectors;

public interface Validator<T> {
    Map<String, String> validate(T t);

    static <T> void validate(Validator<T> validator, T item) {
        var errors = validator.validate(item);
        if (!errors.isEmpty()) {
            var message = errors
                    .entrySet()
                    .stream()
                    .map(e -> e.getKey() + ": " + e.getValue())
                    .collect(Collectors.joining(", "));
            throw new ValidatorException("Validator errors: " + message);
        }
    }

}
