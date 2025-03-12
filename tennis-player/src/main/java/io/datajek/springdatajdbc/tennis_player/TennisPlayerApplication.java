package io.datajek.springdatajdbc.tennis_player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Date;

@SpringBootApplication
public class TennisPlayerApplication implements CommandLineRunner {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PlayerDao playerDao;

	public static void main(String[] args) {
		SpringApplication.run(TennisPlayerApplication.class, args);
	}

	@Override
	public void run(String...args)throws  Exception{
		playerDao.createTournamentTable();
		logger.info("All players: {}", playerDao.getAllPlayers());
//		logger.info("Player with id=1 : {}", playerDao.getPlayerById(1));
		System.out.println("Adding new player 4: " + playerDao.insertPlayer(new Player(4, "Lionel Messi", "Argentina",new Date(System.currentTimeMillis()),46)));
		logger.info("All players: {}", playerDao.getAllPlayers());
		System.out.println("Update player 4 : " + playerDao.updatePlayer(new Player(4, "Lionel Messi", "Argentina",Date.valueOf("1987-06-24"),47)));
		logger.info("All players: {}", playerDao.getAllPlayers());

		System.out.println("Deleting player 4: "+  playerDao.deletePlayerById(4));
		logger.info("All players: {}", playerDao.getAllPlayers());

		logger.info("Players from Switzerland: "+ playerDao.getPlayerByNationality("Switzerland"));

	}

}
