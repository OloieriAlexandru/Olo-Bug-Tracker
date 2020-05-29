package com.olo.olobugtracker.models;

public enum UserRoleEnum {
    OWNER(0L),
    DEVELOPER(1L),
    TESTER(2L),
    DEV_OPS(3L);

    private final Long value;

    UserRoleEnum(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }
}
