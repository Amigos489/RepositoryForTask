public class BookRequestImportCSV extends ImportCSV<BookRequest>{

    @Override
    public BookRequest conversionInEssence(String line) {

        String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        int i = 0;
        int id = Integer.parseInt(parts[i++]);
        int bookId = Integer.parseInt(parts[i++]);
        String bookName = parts[i++].replace("\"", "");
        int requestCount = Integer.parseInt(parts[i++]);
        boolean fulfilled = Boolean.parseBoolean(parts[i]);

        BookRequest request = new BookRequest();
        request.setId(id);
        request.setBookId(bookId);
        request.setBookName(bookName);
        request.setRequestCount(requestCount);
        request.setFulfilled(fulfilled);

        // синхронизация счётчика ID
        BookRequest.syncCounter(id);

        return request;
    }
    
}
