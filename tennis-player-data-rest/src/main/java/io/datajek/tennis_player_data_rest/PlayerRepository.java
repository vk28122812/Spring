package io.datajek.tennis_player_data_rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//to access: /api/athletes
@RepositoryRestResource(path="athletes")
public interface PlayerRepository extends JpaRepository<Player,Integer> {
}
