package io.datajek.database_relationships.manytomany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Integer> {
}
