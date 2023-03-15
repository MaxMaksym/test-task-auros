package auros.mapper.impl;

import auros.dto.request.KPackRequestDto;
import auros.dto.responce.KPackResponseDto;
import auros.mapper.RequestDtoMapper;
import auros.mapper.ResponseDtoMapper;
import auros.model.KPack;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class KPackMapper implements RequestDtoMapper<KPackRequestDto, KPack>,
        ResponseDtoMapper<KPackResponseDto, KPack> {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public KPack mapToModel(KPackRequestDto dto) {
        KPack kpack = new KPack();
        kpack.setTitle(dto.getTitle());
        kpack.setDescription(dto.getDescription());
        kpack.setCreationDate(LocalDate.now().format(DATE_FORMATTER));
        return kpack;
    }

    @Override
    public KPackResponseDto mapToDto(KPack kpack) {
        KPackResponseDto kpackResponseDto = new KPackResponseDto();
        kpackResponseDto.setId(kpack.getId());
        kpackResponseDto.setTitle(kpack.getTitle());
        kpackResponseDto.setDescription(kpack.getDescription());
        kpackResponseDto.setCreationDate(kpack.getCreationDate());
        return kpackResponseDto;
    }
}
