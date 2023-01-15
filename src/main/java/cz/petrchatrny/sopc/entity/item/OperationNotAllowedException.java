package cz.petrchatrny.sopc.entity.item;

public class OperationNotAllowedException extends Exception {
    public OperationNotAllowedException(String errorMessage) {
        super(errorMessage);
    }
}
