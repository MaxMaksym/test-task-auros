package auros.dto.responce;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class KPackResponseDto {
    private Long id;
    private String title;
    private String description;
    private String creationDate;
}
