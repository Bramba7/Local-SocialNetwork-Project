/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

/**
 *
 * @author Fernando Brambilla de Mello
 */
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Connection {

    // Proporties
    //private Connection  connection  = null;
    private Statement   statement   = null;
    private ResultSet   set         = null;
    private String hostname;
    private String port;
    private String username;
    private String password;
    private String database;
    private int UserID;
    private java.sql.Connection connection;

	// Constructors
    public Connection() {
        this.hostname = "ADD HERE THE IP ADDRESS OF YOUR SQL SERVER";
    //  this.hostname = "localsjava.ddns.net";
        this.port = "3306";
        this.username = "root";
        this.password = "toor";
        this.database = "locals";
    }

    // Functions
    public void connect() {
        String url = "jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database;
        try {
            //     DriverManager.setLoginTimeout(10);
            this.connection = DriverManager.getConnection(url, this.username, this.password);
            System.out.println("Connected database successfully...");
        } catch (SQLException ex) {
            System.out.println("Failed to create the database connection.");
            this.connection = null;
        }
    }

    public void disconnect() {
        try {
            this.connection.close();
            System.out.println("Disconnect database successfully...");
        } catch (SQLException e) {
            this.connection = null;
            System.out.println("Failed to create the database disconnect.");
        }
        this.connection = null;

    }
    
    public ResultSet Query(String query) throws SQLException{

        try {
            statement = this.connection.createStatement();
            set = statement.executeQuery(query);
        }
        catch (Exception e) {
            System.out.println("Exception in query method:\n" + e.getMessage());
        }
        return set;
    }
    

    public ResultSet preparedStatement(String query, ArrayList<Object> values) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }

        PreparedStatement ps = this.connection.prepareStatement(query);
        for (int i = 1; i <= values.size(); i++) {
            ps.setObject(i, values.get(i - 1));
        }
        ps.execute();

        return ps.getResultSet();
    }

    public void query(String query) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }

        Statement sm = this.connection.createStatement();
        sm.executeUpdate(query);
        //return sm.executeQuery(query);
    }
	

    
    
    
    
    
    //*************************************** password check **********************************************
    public int loginTest(String u, String p) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        
        Statement sm = this.connection.createStatement();
        String sql = "Select firstname,pwd,person_id,zipcode from locals_person where email = '" + u + "'";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
            return 0;
        } else {
            do {
                System.out.println(UserID);
                String password = rs.getString("pwd");
                String zip = rs.getString("zipcode");
                this.setUserID(rs.getInt("person_id"));
                       
                if (p.equals(password) && zip.equals(Integer.toString(90405))) {
                    System.out.println("Local");    
                    return 2;
                }else if (p.equals(password) ) {
                    System.out.println("User");    
                    return 1;
            }
            } while (rs.next());
        }

        return 0;
        
    }
    
 //*********************************** get user ID *******************************   
   public int userId(String u) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        
        Statement sm = this.connection.createStatement();
        String sql = "Select person_id from locals_person where email = '" + u + "'";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
            return 0;
        } else {
            do {
                return rs.getInt("person_id");
            } while (rs.next());
        }
    }
   //*********************************** get info about user *******************************   
   public String genericPerson(String print, String cond, String in) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        
        Statement sm = this.connection.createStatement();
        String sql = "Select * from locals_person where " + cond + " = " + in + " ";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
            return "0";
        } else {
            do {
                return rs.getString(print);
            } while (rs.next());
        }
    }
    //*********************************** add activity ******************************* 
    public String addAct(String print, String cond, String in) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        
        Statement sm = this.connection.createStatement();
        String sql = "Select * from locals_person where " + cond + " = " + in + " ";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("ResultSet in empty in Java");
            return "0";
        } else {
            do {
                return rs.getString(print);
            } while (rs.next());
        }
    }
    //*********************************** test activity ******************************* 
    // 0 -> false
    // 1 -> true
        public int activityCheck(String a) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        Statement sm = this.connection.createStatement();
        String sql = "Select act_code from locals_act where act_code = '" + a + "'";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("Activity is not OK");
            return 0;
        } else {
            do {
            System.err.println("Activity OK");
            } while (rs.next());
        }

        return 1;
    }
        //*********************************** Check activity ******************************* 
    // 0 -> false
    // 1 -> true
        public int activityCheckExistUser(int u, String a) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        Statement sm = this.connection.createStatement();
        String sql = "select *\n" +
                     "from locals_person P\n" +
                     "Join locals_act A ON P.person_id = A.act_creator \n" +
                     "join locals_enroll E on E.act_code = A.act_code and E.enroll_user = " + u + " and A.act_code = " + a + " ";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("Activity is not OK User + ACT");
            return 0;
        } else {
            do {
            System.err.println("Activity OK User + ACT");
            } while (rs.next());
        }

        return 1;
    }
                //*********************************** Check activity Owner ******************************* 
    // 0 -> false
    // 1 -> true
        public int activityCheckExistOwner(String a, int owner) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        Statement sm = this.connection.createStatement();
        String sql = "select A.act_code,A.act_type, P.firstname,P.zipcode\n" +
                     "from locals_person P\n" +
                     "Join locals_act A \n" +
                     "on P.person_id = A.act_creator and P.person_id= " + owner + " and A.act_code = " + a + " ";
        
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("Activity is not OK Owner");
            return 0;
        } else {
            do {
            System.err.println("Activity OK OK Owner");
            } while (rs.next());
        }

        return 1;
    }
    //*********************************** test Email ******************************* 
    public int emailTest(String e) throws SQLException {
        if (!this.isConnected()) {
            this.connect();
        }
        Statement sm = this.connection.createStatement();
        String sql = "Select email from locals_person where email = '" + e + "'";
        ResultSet rs = sm.executeQuery(sql);
        //Extract data from result set
        if (rs.next() == false) {
            System.out.println("Email is unique");
            return 1;
        } else {
            do {
            System.err.println("Email is not unique");
            } while (rs.next());
        }

        return 0;
    }

    public int updateQuery(String query) throws SQLException {
        if (this.isConnected()) {
            this.connect();
        }

        Statement sm = this.connection.createStatement();

        return sm.executeUpdate(query);
    }

    // Getters and setters
    public int getUserID() {
        return this.UserID;
    }

    public String getHostname() {
        return this.hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public java.sql.Connection getConnection() {
        return this.connection;
    }

    public boolean isConnected() {
        try {
            return this.connection != null && !this.connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    Statement createStatment() {
          
       throw new UnsupportedOperationException("Not supported yet.");  
   }

}
