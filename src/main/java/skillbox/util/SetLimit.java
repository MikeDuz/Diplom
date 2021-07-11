package skillbox.util;

public class SetLimit {

    public static int setLimit(int offset, int limit, int count) {
        if((count - offset ) <= limit) {
            return count - offset;
        }
        return limit;
    }
}
