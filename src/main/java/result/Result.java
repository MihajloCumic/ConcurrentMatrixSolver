package result;

import matrix.Matrix;

public interface Result {
    String resultAsString();
    boolean resultContainsMatrix(Matrix matrix);
}
