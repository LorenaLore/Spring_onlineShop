package ro.msg.learning.shop.exception;

public class OrderHandlingException extends Exception {

    public OrderHandlingException(String message) {
        super("An error occurred while creating order :" + message);
    }

}
