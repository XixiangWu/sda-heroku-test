package database;

import java.util.HashMap;
import java.util.Map;

public class Registry {

    private static Map<Integer, BookGateway> bookGateway = new HashMap<>();

    public static BookGateway getBook(int id) {
        return bookGateway.get(id);
    }

    public static void addBook(BookGateway bookGateway) {
        Registry.bookGateway.put(bookGateway.getIsbn(), bookGateway);
    }

}
