package com.insign.common.linearalgebra.exceptions;

/**
 * Created by ilion on 05.01.2015.
 */
public class IndexException extends RuntimeException {
    public IndexException(int row, int column, int rowsCount, int columnsCount) {
        super("Element (" + row + ";" + column + ") does not exist in " + rowsCount + "x" + columnsCount + " matrix");
    }
}
