package com.example.identity.infrastructure.config;

import com.example.identity.domain.shared.EmailAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.util.List;

@Configuration
public class JdbcConfiguration {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(List.of(
                new EmailAddressToStringConverter(),
                new StringToEmailAddressConverter()
        ));
    }

    static class EmailAddressToStringConverter implements Converter<EmailAddress, String> {
        @Override
        public String convert(EmailAddress source) {
            return source == null ? null : source.getValue();
        }
    }

    static class StringToEmailAddressConverter implements Converter<String, EmailAddress> {
        @Override
        public EmailAddress convert(String source) {
            return source == null ? null : new EmailAddress(source);
        }
    }
}
