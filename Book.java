public class Book {
    private String title;
    private String ISBN;
    private boolean available;
    private MyQueue<String> reservedQueue = new MyQueue<String>();

    public boolean isAvailable() {
        return available;
    }

    public MyQueue<String> getReservedQueue() {
        return reservedQueue;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }


}
