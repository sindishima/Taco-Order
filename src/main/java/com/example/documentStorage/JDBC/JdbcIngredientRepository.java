package com.example.documentStorage.JDBC;


import com.example.documentStorage.domains.Ingredient;
import com.example.documentStorage.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) { //When Spring creates the JdbcIngredientRepository bean, it
        this.jdbc = jdbc;                                // injects it with Jdbc-Template via the @Autowired annotated construction
    }

    @Override
    public Iterable<Ingredient> findAll() {
        //returns a list of objects
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);  //rowMapper - a callback that will map one object per row
    }
    @Override
    public Ingredient findOne(String id) {
        //returns a single object
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id);
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"),
                              rs.getString("name"),
                              Ingredient.Type.valueOf(rs.getString("type")));
    }

    //UPDATE data into db
    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?)",
                     ingredient.getId(),
                     ingredient.getName(),
                     ingredient.getType().toString());
        return ingredient;
    }


    //OPTIMIZED VERSION
//    @Override
//    public Ingredient findOne(String id) {
//        return jdbc.queryForObject(
//                "select id, name, type from Ingredient where id=?",
//                new RowMapper<Ingredient>() {
//                    public Ingredient mapRow(ResultSet rs, int rowNum)
//                            throws SQLException {
//                        return new Ingredient(
//                                rs.getString("id"),
//                                rs.getString("name"),
//                                Ingredient.Type.valueOf(rs.getString("type")));
//                    };
//                }, id);
//    }
}
