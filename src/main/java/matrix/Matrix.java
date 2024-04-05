package matrix;

import java.math.BigInteger;
import java.nio.file.Path;

public interface Matrix {
    BigInteger getElement(int row, int col);
    void putElement(int row, int col, BigInteger value);

    void addMatrix(Matrix matrix);

    int getRowNumber();
    int getColNumber();

    String getName();

    String getFilePath();
    void setFile(Path file);

    void printMatrix(boolean format);
    String getMatrixContentAsString();

}
