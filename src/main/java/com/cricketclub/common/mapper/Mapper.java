package com.cricketclub.common.mapper;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface Mapper<T, V extends ResourceSupport, K extends ResourceSupport> {

    T transform(final V v);

    V transform(final T t);

    List<V> transform(List<T> tList);

    K transformToList(final List<V> vList);
}
