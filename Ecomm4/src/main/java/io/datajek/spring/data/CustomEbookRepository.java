package io.datajek.spring.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomEbookRepository {
    
    @Modifying
    @Query("UPDATE Ebook e SET e.title = :title WHERE e.id = :id")
    void updateTitle(@Param("id") int id, @Param("title") String title);

}