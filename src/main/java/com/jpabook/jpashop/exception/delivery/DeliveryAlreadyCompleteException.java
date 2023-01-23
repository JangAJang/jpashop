package com.jpabook.jpashop.exception.delivery;

public class DeliveryAlreadyCompleteException extends IllegalArgumentException{
    public DeliveryAlreadyCompleteException() {
        super();
    }

    public DeliveryAlreadyCompleteException(String s) {
        super(s);
    }

    public DeliveryAlreadyCompleteException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeliveryAlreadyCompleteException(Throwable cause) {
        super(cause);
    }
}
