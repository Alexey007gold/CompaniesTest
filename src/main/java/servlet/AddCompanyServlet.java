package servlet;

import db.DBUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created on 7/15/2017.
 */
@WebServlet(urlPatterns = { "/add_company"})
public class AddCompanyServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String companyName = request.getParameter("companyName");
            String parentName = request.getParameter("parentName");
            int earnings = Integer.parseInt(request.getParameter("earnings"));
            DBUtils.addCompany(companyName, parentName, earnings);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
