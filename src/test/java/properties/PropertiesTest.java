package properties;

import properties.impl.PropertiesLoader;
import properties.model.ConfigProperties;

public class PropertiesTest {
    public static void main(String[] args) {
        loadProperties("src/main/resources/app.properties");
        loadProperties("invalid1.properties");
        loadProperties("invalid2.properties");
    }

    private static void loadProperties(String path){
        Loader loader = new PropertiesLoader(path);
        try {
            ConfigProperties configProperties = loader.load();
            System.out.println(configProperties.toString());
        } catch (Exception e) {
            System.out.println("My message:");
            System.out.println(e.getLocalizedMessage());
        }
    }
}
