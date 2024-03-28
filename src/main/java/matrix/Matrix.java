package matrix;

import java.math.BigInteger;

public interface Matrix {
    BigInteger getElement(int row, int col);
    void putElement(int row, int col, BigInteger value);

    int getRowNumber();
    int getColNumber();

    String getName();


    void printMatrix(boolean format);

}
