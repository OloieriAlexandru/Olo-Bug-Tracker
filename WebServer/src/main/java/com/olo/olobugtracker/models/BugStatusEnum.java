package com.olo.olobugtracker.models;

public enum BugStatusEnum {
    IN_PROGRESS(0L),
    NOT_A_BUG(1L),
    NOT_REPRODUCIBLE(2L),
    MISSING_INFORMATION(3L),
    READY_FOR_NEXT_RELEASE(4L),
    READY_FOR_RETEST(5L),
    CLOSED(6L),
    ON_HOLD(7L),
    DUPLICATE_BUG(8L),
    OPEN(9L);

    private final Long value;

    BugStatusEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
