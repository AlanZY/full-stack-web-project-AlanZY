import com.google.gson.Gson;

import java.sql.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;

import static spark.Spark.*;
import spark.template.freemarker.FreeMarkerEngine;
import spark.ModelAndView;
import static spark.Spark.get;

import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import static javax.measure.unit.SI.KILOGRAM;
import javax.measure.quantity.Mass;
import org.jscience.physics.model.RelativisticModel;
import org.jscience.physics.amount.Amount;

import com.heroku.sdk.jdbc.DatabaseUrl;


public class Main {

  public static void main(String[] args) {

     Gson gson = new Gson();

    port(Integer.valueOf(System.getenv("PORT")));
    staticFileLocation("/public");

    get("/hello", (req, res) -> "Hello World");

	/*
    get("/user", (request, response) -> {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("message", "Hello World!");

            return new ModelAndView(attributes, "user.ftl");
        }, new FreeMarkerEngine());
*/
    get("/db", (req, res) -> {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try {
        connection = DatabaseUrl.extract().getConnection();

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add( "Read from DB: " + rs.getTimestamp("tick"));
        }

        attributes.put("results", output);
        return new ModelAndView(attributes, "db2.ftl");
      } catch (Exception e) {
        attributes.put("message", "There was an error: " + e);
        return new ModelAndView(attributes, "error.ftl");
      } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }
    }, new FreeMarkerEngine());

    get("/user_info", (req, res) ->
    {
      Connection connection = null;
      Map<String, Object> attributes = new HashMap<>();
      try{
      connection = DatabaseUrl.extract().getConnection();
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user_info (user_email varchar(100),  user_password  varchar(30),  user_name  varchar(30) )");
    //  stmt.executeUpdate("INSERT INTO users_info VALUES ('user_email','user_password','user_name')");
      ResultSet rs = stmt.executeQuery("SELECT user_email, user_password FROM user_info");
      ArrayList<String> output = new ArrayList<String>();

    while(rs.next())
    {
       output.add("read user " + "email: " + rs.getString("user_email") + "     password: " + rs.getString("user_password") );
     }
     attributes.put("results",output);
     return new ModelAndView(attributes, "user_info.ftl");
     } catch (Exception e) {
     attributes.put("message", "There was an error: " + e);
     return new ModelAndView(attributes, "error.ftl");
     } finally {
     if (connection != null) try{connection.close();} catch(SQLException e){}
    }}, new FreeMarkerEngine());





      get("/user_image", (req, res) ->
      {
        Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        try{
        connection = DatabaseUrl.extract().getConnection();
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user_info_image (user_name varchar(100),  user_image  varchar(10000000) )");
       //stmt.executeUpdate("INSERT INTO user_info_image VALUES ('Smith','it should be dataurl data')");
        ResultSet rs = stmt.executeQuery("SELECT user_name, user_image FROM user_info_image");
        ArrayList<String> output = new ArrayList<String>();

      while(rs.next())
      {
         output.add("read user " + "name: " + rs.getString("user_name") + "     photo: " + rs.getString("user_image") );
       }
       attributes.put("results",output);
       return new ModelAndView(attributes, "image.ftl");
       } catch (Exception e) {
       attributes.put("message", "There was an error: " + e);
       return new ModelAndView(attributes, "error.ftl");
       } finally {
       if (connection != null) try{connection.close();} catch(SQLException e){}
      }}, new FreeMarkerEngine());


      // add user image Database
      post("/user_info_image", (req, res) ->
      {
        Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        try{
        connection = DatabaseUrl.extract().getConnection();
         System.out.println(req.body());
        JSONObject obj=new JSONObject(req.body());

        String username_image=obj.getString("edit_username");
        String image=obj.getString("edit_userimage");
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO user_info_image (user_name,  user_image )"+ "VALUES('" + username_image + "','" + image + "')");//
      //  stmt.executeUpdate("INSERT INTO users_info_image VALUES ('user_email','user_password','user_name')");
        return req.body();
      }catch(Exception e){
        System.err.println("Exception: "+e);
      return e.getMessage();
             } finally {
              if (connection != null) try{connection.close();} catch(SQLException e){}
            }});





    post("/adduser",(req,res)->
      {
        Connection connection = null;
        Map<String, Object> attributes = new HashMap<>();
        try{
        connection = DatabaseUrl.extract().getConnection();
        System.out.println(req.body());
        JSONObject obj = new JSONObject(req.body());


        String email = obj.getString("signup-email");
        String password = obj.getString("signup-password");
       Statement stmt = connection.createStatement();
       stmt.executeUpdate("INSERT INTO user_info(user_email, user_password, user_name)" +
                "VALUES('" + email + "', '" + password + "', 'null')");
       return req.body();
       } catch (Exception e) {
         System.err.println("Exception: "+ e);
          return e.getMessage();
       } finally {
        if (connection != null) try{connection.close();} catch(SQLException e){}
      }});


            get("/get_user_image", (req, res) ->
            {
              Connection connection = null;
              res.type("application/json");
              Map<String, Object> attributes = new HashMap<>();
              try{
              connection = DatabaseUrl.extract().getConnection();
              Statement stmt = connection.createStatement();
              ResultSet rs = stmt.executeQuery("SELECT user_image FROM user_info_image WHERE user_name='Sam'");
              ArrayList<JSONObject> resList = new ArrayList<JSONObject>();

              ResultSetMetaData rsMeta=rs.getMetaData();
              int columnCnt=rsMeta.getColumnCount();
              ArrayList<String> columnNames=new ArrayList<String>();
              for(int i=1;i<=columnCnt;i++)
              {
                columnNames.add(rsMeta.getColumnName(i).toUpperCase());
              }

              while(rs.next())
              {
                JSONObject obj=new JSONObject();
                for(int i=1;i<=columnCnt;i++)
                {
                  String key=columnNames.get(i-1);
                  String value=rs.getString(i);
                  obj.put(key,value);
                }
                resList.add(obj);
              }
              res.status(200);
              return resList;

          } catch (Exception e) {
              attributes.put("message", "There was an error: " + e);
              return attributes;
          } finally {
              if (connection != null) try{connection.close();} catch(SQLException e){}
          }
        });







/*
            get("/user", (req, res) -> {
              ArrayList<String> users = new ArrayList<String>();
              users.add("John Doe");
              users.add("Tony Doe");
              users.add("test one");

              Map<String, Object> attributes = new HashMap<>();
              attributes.put("users", users);


               return new ModelAndView(attributes, "user.ftl");
            }, new FreeMarkerEngine());
*/

            get("/api/timeline_info", (req, res) -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("header_username","Smith");
                    data.put("title1", "sport");
                    data.put("content1", "today night, gym");
                    data.put("image1", "background: #FFC1C1;");
                    data.put("title2","sport");
                    data.put("content2", "monday moring, swimming with John");
                    data.put("image2", "background: #BFEFFF;");
                    data.put("title3","sport");
                    data.put("content3", "friday, a basketball competition");
                    data.put("image3", "background: #FFC1C1;");
                    return data;
                }, gson::toJson);

/*
                get("/getuser", (req, res) -> {

                  Connection connection = null;
                  List<Object> data = new ArrayList<>();
                  connection = DatabaseUrl.extract().getConnection();
                //  JSONObject obj = new JSONObject(req.body());

                //  String email = obj.getString("loginin-email");
                //  String password = obj.getString("loginin-password");
                  Statement stmt = connection.createStatement();
                  ResultSet rs = stmt.executeUpdate("SELECT user_name FROM user_info" +
                          "WHERE user_email='john2@gmail.com' ");

                    Map<String, Object> output = new HashMap<>();
                  while (rs.next()) {
                  output.put("user_email" , rs.getString("user_name"));
                  }
                  data.add(output);
                  return data;
                  }, gson::toJson);


*/







                get("/recommendation", (req, res) -> {
                  ArrayList<String> users = new ArrayList<String>();
                  users.add("John Doe");
                  users.add("Smith");
                  users.add("Daniel");
                  users.add("Mark");
                  users.add("Ellen");
                  users.add("Lily");
                  users.add("Julio");
                  users.add("Chela");
                  users.add("Bells");


                  ArrayList<String> images = new ArrayList<String>();
                  images.add("picture/image1.jpg");
                  images.add("picture/image2.jpg");
                  images.add("picture/image3.jpg");
                  images.add("picture/image4.jpg");
                  images.add("picture/image5.jpg");
                  images.add("picture/image6.jpg");
                  images.add("picture/image7.jpg");
                  images.add("picture/image8.jpg");
                  images.add("picture/image9.jpg");


                  Map<String, Object> attributes = new HashMap<>();
                  attributes.put("users", users);
                  attributes.put("images", images);



                   return new ModelAndView(attributes, "recommendation.ftl");
                  }, new FreeMarkerEngine());


                    get("/api/contact.xml", (req, res ) ->
                    {
                      Map<String, Object> data = new HashMap<>();
                      data.put("username","Smith");
                      String xml= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                                  "<user_profile>" +
                                          "<user_name> Smith </user_name>"+
                                          "<num_timeline> 10 </num_timeline>" +
                                  "</user_profile>" ;
                      res.type("text/xml");
                      return xml;
                    });



}
}
