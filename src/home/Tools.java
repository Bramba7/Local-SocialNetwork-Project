/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home;

/**
 *
 * @author fernandobrambillademello
 */
public class Tools {
    
    public String convertData(String d){
        String dm = null;
        
        if ("January".equals(d)) {
            dm = "01";
        } else if ("February".equals(d)) {
            dm = "02";
        } else if ("March".equals(d)) {
            dm = "03";
        } else if ("April".equals(d)) {
            dm = "04";
        } else if ("May".equals(d)) {
            dm = "05";
        } else if ("June".equals(d)) {
            dm = "06";
        } else if ("July".equals(d)) {
            dm = "07";
        } else if ("August".equals(d)) {
            dm = "08";
        } else if ("September".equals(d)) {
            dm = "09";
        } else if ("October".equals(d)) {
            dm = "10";
        } else if ("November".equals(d)) {
            dm = "11";
        } else if ("December".equals(d)) {
            dm = "12";
        }
        return dm;
    }
    
    
    
    
}
