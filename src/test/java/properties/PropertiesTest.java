package properties;

import properties.impl.PropertiesLoader;
import properties.model.ConfigProperties;

import java.io.IOException;

public class PropertiesTest {
    public static void main(String[] args) {
        Loader loader = new PropertiesLoader("src/main/resources/app.properties");
        try {
            ConfigProperties configProperties = loader.load();
            System.out.println(configProperties.toString());
        } catch (IOException e) {
            System.out.println("My message:");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
