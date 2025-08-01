package gift.exception;

public class MessageNotSentException extends RuntimeException {

    public MessageNotSentException(String meessage) {
        super(meessage);
    }

}
