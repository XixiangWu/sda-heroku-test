package bookshop;

import database.BookGateway;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class browseAllBooks extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public browseAllBooks() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(req, response);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        List<BookGateway> books = BookFinder.findAvailableBooks();

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>View Books</title>");
        out.println("<link rel='stylesheet' href='resources/bootstrap.min.css'/>");
        out.println("<link rel='stylesheet' href='style.css'/>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");

        out.print("<h1>View Books</h1>");

        out.println("<table class='table table-bordered table-striped'>");
        out.print("<tr><th>ISBN</th><th>Title</th>");

        if (books == null) {
            System.out.println("null");
        }

        for (BookGateway book : books) {
            out.println( "<form action=\"cart\" method=\"post\">");
            out.print( "<tr><td>" + book.getIsbn() + "</td><td>" + book.getTitle() + "</td>"
                 +"</tr>");
            out.println("</form>");
        }
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
