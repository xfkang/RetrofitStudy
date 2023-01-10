package com.itbird.bean;

import java.io.IOException;

/**
 * 宠物销售状态
 */
public enum Status {
    AVAILABLE, PENDING, SOLD;

    public String toValue() {
        switch (this) {
            case AVAILABLE: return "available";
            case PENDING: return "pending";
            case SOLD: return "sold";
        }
        return null;
    }

    public static Status forValue(String value) throws IOException {
        if (value.equals("available")) return AVAILABLE;
        if (value.equals("pending")) return PENDING;
        if (value.equals("sold")) return SOLD;
        throw new IOException("Cannot deserialize Status");
    }
}
