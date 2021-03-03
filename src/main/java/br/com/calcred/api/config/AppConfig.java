package br.com.calcred.api.config;

import br.com.calcred.api.integration.account.AccountIntegrationFactory;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.format.Formatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import static java.time.format.DateTimeFormatter.*;

@EnableFeignClients(basePackages = {"br.com.calcred.api.integration"})
@Configuration
public class AppConfig {

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasenames(new String[]{"classpath:i18n/messages"});
        source.setCacheSeconds(3600);
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    @Bean
    LocalValidatorFactoryBean getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Bean
    FactoryBean serviceLocatorFactory() {
        ServiceLocatorFactoryBean serviceFactoryBean = new ServiceLocatorFactoryBean();
        serviceFactoryBean.setServiceLocatorInterface(AccountIntegrationFactory.class);
        return serviceFactoryBean;
    }

    @Bean
    Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) {
                return LocalDate.parse(text, ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return ISO_DATE.format(object);
            }
        };
    }

    @Bean
    Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) {
                return LocalDateTime.parse(text, ISO_DATE_TIME);
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return ISO_DATE_TIME.format(object);
            }
        };
    }
}
