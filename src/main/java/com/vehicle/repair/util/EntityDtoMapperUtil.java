package com.vehicle.repair.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class EntityDtoMapperUtil {

	private final ModelMapper modelMapper;

	public <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> destinationCLass) {
		return entityList.stream()
				.map(entity -> map(entity, destinationCLass))
				.collect(Collectors.toList());
	}

	public <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
}
