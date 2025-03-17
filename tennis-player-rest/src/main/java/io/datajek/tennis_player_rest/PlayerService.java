package io.datajek.tennis_player_rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    public List<Player> getAllPlayers(){
        return playerRepository.findAll();
    }

    public Player getPlayerById(int id){
        Optional<Player> tempPlayer = playerRepository.findById(id);

        Player p = null;

        if(tempPlayer.isPresent()){
            p = tempPlayer.get();
        }else{
            throw new PlayerNotFoundException("Player with id: "+id+" not found");
        }

        return p;
    }

    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }

    public Player patch(int id, Map<String,Object> playerPatch){
        Optional<Player> player = playerRepository.findById(id);
        if(player.isPresent()) {
            playerPatch.forEach( (key,val) -> {
                Field field = ReflectionUtils.findField(Player.class, key);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, player.get(), val);
            });
        }else throw new PlayerNotFoundException("Player with id: "+id+" not found");
        return playerRepository.save(player.get());
    }

    public Player updatePlayer(int id,Player p){

        Optional<Player> tempPlayer = playerRepository.findById(id);
        Player player = null;
        if(tempPlayer.isPresent()) {
            player = tempPlayer.get();
        }else throw new PlayerNotFoundException("Player with id: "+id+" not found");
        player.setName(p.getName());
        player.setNationality(p.getNationality());
        player.setBirthDate(p.getBirthDate());
        player.setTitles(p.getTitles());

        return playerRepository.save(player);
    }

    public  String deletePlayer(int id){
        Optional<Player> player = playerRepository.findById(id);
        if(player.isEmpty()) {
            throw new PlayerNotFoundException("Player with id: "+id+" not found");
        }
        playerRepository.delete(player.get());
        return "Player with id: "+id+" deleted successfully";
    }

}
