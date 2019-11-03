package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookGateway {

    private int isbn;
    private String title;

    public BookGateway(int isbn, String title) {
//        super();
        this.isbn = isbn;
        this.title = title;
    }

    public int getIsbn() {return isbn;}
    public String getTitle() {return title;}

    public static BookGateway load(ResultSet rs) throws SQLException {
        int isbn = rs.getInt(1);
        BookGateway result = database.Registry.getBook(isbn);
        if (result != null)
            return result;
        String titleArg = rs.getString(2);
        result = new BookGateway(isbn, titleArg);
        database.Registry.addBook(result);
        return result;
    }



}
