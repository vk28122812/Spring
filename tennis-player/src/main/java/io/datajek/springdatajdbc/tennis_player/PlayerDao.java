package io.datajek.springdatajdbc.tennis_player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class PlayerDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTournamentTable(){
        String sql = "CREATE TABLE TOURNAMENT(" +
                "ID INT PRIMARY KEY, " +
                "NAME VARCHAR(255), " +
                "lOCATION VARCHAR(255)" +
                ");";
        jdbcTemplate.execute(sql);
        System.out.println("Table created");
    }

    private static final class PlayerMapper implements RowMapper<Player>{

        @Override
        public Player mapRow(ResultSet resultSet,int rowCount) throws SQLException {
            Player player = new Player();
            player.setId(resultSet.getInt("ID"));
            player.setName(resultSet.getString("NAME"));
            player.setNationality(resultSet.getString("NATIONALITY"));
            player.setBirthDate(resultSet.getDate("BIRTH_DATE"));
            player.setTitles(resultSet.getInt("TITLES"));
            return player;
        }
    }

    public List<Player> getPlayerByNationality(String nationality){
        String sql = "SELECT * FROM PLAYER WHERE NATIONALITY = ?;";
        return jdbcTemplate.query(sql, new PlayerMapper(), new Object[]{nationality} );
    }

    public List<Player> getAllPlayers(){
        String sql = "SELECT * FROM PLAYER;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Player>(Player.class));
    }

    public Player getPlayerById(int id){
        String sql = "SELECT * FROM PLAYER WHERE ID = ?;";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Player>(Player.class), new Object[]{id});
    }

    public int insertPlayer(Player player){
        String sql = "INSERT INTO PLAYER (ID, NAME, NATIONALITY, BIRTH_DATE, TITLES) VALUES(?, ?, ?, ?, ?);";

        return jdbcTemplate.update(sql, new Object[]{player.getId(), player.getName(), player.getNationality(),
                new Timestamp(player.getBirthDate().getTime()),player.getTitles()});
    }

    public int updatePlayer(Player player){
        String sql = "UPDATE PLAYER " +
                "SET NAME=?, NATIONALITY=?, BIRTH_DATE=?, TITLES=?" +
                "WHERE ID=?";

        return jdbcTemplate.update(sql, new Object[]{player.getName(), player.getNationality(),
                new Timestamp(player.getBirthDate().getTime()),player.getTitles(),player.getId()});
    }

    public int deletePlayerById(int id){
        String sql = "DELETE FROM PLAYER WHERE ID = ?;";
        return jdbcTemplate.update(sql, new Object[]{id});
    }


}
