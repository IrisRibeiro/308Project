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
import simcontrol.ogs.beans.Manager;

/**
 *
 * @author Eric
 */
public class SiteManagerManager {
    public static Manager getRow(int ID) throws SQLException {

        String sql = "SELECT * FROM students WHERE ID = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Manager managerBean = new Manager();
                managerBean.setName(rs.getString("name"));
                managerBean.setID(ID);
                managerBean.setUserName(rs.getString("userName"));
                managerBean.setPassword(rs.getString("password"));
                managerBean.setEmailAddress(rs.getString("EmailAddress"));
                managerBean.setAccessLevel(rs.getInt("AccessLevel"));
                return managerBean;
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

    public static boolean insert(Manager managerBean) throws Exception {

        String sql = "INSERT into admin (name, userName, password, EmailAddress, AccessLevel) "
                + "VALUES (?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setString(1, managerBean.getName());
            stmt.setString(2, managerBean.getUserName());
            stmt.setString(3, managerBean.getPassword());
            stmt.setString(4, managerBean.getEmailAddress());
            stmt.setInt(5, managerBean.getAccessLevel());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                managerBean.setID(newKey);
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

    public static boolean update(Manager managerBean) throws Exception {

        String sql
                = "UPDATE students SET " + "name = ?, "
                + "userName = ?, password = ?, " + "EmailAddress = ?,"
                + "accessLevel = ? WHERE adminId = ?";
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, managerBean.getName());
            stmt.setString(2, managerBean.getUserName());
            stmt.setString(3, managerBean.getPassword());
            stmt.setString(4, managerBean.getEmailAddress());
            stmt.setInt(5, managerBean.getAccessLevel());
            stmt.setInt(6, managerBean.getID());

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
}
