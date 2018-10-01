package org.microprofileext.configsource.file.properties.external;

import java.util.Collections;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.ConfigSourceProvider;

/**
 * Configures an {@link ExternalPropertiesFileConfigSource} with a file system
 * path string coming from the {@code external.config} system property.
 *
 * @author Derek P. Moore
 */
public class ExternalPropertiesFileConfigSourceProvider implements ConfigSourceProvider {

    /**
     * Provides a single {@link ExternalPropertiesFileConfigSource} instance
     * if system property {@code external.config} is set to the path of a
     * readable file on the default file system.
     * @param classLoader unused
     * @return if {@code external.config} is set, a {@link ConfigSource} backed
     *         by an external {@code .properties} file
     */
    @Override
    public Iterable<ConfigSource> getConfigSources(ClassLoader classLoader) {
        String externalConfig = System.getProperty("external.config");

        if (externalConfig != null) {
            return Collections.singletonList(new ExternalPropertiesFileConfigSource(externalConfig));
        }

        return Collections.emptyList();
    }

}
