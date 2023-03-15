package auros.dto.request;

import java.util.List;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KPackSetRequestDto {
    @Size(max = 250)
    private String title;
    private List<Long> kpackIds;
}
