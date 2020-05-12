/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import javafx.util.Pair;

/**
 *
 * @author moallemi_faraz
 */
public class LocalsDB 
{
    Connection conn;

    public LocalsDB()
    {
        try 
        {
            /* set user id and password on server */
            Properties props = new Properties();
            props.put("user", "root");
            props.put("password", "toor");
            /* establish connections to DB */
            String dbURL = "jdbc:mysql://localsjava.ddns.net/locals";
            conn = DriverManager.getConnection(dbURL, props);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        //this.hostname = “localsjava.ddns.net”;
        //this.port = “3306";
        //this.username = “root”;
        //this.password = “toor”;
        //this.database = “locals”;        
    }

    public void insert(int userInfo, String enroll, String msg)
    {
        try
        {
            Statement stmt = conn.createStatement();
            String cmd = "INSERT INTO locals_message (enroll_id, sending, msg, regMsg_date) VALUES(";
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            cmd += "'" + enroll + "', " + userInfo + ", '" + msg + "','" + timestamp + "')";
            System.out.println("SQL: " + cmd);
            stmt.execute(cmd);
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public String[] getAllMessages() 
    {
        ArrayList<String> list = new ArrayList<>();
        try 
        {
            Statement stmt = conn.createStatement();
            String cmd = "SELECT msg, regMsg_date FROM locals_message";
            System.out.println("SQL: " + cmd);
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next())
            {
                String msg = rs.getString(1);
                String time = rs.getString(2);
                System.out.println("got: " + msg + " at: " + time);
                list.add(msg + "(" + time + ")");
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        String[] array = list.toArray(new String[list.size()]);
        return array;
    }

    public String[] getMessagesFor(String activity)
    {
        ArrayList<String> list = new ArrayList<>();
        try 
        {
            // get other users with same activity code
            Statement stmt1 = conn.createStatement();
            String cmd1 = "SELECT enroll_user FROM locals_enroll WHERE act_code = " + activity;
            System.out.println("SQL: " + cmd1);
            ResultSet rs1 = stmt1.executeQuery(cmd1);
            while (rs1.next())
            {
                String sameActUser = rs1.getString(1);
                // get messages for enrolled users
                Statement stmt2 = conn.createStatement();
                String cmd2 = "SELECT msg, regMsg_date FROM locals_message WHERE sending = " + sameActUser;
                System.out.println("SQL: " + cmd2);
                ResultSet rs2 = stmt2.executeQuery(cmd2);
                while (rs2.next())
                {
                    String msg = rs2.getString(1);
                    String time = rs2.getString(2);
                    System.out.println("got: " + msg + " at: " + time);
                    list.add(msg + "(" + time + ")");
                }
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        String[] array = list.toArray(new String[list.size()]);
        return array;
    }

    public String[] getUsers()
    {
        ArrayList<String> list = new ArrayList<>();
        try 
        {
            Statement stmt = conn.createStatement();
            String cmd = "SELECT msg FROM locals_message";
            System.out.println("SQL: " + cmd);
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next())
            {
                String msg = rs.getString(1);
                System.out.println("got: " + msg);
                int index = msg.indexOf(':');
                list.add(msg.substring(0, index));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        Set<String> s = new LinkedHashSet<>(list);
        String[] array = s.toArray(new String[s.size()]);
        return array;
    }

    public String getUserName(int userInfo)
    {
        String userName = "";
        try 
        {
            Statement stmt = conn.createStatement();
            String cmd = "SELECT firstname, lastname FROM locals_person WHERE person_id = " + userInfo + "";
            System.out.println("SQL: " + cmd);
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next()) 
            {
                userName += rs.getString(1) + " ";
                userName += rs.getString(2);
                System.out.println("got: " + userName);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return userName;
    }

    public String[] getAllActivities()
    {
        ArrayList<String> list = new ArrayList<>();
        try 
        {
            Statement stmt = conn.createStatement();
            String cmd = "SELECT act_code, act_type FROM locals_act";
            System.out.println("SQL: " + cmd);
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next())
            {
                String code = rs.getString(1);
                String type = rs.getString(2);
                System.out.println("got: " + code + " at: " + type);
                list.add(code + " - " + type);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        String[] array = list.toArray(new String[list.size()]);
        return array;
    }

    public HashMap<String, Pair<String, String>> getUserActivities(int userInfo)
    {
        HashMap<String, Pair<String, String>> list = new HashMap<>();
        try 
        {
            Statement stmt = conn.createStatement();
            String cmd = "SELECT act_code, enroll_id FROM locals_enroll WHERE enroll_user = " + userInfo;
            System.out.println("SQL: " + cmd);
            ResultSet rs = stmt.executeQuery(cmd);
            while (rs.next()) 
            {
                String code = rs.getString(1);
                String id = rs.getString(2);
                System.out.println("got: " + code);
                // get activity name
                Statement stmt2 = conn.createStatement();
                String cmd2 = "SELECT act_type FROM locals_act WHERE act_code = " + code;
                System.out.println("SQL: " + cmd2);
                ResultSet rs2 = stmt2.executeQuery(cmd2);
                String actName = "";
                while (rs2.next())
                {
                    actName = rs2.getString(1);
                }
                list.put(id, new Pair(code, actName));
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return list;
    }
}
