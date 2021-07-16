package skillbox.util;

public class SetPageNumber {

    public static int setPage(int offset, int limit) {
        if(limit != 0) {
            return offset / limit;
        }
        return 0;
    }
}
