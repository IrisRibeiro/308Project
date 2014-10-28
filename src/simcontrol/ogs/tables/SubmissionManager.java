package simcontrol.ogs.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import simcontrol.ogs.beans.Submission;
import simcontrol.ogs.dbaccess.DBType;
import simcontrol.ogs.dbaccess.DBUtil;

/**
 *
 * @author Eric
 */
public class SubmissionManager {
    public static Submission getRow(int studentID, int assignmentID) throws SQLException {

        String sql = "SELECT * FROM Submissions WHERE studentID = ? AND "
                + "assignmentID = ?";
        ResultSet rs = null;

        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            stmt.setInt(1, studentID);
            stmt.setInt(2, assignmentID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Submission submissionBean = new Submission();
                submissionBean.setStudentID(studentID);
                submissionBean.setAssignmentID(assignmentID);
                submissionBean.setGraderID(rs.getInt("graderID"));
                submissionBean.setSubmissionID(rs.getInt("submissionID"));
                submissionBean.setGrade(rs.getDouble("grade"));
                submissionBean.setComments(rs.getString("comments"));
                submissionBean.setPath(rs.getString("path"));
                submissionBean.setDateFlag(rs.getString("dateFlag"));
                submissionBean.setSubmissionTime(rs.getString("submissionTime"));
                return submissionBean;
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

    public static boolean insert(Submission submissionBean) throws Exception {

        String sql = "INSERT into Submission"
                + " (studentID, assignmentID, graderID"
                + ", grade, comments, path, dateFlag, submissionTime) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet keys = null;
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {

            stmt.setInt(1, submissionBean.getStudentID());
            stmt.setInt(2, submissionBean.getAssignmentID());
            stmt.setInt(3, submissionBean.getGraderID());
            stmt.setDouble(4, submissionBean.getGrade());
            stmt.setString(5, submissionBean.getComments());
            stmt.setString(6, submissionBean.getPath());
            stmt.setString(7, submissionBean.getPath());
            stmt.setString(8, submissionBean.getSubmissionTime());
            int affected = stmt.executeUpdate();

            if (affected == 1) {
                keys = stmt.getGeneratedKeys();
                keys.next();
                int newKey = keys.getInt(1);
                submissionBean.setSubmissionID(newKey);
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

    public static boolean update(Submission submissionBean) throws Exception {

        String sql
                = "UPDATE Submission SET graderID = ?, "
                + "submissionID = ?, grade = ?, comments = ?, "
                + "path = ?, dateFlag = ?, submissionTime = ? WHERE studentID = ? "
                + "and assignmentID = ?";
        try (
                Connection conn = DBUtil.getConnection(DBType.MYSQL);
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(8, submissionBean.getStudentID());
            stmt.setInt(9, submissionBean.getAssignmentID());
            stmt.setInt(1, submissionBean.getGraderID());
            stmt.setInt(2, submissionBean.getSubmissionID());
            stmt.setDouble(3, submissionBean.getGrade());
            stmt.setString(4, submissionBean.getComments());
            stmt.setString(5, submissionBean.getPath());
            stmt.setString(6, submissionBean.getPath());
            stmt.setString(7, submissionBean.getSubmissionTime());

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
