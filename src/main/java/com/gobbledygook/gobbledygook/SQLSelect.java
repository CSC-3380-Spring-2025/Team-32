package com.gobbledygook.gobbledygook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;





public class SQLSelect {
    private JdbcTemplate jbdc;
    String Query = ("Select word FROM words ORDER BY RANDOM() Limit 1"); 
    jbdc.query(Query);
    
}
