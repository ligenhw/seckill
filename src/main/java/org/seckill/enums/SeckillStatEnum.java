package org.seckill.enums;

public enum SeckillStatEnum {
    
    SUCCESS(1, "kill success"),
    END(0, "kill finish"),
    REPEAT_KILL(-1, "repeat kill"),
    INNER_ERROR(-2, "system error"),
    DATA_REWRITE(-3, "data rewrite");

    private int state;

    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SeckillStatEnum statOf(int index) {
        for (SeckillStatEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}