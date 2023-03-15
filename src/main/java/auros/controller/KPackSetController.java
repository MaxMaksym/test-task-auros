package auros.controller;

import auros.dto.request.KPackSetRequestDto;
import auros.dto.responce.KPackSetResponseDto;
import auros.mapper.impl.KPackSetMapper;
import auros.model.KPackSet;
import auros.service.KPackSetService;
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
@RequestMapping("/sets")
public class KPackSetController {
    private final KPackSetService kpackSetService;
    private final KPackSetMapper mapper;

    public KPackSetController(KPackSetService kpackSetService, KPackSetMapper mapper) {
        this.kpackSetService = kpackSetService;
        this.mapper = mapper;
    }

    /**
     * Adds a KPackSet using the provided request data and returns the response data.
     *
     * @param requestDto the request data for adding a KPackSet
     * @return the added KPackSet data, returned as a KPackSetResponseDto object
     */
    @PostMapping
    public KPackSetResponseDto add(@RequestBody KPackSetRequestDto requestDto) {
        KPackSetResponseDto kpackSetResponseDto =
                mapper.mapToDto(kpackSetService.add(mapper.mapToModel(requestDto)));
        kpackSetService.addKPacksToSet(kpackSetResponseDto.getId(),
                requestDto.getKpackIds());
        return kpackSetResponseDto;

    }

    /**
     * Deletes a KPackSet with the given ID.
     *
     * @param id the ID of the KPackSet to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        kpackSetService.delete(id);
    }

    /**
     * Retrieves a list of KPackSetResponseDto objects filtered and
     *              sorted according to the provided parameters.
     *
     * @param sort  the sorting parameter to use (default: "id").
     *              You can add sorting order by appending `:asc`(used by default)
     *              or `:desc` at the end of the sorting parameter.
     *              For example, `title:desc` would sort the results by title in descending order.
     *
     * @param id    the ID to filter by (optional)
     *
     * @param title the title to filter by (optional)
     *
     * @return a list of KPackSetResponseDto objects that meet
     *              the specified filtering and sorting criteria
     */
    @GetMapping
    public List<KPackSetResponseDto> getWithFilteringAndSorting(
            @RequestParam(defaultValue = "id") String sort,
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String title) {
        List<KPackSet> kpackSets =
                kpackSetService.getAllWithFilteringAndSorting(sort, id, title);
        return kpackSets.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }
}
