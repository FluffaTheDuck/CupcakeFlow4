package dat.startcode.control;

import dat.startcode.model.DTO.DTOShoppingCart;
import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.CupcakeMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "MakeOrderServlet", value = "/MakeOrderServlet")
public class MakeOrderServlet extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        DTOShoppingCart cart = (DTOShoppingCart) session.getAttribute("cart");
        CupcakeMapper cupcakeMapper = new CupcakeMapper(connectionPool);

        String msg = "";

        try{
            int orderID = cupcakeMapper.makeOrder(cart, user);
            request.setAttribute("cart", cart);
            request.setAttribute("orderID", orderID);
            session.setAttribute("cart", new DTOShoppingCart());
            request.getRequestDispatcher("confirm.jsp").forward(request,response);
        }catch (DatabaseException e){
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
