package com.example.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MapperUtils {

    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    private MapperUtils() {
    }

    public static <D, T> D map(final T entity, final Class<D> outClass) {
        if (entity == null) {
            return null;
        }

        return modelMapper.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, final Class<D> outCLass) {
        if (entityList == null) {
            return null;
        }

        if (entityList.isEmpty()) {
            return Collections.emptyList();
        }

        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public static <S, D> D map(final S source, final D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
