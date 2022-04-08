package dat.startcode.control;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DashServlet", value = "/DashServlet")
public class DashServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //HttpSession session = request.getSession();
        String page = request.getParameter("Dashpage");

        System.out.println(page);
        request.setAttribute("Dashpage", page);
        request.getRequestDispatcher("Dashboard.jsp").forward(request,response);

    }
}