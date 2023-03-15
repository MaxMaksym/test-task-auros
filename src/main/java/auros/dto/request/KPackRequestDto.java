package auros.dto.request;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KPackRequestDto {
    @Size(max = 250)
    private String title;
    @Size(max = 2000)
    private String description;
}
