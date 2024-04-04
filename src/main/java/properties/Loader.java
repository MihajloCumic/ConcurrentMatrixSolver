package properties;

import properties.model.ConfigProperties;

import java.io.IOException;

public interface Loader {
    ConfigProperties load() throws Exception;
}
