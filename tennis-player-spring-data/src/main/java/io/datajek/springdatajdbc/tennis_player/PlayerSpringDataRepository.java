package io.datajek.springdatajdbc.tennis_player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerSpringDataRepository extends JpaRepository<Player, Integer> {

    List<Player> findByNationality(String nationality);
}
