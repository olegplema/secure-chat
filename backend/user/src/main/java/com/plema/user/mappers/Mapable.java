package com.plema.user.mappers;

import java.util.List;

public interface Mapable<E, D> {

    E toEntity(D d);

    List<E> toEntity(List<D> d);

    D toDto(E e);

    List<D> toDto(List<E> e);
}
