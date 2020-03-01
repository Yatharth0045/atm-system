/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Database.JConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yatharth
 */
public class Card {
    private String holderName;
    private String cardNumber;
    private String pin;
    private double balance;
    private static Connection conn;
    private static String sql;
    private static Statement st;
    private static ResultSet rs;
    private static PreparedStatement ps;
    private int count;
    
    /*sql=insert into transaction (cardno,action,tdate,ttime,amount) values
('112233445566','deposit','2018-02-28','12:10:23',200);*/

    public Card(String name, String cardNumber, String pin, double balance) {
        this.holderName = name;
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public Card() { 
    }
   
    public String getName() {
        return holderName;
    }

    public void setName(String name) {
        this.holderName = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public double getBalance() {
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public boolean setCardDetails()
    {
        try {
            conn=JConnection.getSqlConnection();
            sql="insert into cardinfo values(?,?,?,?)";
            //System.out.println(conn);
            //System.out.println("set details function called 1");
            ps=conn.prepareStatement(sql);
            //System.out.println("set details function called 2");
            ps.setString(1,getCardNumber());
            ps.setString(2,getName());
            ps.setInt(3,Integer.parseInt(this.getPin()));
            ps.setFloat(4,(float)this.getBalance());
            //System.out.println("set details function called 3");
            count=ps.executeUpdate();
            //System.out.println("set details function called 4");
            //System.out.println(count);
            ps.close();
            conn.close();
        } catch (SQLException ex) {
            ex.getStackTrace();
            //Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (count != 0) {
            return true;
        } else {
            return false;
        }
    }
    public Card getCardDetails(String cardno)
    {
        Card user=new Card();
        try {
            conn=JConnection.getSqlConnection();
            sql="select * from cardinfo where cardno="+cardno;
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            rs.next();
            user.setCardNumber(rs.getString("cardno"));
            user.setName(rs.getString("name"));
            user.setPin(rs.getString("pin"));
            user.setBalance(rs.getFloat("balance"));
            st.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    public boolean verifyLogin(String cardNo,String pin)
    {            
        boolean b=false;
        try {
            conn=JConnection.getSqlConnection();
            sql="select * from cardinfo where cardno="+cardNo;
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            if (!rs.isBeforeFirst() ) {    
                return b; 
            } 
            rs.next();
            if(cardNo.equals(rs.getString("cardno")) && pin.equals(rs.getString("pin")))
            {
                this.setName(rs.getString("name"));
                this.setCardNumber(rs.getString("cardno"));
                this.setPin(rs.getString("pin"));
                this.setBalance(rs.getFloat("balance"));
                b=true;
            }
            else
            {
                b=false;
            }
        } catch (Exception ex) {
            Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
         return b;
    }
    public boolean verifyOld(String old)
    {
        boolean b=false;
        try {
            String pin=this.getPin();
            conn=JConnection.getSqlConnection();
            sql="select pin from cardinfo where cardno="+this.getCardNumber();
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            rs.next();
            if(pin.equals(old))
            {
                b=true;
            }
            else
            {
                b=false;
            }
        } 
        catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    public boolean updatePin(String newPin)
    {
        boolean b=false;
        try {
            conn=JConnection.getSqlConnection();
            sql="update cardinfo set pin = "+newPin+" where cardno = "+this.cardNumber;
            st=conn.createStatement();
            count=st.executeUpdate(sql);
            if(count!=0)
            {
                b=true;
            }
            else
            {
                b=false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }
    
    public boolean updateAmount(double amt)
    {
        boolean b=false;
        try {
            conn=JConnection.getSqlConnection();
            sql="update cardinfo set balance = "+amt+" where cardno = "+this.cardNumber;
            st=conn.createStatement();
            count=st.executeUpdate(sql);
            if(count!=0)
            {
                b=true;
            }
            else
            {
                b=false;
            }

        } catch (SQLException ex) {
            //Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;        
    }
    public boolean addDTransaction()
    {
        boolean b=false;
        return b;
    }
}