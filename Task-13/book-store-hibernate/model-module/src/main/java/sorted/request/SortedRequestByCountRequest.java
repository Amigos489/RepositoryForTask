package sorted.request;

import model.Request;

import java.util.Comparator;

public class SortedRequestByCountRequest implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        if (request1.getCountRequest() > request2.getCountRequest()) {
            return 1;
        } else if (request1.getCountRequest() < request2.getCountRequest()) {
            return -1;
        }
        return 0;
    }
}
