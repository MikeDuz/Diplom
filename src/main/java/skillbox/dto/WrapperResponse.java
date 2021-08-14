package skillbox.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrapperResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    boolean result;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object errors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    int id;

}
