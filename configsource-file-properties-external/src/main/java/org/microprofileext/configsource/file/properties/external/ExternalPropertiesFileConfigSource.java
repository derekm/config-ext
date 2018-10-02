package org.microprofileext.configsource.file.properties.external;

import com.github.t1.log.LogLevel;
import com.github.t1.log.Logged;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import org.microprofileext.configsource.url.properties.PropertyFileConfigSource;

/**
 * Use an external {@code .properties} file on the default file system to
 * back a {@link PropertyFileConfigSource} instance.
 * <p>
 * Runs at priority ordinal {@code 199}, just between all
 * {@code microprofile-config.properties} resource files at priority {@code 100}
 * and environment variables at priority {@code 300}, allowing its property names
 * to be overridden by environment variables ({@link System#getenv()}, priority
 * {@code 300}) and system properties ({@link System#getProperties()}, priority
 * {@code 400}).
 *
 * @author Derek P. Moore
 */
public class ExternalPropertiesFileConfigSource extends PropertyFileConfigSource {

    /**
     * Initialize the {@link PropertyFileConfigSource} super class with a
     * {@code URL} and set its priority ordinal to {@code 199}.
     *
     * @param propertyFileUrl URL to a {@code .properties} file
     */
    public ExternalPropertiesFileConfigSource(URL propertyFileUrl) {
        super(propertyFileUrl);
        initOrdinal(199);
    }

    /**
     * Get a {@code URL} from a path to a file on the default file system and
     * call {@link ExternalPropertiesFileConfigSource(URL propertyFileUrl)}.
     *
     * @param externalFile path to a {@code .properties} file on the default
     *                     file system
     */
    public ExternalPropertiesFileConfigSource(String externalFile) {
        this(loadExternalConfig(externalFile));
    }

    /**
     * Make a {@code URL} from a path to a file on the default file system and
     * ensure the file can be opened for reading.
     *
     * @param externalConfig path to a file on the default file system
     * @return a readable URL to a file on the default file system
     */
    @Logged(level = LogLevel.INFO)
    private static URL loadExternalConfig(String externalConfig) {
        try {
            URL url = Paths.get(externalConfig).toUri().toURL();

            url.openStream().close(); // Test URL to see if it exists

            return url;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load " + externalConfig, ex);
        }
    }

}
