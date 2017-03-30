package ru.nergal.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TakenItemRepository extends CrudRepository<TakenItem, Long>{
    List<TakenItem> findByTenant(User user);
}
