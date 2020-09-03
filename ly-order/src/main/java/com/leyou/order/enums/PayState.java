package com.leyou.order.enums;

/**
 * @author Mr.JK
 * @create 2020-06-05  9:33
 */

public enum PayState {
    NOT_PAY(0), SUCCESS(1), FAIL(2);

    PayState(int value) {
        this.value = value;
    }

    int value;

    public int getValue() {
        return value;
    }
}