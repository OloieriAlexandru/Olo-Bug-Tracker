package com.olo.olobugtracker.models;

public enum BugPriorityEnum {
    LOW(0L),
    MEDIUM(1L),
    HIGH(2L),
    CRITICAL(3L);

    private final Long value;

    BugPriorityEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
