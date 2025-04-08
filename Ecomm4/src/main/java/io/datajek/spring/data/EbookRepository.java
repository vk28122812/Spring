package io.datajek.spring.data;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface EbookRepository extends JpaRepository <Ebook, Integer>, CustomEbookRepository {

}