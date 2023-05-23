package main;
import entity.Player;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DB_info {
    String[] colHeads = {"age","gender", "HS_football", "HS_card", "HS_meteor", "hunger", "fun", "hygiene", "energy", "veggie_love", "meat_love", "milk_love", "sweet_love"};
    String[][] data = {};
    DefaultTableModel model = new DefaultTableModel(data, colHeads);
//jt = new JTable(data, colHeads);
    JTable jt = new JTable(model);

    public DB_info(){

    }

    public void read(){
        DefaultTableModel model = (DefaultTableModel) jt.getModel();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");

            c = DriverManager.getConnection("jdbc:sqlite:TamaSaurier_DB.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM Saurier;" );
            while ( rs.next() ) {
                Player.getInstance().age = rs.getInt("age");
                Player.getInstance().gender = rs.getInt("gender");
                GamePanel.getInstance().high_score_football = rs.getInt("HS_football");
                GamePanel.getInstance().high_score_card = rs.getInt("HS_card");
                GamePanel.getInstance().high_score_meteor = rs.getInt("HS_meteor");
                Player.getInstance().hunger.level = rs.getFloat("hunger");
                Player.getInstance().fun.level = rs.getFloat("fun");
                Player.getInstance().hygiene.level = rs.getFloat("hygiene");
                Player.getInstance().energy.level = rs.getFloat("energy");
                Player.getInstance().veggie_love = rs.getInt("veggie_love") == 1;
                Player.getInstance().meat_love = rs.getInt("meat_love") == 1;
                Player.getInstance().milk_love = rs.getInt("milk_love") == 1;
                Player.getInstance().sweet_love = rs.getInt("sweet_love") == 1;

                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + "1 " + e.getMessage() );
            System.exit(0);
        }
    }

    public void update(){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:TamaSaurier_DB.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            String sql = "UPDATE Saurier set age = " + Player.getInstance().age;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set gender = " + Player.getInstance().gender;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set hunger = " + Player.getInstance().hunger.level;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set fun = " + Player.getInstance().fun.level;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set hygiene = " + Player.getInstance().hygiene.level;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set energy = " + Player.getInstance().energy.level;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set HS_football = " + GamePanel.getInstance().high_score_football;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set HS_card = " + GamePanel.getInstance().high_score_card;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set HS_meteor = " + GamePanel.getInstance().high_score_meteor;
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set veggie_love = " + ((Player.getInstance().veggie_love) ? 1 : 0);
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set meat_love = " + ((Player.getInstance().meat_love) ? 1 : 0);
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set milk_love = " + ((Player.getInstance().milk_love) ? 1 : 0);
            stmt.executeUpdate(sql);

            sql = "UPDATE Saurier set sweet_love = " + ((Player.getInstance().sweet_love) ? 1 : 0);
            stmt.executeUpdate(sql);

            c.commit();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + "2 " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("update done");
    }

}

