package sorted.request;

import model.Request;

import java.util.Comparator;

public class SortedRequestByNameBook implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return request1.getNameBook().compareTo(request2.getNameBook());
    }
}
