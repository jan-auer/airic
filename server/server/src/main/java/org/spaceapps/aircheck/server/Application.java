package org.spaceapps.aircheck.server;

import com.google.android.gcm.server.Sender;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Sender getGcmSender(@Value("${org.spaceapps.aircheck.gcm_key}") String apiKey) {
        return new Sender(apiKey);
    }

    @Bean
    public ReloadableResourceBundleMessageSource getMessageSource() {
        final ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setFallbackToSystemLocale(false);
        source.setBasenames("classpath:common");

        if (env.acceptsProfiles("dev")) {
            source.setCacheSeconds(0);
        } else {
            source.setCacheSeconds(-1);
        }

        return source;
    }

}
