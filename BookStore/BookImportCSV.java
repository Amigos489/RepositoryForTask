import java.time.LocalDate;

public class BookImportCSV extends ImportCSV<Book> {

    @Override
    public Book conversionInEssence(String line) {

        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            int id = Integer.parseInt(parts[0]);
            String nameBook = parts[1].replace("\"", "");
            String authorBook = parts[2].replace("\"", "");
            LocalDate dateOfPublication = LocalDate.parse(parts[3]);
            LocalDate dateAddedToWarehouse = LocalDate.parse(parts[4]);
            int numberOfCopies = Integer.parseInt(parts[5]);
            int numberPages = Integer.parseInt(parts[6]);
            int price = Integer.parseInt(parts[7]);
            int numberOfRequests = Integer.parseInt(parts[8]);
            boolean availability = Boolean.parseBoolean(parts[9]);

            Book book = new Book(nameBook, authorBook, dateOfPublication,
                    numberOfCopies, numberPages, price, dateAddedToWarehouse);
            book.setBookID(id);
            book.setAvailability(availability);
            for (int i = 0; i < numberOfRequests; i++) {
                book.incrementRequests();
            }
            return book;
    }

    
}
