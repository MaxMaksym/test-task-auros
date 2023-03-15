package auros.mapper.impl;

import auros.dto.request.KPackSetRequestDto;
import auros.dto.responce.KPackSetResponseDto;
import auros.mapper.RequestDtoMapper;
import auros.mapper.ResponseDtoMapper;
import auros.model.KPackSet;
import org.springframework.stereotype.Component;

@Component
public class KPackSetMapper implements RequestDtoMapper<KPackSetRequestDto, KPackSet>,
        ResponseDtoMapper<KPackSetResponseDto, KPackSet> {

    @Override
    public KPackSet mapToModel(KPackSetRequestDto dto) {
        KPackSet kpackSet = new KPackSet();
        kpackSet.setTitle(dto.getTitle());
        return kpackSet;
    }

    @Override
    public KPackSetResponseDto mapToDto(KPackSet kpackSet) {
        KPackSetResponseDto kpackResponseDto = new KPackSetResponseDto();
        kpackResponseDto.setId(kpackSet.getId());
        kpackResponseDto.setTitle(kpackSet.getTitle());
        return kpackResponseDto;
    }
}
