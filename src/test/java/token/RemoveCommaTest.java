package token;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveCommaTest {
    public static void main(String[] args) {
        String s1 = "multiply A,B ";
        String s2 = "multiply A, B ";
        String s3 = "multiply A , B ";
        String s4 = "multiply A,B --name ";
        String s5 = "multiply A, B  --name ";
        String s6 = "multiply A , B  --name  ";
        String s7 = "multiply A B --name ";
        String s8 = " multiply A  B  --name ";
        String s9 = "   multiply  A   B  --name  ";

        List<String> strings = new ArrayList<>();
        strings.add(s1);
        strings.add(s2);
        strings.add(s3);
        strings.add(s4);
        strings.add(s5);
        strings.add(s6);
        strings.add(s7);
        strings.add(s8);
        strings.add(s9);
        List<String> results = new ArrayList<>();
        List<String[]> partsOfStrings = new ArrayList<>();
        for(String s: strings){
            String tmp = s.replace(",", " ").replaceAll("[^\\S\\r\\n]{2,}", " ").trim();
            results.add(tmp);
            partsOfStrings.add(tmp.split(" "));
        }

        System.out.println("Results strings:");
        for(String s: results){
            System.out.println(s);
        }
        System.out.println("Parts strings:");
        for(String[] s: partsOfStrings){
            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%");
            Arrays.stream(s).forEach(System.out::println);
        }

    }
}
