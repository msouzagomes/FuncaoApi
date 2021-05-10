package br.com.calcred.api.config;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.Formatter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@EnableFeignClients(basePackages = {"br.com.calcred.api.integration"})
@Configuration
public class AppConfig {

    @Bean
    MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames(new String[]{"classpath:i18n/messages"});
        source.setCacheSeconds(3600);
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Bean
    LocalValidatorFactoryBean getValidator(final MessageSource messageSource) {
        final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(final String text, final Locale locale) {
                return LocalDate.parse(text, ISO_DATE);
            }

            @Override
            public String print(final LocalDate object, final Locale locale) {
                return ISO_DATE.format(object);
            }
        };
    }

    @Bean
    Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDateTime parse(final String text, final Locale locale) {
                return LocalDateTime.parse(text, ISO_DATE_TIME);
            }

            @Override
            public String print(final LocalDateTime object, final Locale locale) {
                return ISO_DATE_TIME.format(object);
            }
        };
    }
}
