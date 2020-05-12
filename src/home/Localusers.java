/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

/**
 *
 * @author zackdavis
 */
public class Localusers {
    
    private String firstname, actType, day, time, location;
    private int codeAct;
    
    public Localusers(int codeAct, String actType, String day, String time, String location, String firstname){
       
        this.codeAct = codeAct; 
        this.actType = actType;
        this.location = location;
        this.day = day; 
        this.time = time;
        this.firstname = firstname; 
    }
    public String getLocation(){
       return location; 
    }
     public int getCodeAct(){
       return codeAct; 
    }
    public String getActType(){
       return actType; 
    }
     public String getDay(){
       return day; 
    }
     public String getTime(){
       return time; 
    }
     public String getFirstname(){
       return firstname; 
    }
  
    
}
