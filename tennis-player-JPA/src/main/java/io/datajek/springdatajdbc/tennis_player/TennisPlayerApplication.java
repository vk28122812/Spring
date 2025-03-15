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

	@Autowired
	PlayerRepository repo;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public static void main(String[] args) {
		SpringApplication.run(TennisPlayerApplication.class, args);
	}

	@Override
	public void run(String...args)throws  Exception{
		logger.info("\n\n>>Inserting Player: {}", repo.insertPlayer(new Player("Roger Federer", "Swiss", Date.valueOf("1981-08-08"), 103)));

		logger.info("\n\n>> Inserting Player: {}\n", repo.insertPlayer(
				new Player("Djokovic", "Serbia", Date.valueOf("1987-05-22"), 81)));
		logger.info("\n\n>> Inserting Player: {}\n", repo.insertPlayer(new Player("Thiem", "Austria", new Date(System.currentTimeMillis()), 17)));

		logger.info("\n\n>>Querying Player with id=2: {}", repo.getPlayerById(2));

		logger.info("\n\n>>Updating Player with id=3: {}", repo.updatePlayer(new Player(3,"Thiem", "Austria", Date.valueOf("1993-09-03"), 17)) );

		logger.info("\n\n Deleting Player with id=1");
		repo.deletePlayerById(1);

		logger.info("\n\n >> All Players: {}",repo.getAllPlayers());
	}

}
