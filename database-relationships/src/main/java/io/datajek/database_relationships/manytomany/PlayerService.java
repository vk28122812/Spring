package io.datajek.database_relationships.manytomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> allPlayers(){
        return playerRepository.findAll();
    }

    public Player getPlayer(int id){
        return playerRepository.findById(id).get();
    }

    public Player addPlayer(Player player){
        player.setId(0);
        return playerRepository.save(player);
    }

    public void deletePlayer(int id){
        playerRepository.deleteById(id);
    }

    public Player assignProfile(int id, PlayerProfile profile){
        Player player = playerRepository.findById(id).get();
        player.setPlayerProfile(profile);
        return playerRepository.save(player);
    }

    public Player assignRegistration(int id, Registration reg){
        Player player = playerRepository.findById(id).get();
        player.registerPlayer(reg);
        return playerRepository.save(player);
    }
}
