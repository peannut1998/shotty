package com.shotty.shotty.domain.user.enums;

public enum UserRoleEnum {
    COMMON((short)0),
    INFLUENCER((short)1);

    private final short roleNum;

    UserRoleEnum(short roleNum) {
        this.roleNum = roleNum;
    }

    public short getRoleNum() {
        return roleNum;
    }

    public static UserRoleEnum getUserRoleByRoleNum(short roleNum) {
        for (UserRoleEnum userRoleEnum : values()) {
            if (userRoleEnum.getRoleNum() == roleNum) {
                return userRoleEnum;
            }
        }
        throw new IllegalArgumentException("Invalid roleNum: " + roleNum);
    }
}
