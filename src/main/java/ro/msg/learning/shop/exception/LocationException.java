package ro.msg.learning.shop.exception;

public class LocationException extends Exception {

    public LocationException(String message) {
        super("An error occurred while attempting to find a location :" + message);
    }
}
