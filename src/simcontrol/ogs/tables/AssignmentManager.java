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
import simcontrol.ogs.beans.Assignment;
import simcontrol.ogs.dbaccess.DBType;
import simcontrol.ogs.dbaccess.DBUtil;

/**
 *
 * @author Eric
 */
public class AssignmentManager {
    public static Assignment getRow(int ID) throws SQLException {

        String sql = "SELECT * FROM students WHERE ID = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Assignment assignmentBean = new Assignment();
                assignmentBean.setName(rs.getString("name"));
                assignmentBean.setID(ID);
                assignmentBean.setUserName(rs.getString("userName"));
                assignmentBean.setPassword(rs.getString("password"));
                assignmentBean.setEmailAddress(rs.getString("EmailAddress"));
                assignmentBean.setAccessLevel(rs.getInt("AccessLevel"));
                return assignmentBean;
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

    public static boolean insert(Assignment assignmentBean) throws Exception {

        String sql = "INSERT into admin (name, userName, password, EmailAddress, AccessLevel) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, assignmentBean.getName());
            stmt.setString(2, assignmentBean.getUserName());
            stmt.setString(3, assignmentBean.getPassword());
            stmt.setString(4, assignmentBean.getEmailAddress());
            stmt.setInt(5, assignmentBean.getAccessLevel());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                assignmentBean.setID(newKey);
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

    public static boolean update(Assignment assignmentBean) throws Exception {

        String sql
                = "UPDATE students SET " + "name = ?, "
                + "userName = ?, password = ?, " + "EmailAddress = ?,"
                + "accessLevel = ? WHERE adminId = ?";
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, assignmentBean.getName());
            stmt.setString(2, assignmentBean.getUserName());
            stmt.setString(3, assignmentBean.getPassword());
            stmt.setString(4, assignmentBean.getEmailAddress());
            stmt.setInt(5, assignmentBean.getAccessLevel());
            stmt.setInt(6, assignmentBean.getID());

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
     * Sent Mail Method
     */
    public void sentMail()
    {
        
    }
    
    /**
     * Mark Assignment method
     */
    public void markAssignment()
    {
        
    }
    
    /**
     * Grade Assignment method
     */
    public void gradeAssignment()
    {
        
    }
    
    /**
     * Create Assignment method
     */
    public void createAssignment()
    {
        
    }
    
    //From Course Assignments
    
    /**
     * Get Points Possible Method
     */
    public void getPointsPossible()
    {
        
    }
    
    /**
     * Get Points Grades method
     */
    public void getPointsGrades()
    {
        
    }
}
