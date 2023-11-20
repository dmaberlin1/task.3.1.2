package ru.itmentor.spring.boot_security.demo.model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non-Binary"),
    GENDERQUEER("Genderqueer"),
    GENDERFLUID("Genderfluid"),
    AGENDER("Agender"),
    BIGENDER("Bigender"),
    TWO_SPIRIT("Two-Spirit"),
    PREFER_NOT_TO_SAY("Prefer not to say");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}