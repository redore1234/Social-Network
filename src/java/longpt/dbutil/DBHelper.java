/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longpt.dbutil;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author phamt
 */
public class DBHelper implements Serializable{
    public static Connection makeConnection() throws NamingException, SQLException {
        Context curContext = new InitialContext();
        Context tomcat = (Context) curContext.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcat.lookup("DB");
        Connection con = ds.getConnection();
        
        return con;
    }
}
