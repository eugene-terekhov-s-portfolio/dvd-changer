package ru.nergal.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TakenItemRepository extends CrudRepository<TakenItem, Long>{
    List<TakenItem> findByTenant(User user);

    @Query("select ti from TakenItem ti right join ti.disk as disk where ti.id is not null and disk.owner = :user")
    Set<TakenItem> findAllTaken(@Param("user") User user);
}
