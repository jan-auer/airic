package org.spaceapps.aircheck.server.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.CssLinkResourceTransformer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.Arrays;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        // NOTE: as soon as Spring Boot 1.3 comes out, all this will be configurable
        // via application.properties

        final String[] RESOURCE_FOLDERS = new String[]{"/css/", "/fonts/", "/images/", "/js/"};
        final String CLASSPATH_PREFIX = "classpath:/static";
        final String FOLDER_SUFFIX = "**";

        boolean devMode = this.env.acceptsProfiles("dev");
        boolean useResourceCache = !devMode;
        /* Time, in seconds, to have the browser cache static resources (one year; is maximum that is allowed). */
        Integer cachePeriod = devMode ? 365 * 24 * 60 * 60 : null;

        final String[] pathPatterns = Arrays.stream(RESOURCE_FOLDERS).map(f -> f + FOLDER_SUFFIX)
                .toArray(String[]::new);

        final String[] resourceLocations = Arrays.stream(RESOURCE_FOLDERS).map(f -> CLASSPATH_PREFIX + f)
                .toArray(String[]::new);

        registry.addResourceHandler(pathPatterns)
                .addResourceLocations(resourceLocations)
                .setCachePeriod(cachePeriod)
                .resourceChain(useResourceCache)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"))
                .addTransformer(new CssLinkResourceTransformer());
    }

}
