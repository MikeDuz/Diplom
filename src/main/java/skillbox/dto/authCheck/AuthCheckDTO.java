package skillbox.dto.authCheck;

import lombok.Value;

import javax.validation.constraints.Positive;

public enum AuthCheckDTO {
    ;

    private interface Result {
        @Positive Boolean getResult();
    }

    private interface User {
        @Positive skillbox.dto.authCheck.User getUser();
    }


public enum Response {
    ;

    @Value public static class Create implements Result, User {
       Boolean result;
       skillbox.dto.authCheck.User user;
    }
}

}
