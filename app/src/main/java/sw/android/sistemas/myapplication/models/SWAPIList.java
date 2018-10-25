package sw.android.sistemas.myapplication.models;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class SWAPIList<T> implements Serializable {

    public int count;
    public String next;
    public String previous;
    public ArrayList<T> results;

    public SWAPIList(int count, String next,
                     String previous, ArrayList<T> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<T> getResults() {
        return results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }

    public boolean hasMore() {
        return !TextUtils.isEmpty(next);
    }

}
