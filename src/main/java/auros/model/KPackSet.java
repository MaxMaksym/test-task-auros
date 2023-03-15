package auros.model;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KPackSet {
    private Long id;
    private String title;
    private Set<KPack> kpacks;
}
