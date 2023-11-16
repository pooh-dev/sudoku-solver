package ua.kh.poohdev.sudokusolver.exceptions;

public class InconsistentCellStateException extends IllegalStateException {
    public InconsistentCellStateException(String errorMsg) {
        super(errorMsg);
    }
}
