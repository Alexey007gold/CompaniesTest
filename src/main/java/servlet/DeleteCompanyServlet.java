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
@WebServlet(urlPatterns = { "/delete_company"})
public class DeleteCompanyServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String companyName = request.getParameter("companyName");
            DBUtils.deleteCompany(companyName);
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
