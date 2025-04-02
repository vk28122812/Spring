package io.datajek.database_relationships.onetoone;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends JpaRepository<Player, Integer> {
}
