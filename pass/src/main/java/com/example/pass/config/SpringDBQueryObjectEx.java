package com.example.pass.config;

import com.example.pass.entity.GetOrder;
import com.example.pass.entity.TelegramUser;
import com.example.pass.repository.TelegramUsersRepository;
import com.example.pass.telegram.TelegramController;
import lombok.var;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.SqlQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpringDBQueryObjectEx{
//        implements RowMapper<GetOrder> {

//    TelegramController telegramController;
//
//    SpringDBQueryObjectEx(TelegramController telegramController){
//    this.telegramController = telegramController;
//}
//
//    @Autowired
//    private org.hibernate.SessionFactory sessionFactory;
//    Session session = sessionFactory.openSession();
//
//
//    public <GetOrder> List<GetOrder> executeSqlQuery(Class<GetOrder> c, String sql, Object[]params, ResultTransformer rt){
//            try{
//                    session.beginTransaction();
//                    SqlQuery sqlQuery = session.createSQLQuery(sql);
//
//                }finally {
//                    session.close();
//                }
//}
//    @Override
//    public GetOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
//        GetOrder getOrder = new GetOrder();
//        getOrder.setId(rs.getLong("ID"));
//        getOrder.setDelivery_type(rs.getString("delivery_type"));
//        getOrder.setInstime(rs.getTimestamp("instime"));
//        return getOrder;
//    }
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;

//    public GetOrder findByDeliveryType(String delivery_type) {
//
//        String sql = "SELECT delivery_type from billing.orders WHERE instime >= current_timestamp - interval '30 seconds'";
//
//        return jdbcTemplate.queryForObject(sql, new Object[]{delivery_type}, new SpringDBQueryObjectEx());
//
//    }
//public List<GetOrder> findAll() {
//
//    String sql = "SELECT delivery_type from billing.orders WHERE instime >= current_timestamp - interval '30 seconds'";
//
//    return jdbcTemplate.query(
//            sql,
//            (rs, rowNum) ->
//                    new GetOrder(
//                            rs.getLong("id"),
//                            rs.getString("delivery_type"),
//                            rs.getTimestamp("instime")
//                    )
//    );
//}
//
//public void function(){
//    for (int i = 0; i < findAll().size(); i++) {
//        String type = findAll().get(i).getDelivery_type();
//        telegramController.sendMessagenotification(type);
//    }
//}

//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    public String findOrder() {
//
//        String sql = "SELECT delivery_type from billing.orders WHERE instime >= current_timestamp - interval '30 seconds'";
//
//        System.out.println("sql: " + sql);
//        return jdbcTemplate.queryForObject(
//                sql, String.class);
//    }


    static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    //  Database credentials
    static final String USER = "postgres";
    static final String PASS = "postgres";

    public static void main() {
//        TelegramController telegramController = new TelegramController();
        Connection conn = null;
        Statement stmt = null;
        var ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.example.pass");
        ctx.refresh();

        var telegramController = ctx.getBean(TelegramController.class);
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.       getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "SELECT * from billing.orders WHERE instime >= current_timestamp - interval '30 seconds'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                System.out.println(rs.getString("delivery_type") + " ---------- delivery type ");
                String type = rs.getString("delivery_type");
                System.out.println(rs.getTimestamp("instime") + "  ");
                System.out.println("type : -> " + type);
                telegramController.sendMessagenotification(type);
                //                for (int i = 0; i < number; i++) {
//                        telegramController.sendMessage(telegramUser.get(i).getChatUserId(),'Есть новая доставка');
//                }

            }
            rs.close();
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end JDBCExample

//    public static void main(String[] args) throws SQLException {
//
//        var ds = new SimpleDriverDataSource();
//        ds.setDriverClass(org.postgresql.Driver.class);
//        ds.setUrl("jdbc:postgresql://localhost:80/test");
//        ds.setUsername("postgres");
//        ds.setPassword("postgres");
//        var rm = (RowMapper<GetOrder>) (ResultSet result, int rowNum) -> {
//
//            var getOrder = new GetOrder();
//
//            getOrder.setId(result.getLong("id"));
//            getOrder.setDelivery_type(result.getString("delivery_type"));
//            getOrder.setInstime(result.getTimestamp("instime"));
//
//            return getOrder;
//        };
//        var sql = "SELECT * FROM billing.orders";
//
//        var jtm = new JdbcTemplate(ds);
//        var rows = (List<Map<String, Object>>) jtm.queryForList(sql);
//        int numOfOrders = jtm.queryForObject(sql, Integer.class);
//        rows.forEach(System.out::println);
//        System.out.format("There are %d orders in the table", numOfOrders);
//        System.out.println(rows + "/n");
//        System.out.println(" --------------- ");
//
//
//    }