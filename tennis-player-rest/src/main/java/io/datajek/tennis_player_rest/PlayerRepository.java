package io.datajek.tennis_player_rest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends JpaRepository<Player,Integer> {

    @Modifying
    @Query("update Player p set p.titles = :titles where p.id = :id")
    public void updateTitles(@Param("id")int id, @Param("titles")int titles);
}
