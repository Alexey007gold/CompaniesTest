package db;

import model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtils {
 
    public static List<Company> getCompaniesTree() throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        String sql = "SELECT * FROM companies12341024.companies WHERE PARENT_COMPANY_NAME IS NULL";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Company> list = new ArrayList<>();
        while (rs.next()) {
          String name = rs.getString("COMPANY_NAME");
          int earning = rs.getInt("ESTIMATED_EARNING");
          Company company = new Company(name, earning);
          list.add(company);
        }

        list.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));

        for (Company company : list) {
          addChildrenToCompanyRecursively(company);
        }
        return list;
    }

    private static void addChildrenToCompanyRecursively(Company company) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        String sql = "SELECT * FROM companies12341024.companies WHERE PARENT_COMPANY_NAME ='" + company.getName() + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        List<Company> children = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("COMPANY_NAME");
            int earning = rs.getInt("ESTIMATED_EARNING");
            Company cmp = new Company(name, company, earning);
            addChildrenToCompanyRecursively(cmp);
            children.add(cmp);
        }
//        if (!children.isEmpty())
        children.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        company.setChildren(children);
    }

    public static boolean companyWithNameExists(String companyName) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        String sql = "SELECT * FROM companies12341024.companies WHERE COMPANY_NAME='" + companyName + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public static void updateCompany(String previousName, String companyName,
                                     String parentName, int earnings) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();

        if (!companyWithNameExists(previousName)) {
            throw new Error("Company with such name does not exist");
        }
        if (!parentName.isEmpty() && !companyWithNameExists(parentName)) {
            throw new Error("Such parent does not exist!");
        }
        //check if user isn't trying to make company a child of itself
        if (parentName.equals(companyName)) {
            throw new Error("Company can't become a child of itself");
        }
        //check if user isn't trying to set company's child as its parent
        if (Objects.equals(previousName, getParent(parentName))) {
            throw new Error("Company can't become a child of its child");
        }
        //check if user has sanity
        if (parentName.equals(previousName)) {
            throw new Error("Don't do it this way");
        }
        String sql = "Update companies12341024.companies set COMPANY_NAME =?, PARENT_COMPANY_NAME=?," +
              "ESTIMATED_EARNING=? where COMPANY_NAME=?";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, companyName);
        pstm.setString(2, parentName.isEmpty() ? null : parentName);
        pstm.setInt(3, earnings);
        pstm.setString(4, previousName);
        pstm.executeUpdate();

        if (!previousName.equals(companyName)) {
            sql = "SELECT * FROM companies12341024.companies WHERE PARENT_COMPANY_NAME ='" + previousName + "'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                sql = "Update companies12341024.companies set PARENT_COMPANY_NAME=? where COMPANY_NAME=?";
                pstm = conn.prepareStatement(sql);
                pstm.setString(1, companyName);
                pstm.setString(2, rs.getString("COMPANY_NAME"));
                pstm.executeUpdate();
            }
        }
    }

    private static String getParent(String companyName) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        String sql = "SELECT * FROM companies12341024.companies WHERE COMPANY_NAME='" + companyName + "'";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getString("PARENT_COMPANY_NAME");
        }
        return null;
    }

    public static void addCompany(String companyName, String parentName, int earnings)
            throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        if (!parentName.isEmpty() && !companyWithNameExists(parentName)) {
            throw new Error("Such parent does not exist!");
        }
        //check if user isn't trying to make company a child of itself
        if (parentName.equals(companyName)) {
            throw new Error("Company can't become a child of itself");
        }
        String sql = "Insert into companies12341024.companies(COMPANY_NAME, PARENT_COMPANY_NAME, ESTIMATED_EARNING) " +
              "values (?,?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);

        pstm.setString(1, companyName);
        pstm.setString(2, parentName.isEmpty() ? null : parentName);
        pstm.setFloat(3, earnings);

        pstm.executeUpdate();
    }

    public static void deleteCompany(String companyName) throws SQLException, ClassNotFoundException {
        Connection conn = ConnectionUtils.getConnection();
        String sql = "DELETE FROM companies12341024.companies WHERE COMPANY_NAME ='" + companyName + "'";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

        sql = "SELECT * FROM companies12341024.companies WHERE PARENT_COMPANY_NAME ='" + companyName + "'";
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            sql = "Update companies12341024.companies set PARENT_COMPANY_NAME=? where COMPANY_NAME=?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, null);
            pstm.setString(2, rs.getString("COMPANY_NAME"));
            pstm.executeUpdate();
        }
    }
 
}