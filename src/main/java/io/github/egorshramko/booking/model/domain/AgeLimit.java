package io.github.egorshramko.booking.model.domain;

import lombok.Getter;

@Getter
public enum AgeLimit {
    NO_LIMIT("0+"),
    OLDER_6("6+"),
    OLDER_12("12+"),
    OLDER_16("16+"),
    ADULT_ONLY("18+");

    private final String description;

    AgeLimit(String description) {
        this.description = description;
    }
}
