package io.datajek.spring;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomEbookRepository {

    @Modifying
    @Query("update Ebook e set e.price = :price where e.id = :id")
    void updatePrice(@Param("id")int id,@Param("price") double price);

    @Modifying
    @Query("update Ebook e set e.discount = :discount where e.id = :id")
    void updateDiscount(@Param("id")int id,@Param("discount") double discount);

}