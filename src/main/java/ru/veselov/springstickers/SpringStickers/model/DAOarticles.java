package ru.veselov.springstickers.SpringStickers.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DAOarticles {
/*    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public DAOarticles(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<LabelSticker> index(){
        //LabelMapper - заполняет объекты стикеров заполненных из базы и заполняет лист
        return jdbcTemplate.query("SELECT*FROM articles", new LabelMapper());
    }
    public void addSerials(List<LabelSticker> list){
        for(LabelSticker l: list){
            jdbcTemplate.update("INSERT INTO serial_num(id, num) values(?,?)",
                    l.getId(), l.getSerial());
        }
    }*/
}