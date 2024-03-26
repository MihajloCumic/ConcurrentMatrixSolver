package stringtest;

import java.math.BigInteger;
import java.util.Arrays;

public class StringTest {
    public static void main(String[] args) {
        String s = "12,0 = 9183554 ";
        String[] parts = s.split(",");
        int row = Integer.parseInt(parts[0].trim());
        String[] colValue = parts[1].split("=");
        int col = Integer.parseInt(colValue[0].trim());
        BigInteger value = new BigInteger(colValue[1].trim());
        System.out.println(row);
        System.out.println(col);
        System.out.println(value);

    }
}
