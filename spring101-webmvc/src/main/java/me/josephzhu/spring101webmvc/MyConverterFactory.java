package me.josephzhu.spring101webmvc;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.Arrays;

public class MyConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        return new String2EnumConverter(targetType);
    }

    class String2EnumConverter<T extends Enum<T>> implements Converter<String, T> {

        private Class<T> enumType;

        private String2EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return Arrays.stream(enumType.getEnumConstants())
                    .filter(e -> e.name().equalsIgnoreCase(source))
                    .findAny().orElse(null);
        }
    }
}
