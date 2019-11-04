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

public class EditIssue extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: Process issue edits
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/login");
            return;
        } else {
            user = User.getUser((Integer) session.getAttribute("userId"));
            if (user == null) {
                response.sendRedirect("/login");
                return;
            }
        }
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">");
        out.println("<title>Edit Isuue</title>");
        out.println("</head>");
        out.println("<body>");
        // TODO: Show issue details edit form for Employee only if Employee is issue reporter
        // TODO: Show issue comment add/edit form if Tech
        // TODO: Show change issue status/stickied form if Tech

        out.println("<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js\" integrity=\"sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM\" crossorigin=\"anonymous\"></script>");
        out.println("</body>");
        out.println("</html>");

        out.close();
    }
}
