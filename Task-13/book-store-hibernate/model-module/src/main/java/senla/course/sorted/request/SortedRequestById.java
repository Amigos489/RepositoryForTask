package senla.course.sorted.request;

import senla.course.model.Request;

import java.util.Comparator;

public class SortedRequestById implements Comparator<Request> {
    @Override
    public int compare(Request request1, Request request2) {
        return Integer.compare(request1.getId(), request2.getId());
    }
}