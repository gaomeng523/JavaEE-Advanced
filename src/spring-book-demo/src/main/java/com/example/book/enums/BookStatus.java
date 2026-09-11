package com.example.book.enums;


public enum BookStatus {
    DELETED(0,"无效"),
    NORMAL(1,"可借阅"),
    FORBIDDEN(2,"不可借阅"),
    ;
    private int code;
    private String name;

    BookStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    // 入参改成 Integer 并先判空，避免 status 为 null 时自动拆箱抛 NPE
    public static String getStatusByCode(Integer code){
        if(code == null){
            return null;
        }
        switch (code){
            case 0: return BookStatus.DELETED.name;
            case 1: return BookStatus.NORMAL.name;
            case 2: return BookStatus.FORBIDDEN.name;
            default: return null;
        }
    }
}
