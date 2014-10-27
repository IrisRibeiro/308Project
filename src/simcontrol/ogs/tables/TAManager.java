/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simcontrol.ogs.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import simcontol.ogs.dbaccess.DBType;
import simcontol.ogs.dbaccess.DBUtil;
import simcontrol.ogs.beans.TA;

/**
 *
 * @author Eric, Zain
 */
public class TAManager {
    
    public static TA getRow(int ID) throws SQLException {

        String sql = "SELECT * FROM students WHERE ID = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                TA TABean = new TA();
                TABean.setName(rs.getString("name"));
                TABean.setID(ID);
                TABean.setUserName(rs.getString("userName"));
                TABean.setPassword(rs.getString("password"));
                TABean.setEmailAddress(rs.getString("EmailAddress"));
                TABean.setAccessLevel(rs.getInt("AccessLevel"));
                return TABean;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }

    }

    public static boolean insert(TA TABean) throws Exception {

        String sql = "INSERT into admin (name, userName, password, EmailAddress, AccessLevel) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, TABean.getName());
            stmt.setString(2, TABean.getUserName());
            stmt.setString(3, TABean.getPassword());
            stmt.setString(4, TABean.getEmailAddress());
            stmt.setInt(5, TABean.getAccessLevel());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                TABean.setID(newKey);
            } else {
                System.err.println("No rows affected");
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            if (keys != null) {
                keys.close();
            }
        }
        return true;
    }

    public static boolean update(TA TABean) throws Exception {

        String sql
                = "UPDATE students SET " + "name = ?, "
                + "userName = ?, password = ?, " + "EmailAddress = ?,"
                + "accessLevel = ? WHERE adminId = ?";
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, TABean.getName());
            stmt.setString(2, TABean.getUserName());
            stmt.setString(3, TABean.getPassword());
            stmt.setString(4, TABean.getEmailAddress());
            stmt.setInt(5, TABean.getAccessLevel());
            stmt.setInt(6, TABean.getID());

            int affected = stmt.executeUpdate();
            if (affected == 1) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }

    }
    
    /**
     * Get Assignment method
     */
    public void getAssignment()
    {
        
    }
    
    /**
     * Post Grade method
     */
    public void postGrade()
    {
        
    }
    
    /**
     * build Report Method
     */
    public void buildReport()
    {
        
    }
    
    /**
     * Grade Assignment Method
     */
    public void gradeAssignment()
    {
        
    }
}
