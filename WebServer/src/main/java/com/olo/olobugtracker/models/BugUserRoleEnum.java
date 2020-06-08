package com.olo.olobugtracker.models;

public enum BugUserRoleEnum {
    OWNER(0L),
    DEVELOPER(1L),
    TESTER(2L),
    DEV_OPS(3L),
    ALL(4L);

    private final Long value;

    BugUserRoleEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
