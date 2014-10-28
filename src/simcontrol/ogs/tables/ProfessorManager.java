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
import simcontrol.ogs.dbaccess.DBType;
import simcontrol.ogs.dbaccess.DBUtil;
import simcontrol.ogs.beans.Professor;

/**
 *
 * @author Eric
 */
public class ProfessorManager {
    public static Professor getRow(int ID) throws SQLException {

        String sql = "SELECT * FROM Professor WHERE ID = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Professor professorBean = new Professor();
                professorBean.setName(rs.getString("name"));
                professorBean.setID(ID);
                professorBean.setUserName(rs.getString("userName"));
                professorBean.setPassword(rs.getString("password"));
                professorBean.setEmailAddress(rs.getString("EmailAddress"));
                professorBean.setAccessLevel(rs.getInt("AccessLevel"));
                return professorBean;
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

    public static boolean insert(Professor professorBean) throws Exception {

        String sql = "INSERT into Professor"
                + " (name, userName, password, EmailAddress, AccessLevel) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, professorBean.getName());
            stmt.setString(2, professorBean.getUserName());
            stmt.setString(3, professorBean.getPassword());
            stmt.setString(4, professorBean.getEmailAddress());
            stmt.setInt(5, professorBean.getAccessLevel());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                professorBean.setID(newKey);
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

    public static boolean update(Professor professorBean) throws Exception {

        String sql
                = "UPDATE Professor SET " + "name = ?, "
                + "userName = ?, password = ?, " + "EmailAddress = ?,"
                + "accessLevel = ? WHERE ID = ?";
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, professorBean.getName());
            stmt.setString(2, professorBean.getUserName());
            stmt.setString(3, professorBean.getPassword());
            stmt.setString(4, professorBean.getEmailAddress());
            stmt.setInt(5, professorBean.getAccessLevel());
            stmt.setInt(6, professorBean.getID());

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
     * Appoint TA method
     */
    public void appointTA()
    {
        
    }
    
    /**
     * Create Assignment method
     */
    
    /**
     * View Grade Statistic method
     */
    public void viewGradeStatistic()
    {
        
    }
    
    /**
     * View Report method
     */
    public void viewReport()
    {
        
    }
}
