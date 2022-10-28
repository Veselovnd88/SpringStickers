package ru.veselov.springstickers.SpringStickers.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LabelMapper implements RowMapper<LabelSticker> {
    @Override
    public LabelSticker mapRow(ResultSet rs, int rowNum) throws SQLException {
        //проходит по результату загруженному из бд
       LabelSticker labelSticker= LabelFactory.getLabel(rs.getString("article"),
               rs.getString("name"),
                rs.getString("range"),
                rs.getString("pinout"),rs.getString("manufacturer"),
               "", rs.getInt("id"));
       return labelSticker;
    }
}
