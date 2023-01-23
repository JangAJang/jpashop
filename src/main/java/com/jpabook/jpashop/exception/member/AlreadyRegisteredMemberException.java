package com.jpabook.jpashop.exception.member;

public class AlreadyRegisteredMemberException extends IllegalArgumentException{

    public AlreadyRegisteredMemberException() {
        super();
    }

    public AlreadyRegisteredMemberException(String s) {
        super(s);
    }

    public AlreadyRegisteredMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyRegisteredMemberException(Throwable cause) {
        super(cause);
    }
}
