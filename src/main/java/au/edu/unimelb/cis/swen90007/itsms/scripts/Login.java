package au.edu.unimelb.cis.swen90007.itsms.scripts;

import au.edu.unimelb.cis.swen90007.itsms.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = User.getUserByUsername(username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                HttpSession session = request.getSession();
                session.setAttribute("userId", user.getId());
                response.sendRedirect("/view");
                return;
            }
        }
        doGet(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css' integrity='sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T' crossorigin='anonymous'>");
        out.println("<title>Login</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8'>");
        out.println("<div class='card'>");
        out.println("<div class='card-header'>Login</div>");
        out.println("<div class='card-body'>");
        out.println("<form action='' method='post'>");
        out.println("<div class='form-group row'>");
        out.println("<label for='username' class='col-md-4 col-form-label text-md-right'>Username</label>");
        out.println("<div class='col-md-6'>");
        out.println("<input type='text' id='username' class='form-control' name='username' required autofocus>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='form-group row'>");
        out.println("<label for='password' class='col-md-4 col-form-label text-md-right'>Password</label>");
        out.println("<div class='col-md-6'>");
        out.println("<input type='password' id='password' class='form-control' name='password' required>");
        out.println("</div>");
        out.println("</div>");
        out.println("<div class='col-md-6 offset-md-4'>");
        out.println("<button type='submit' class='btn btn-primary'>Login</button>");
        out.println("</div>");
        out.println("</div>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");
        out.println("</div>");

        out.println("<script src='https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js' integrity='sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM' crossorigin='anonymous'></script>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
