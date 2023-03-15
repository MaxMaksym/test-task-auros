package auros.controller;

import auros.dto.request.KPackRequestDto;
import auros.dto.responce.KPackResponseDto;
import auros.mapper.impl.KPackMapper;
import auros.model.KPack;
import auros.service.KPackService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kpacs")
public class KPackController {
    private final KPackService kpackService;
    private final KPackMapper mapper;

    public KPackController(KPackService kpackService, KPackMapper mapper) {
        this.kpackService = kpackService;
        this.mapper = mapper;
    }

    /**
     * Adds a new KPack with the data provided in the request body,
     *                    and returns the added KPack data as a response.
     *
     * @param requestDto the data of the KPack to add, provided in the
     *                    request body as a KPackRequestDto object
     *
     * @return the added KPack data, returned as a KPackResponseDto object
     */
    @PostMapping
    public KPackResponseDto add(@RequestBody KPackRequestDto requestDto) {
        return mapper.mapToDto(kpackService.add(mapper.mapToModel(requestDto)));
    }

    /**
     * Deletes a KPack with the given ID.
     *
     * @param id the ID of the KPack to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        kpackService.delete(id);
    }

    /**
     * Retrieves a list of KPackResponseDto objects filtered and
     *                    sorted according to the provided parameters.
     *
     * @param sort        the sorting parameter to use (default: "id").
     *                    You can add sorting order by appending `:asc`(used by default) or
     *                    `:desc` at the end of the sorting parameter.
     *                    For example, `title:desc` would sort the results by
     *                    title in descending order.
     *
     * @param id          the ID to filter by (optional)
     *
     * @param title       the title to filter by (optional)
     *
     * @param description the description to filter by (optional)
     *
     * @param date        the date to filter by (optional)
     *
     * @return a list of KPackResponseDto objects that meet the
     *                    specified filtering and sorting criteria
     */
    @GetMapping
    public List<KPackResponseDto> getAllWithFilteringAndSorting(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) String date) {
        List<KPack> all = kpackService.getAllWithFilteringAndSorting(
                sort, id, title, description, date);
        return all.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
