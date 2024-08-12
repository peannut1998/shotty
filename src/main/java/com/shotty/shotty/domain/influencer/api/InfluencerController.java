package com.shotty.shotty.domain.influencer.api;

import com.shotty.shotty.domain.influencer.application.InfluencerService;
import com.shotty.shotty.domain.influencer.domain.InfluencerPatch;
import com.shotty.shotty.domain.influencer.dto.*;
import com.shotty.shotty.domain.influencer.enums.Niche;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "인플루언서 관련 API")
public class InfluencerController {
    private final InfluencerService influencerService;

    @Operation(summary = "개별 조회", description = "패스 파라미터로 받은 id를 통해 개별 인플루언서 조회")
    @GetMapping("/influencers/{id}")
    public ResponseEntity<ResponseDto<ResponseInfluencerDto>> getInfluencer(@PathVariable("id") Long influencer_id) {
        ResponseInfluencerDto responseInfluencerDto = influencerService.findOne(influencer_id);

        ResponseDto<ResponseInfluencerDto> influence = new ResponseDto<>(
                2004,
                "인플루언서 개별 조회 성공",
                responseInfluencerDto
        );
        return ResponseEntity.ok(influence);
    }

    @GetMapping("/influencers")
    @Operation(summary = "전체 조회", description = "쿼리 파라미터로 받은 페이지네이션 정보로 인플루언서 전체 조회")
    public ResponseEntity<ResponseDto<Page<ResponseInfluencerDto>>> getAllInfluencers(
            @ParameterObject @PageableDefault(size = 10, sort = "subscribers", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false,defaultValue = "") String userName, @RequestParam(required = false) Niche niche) {
        log.info("userName:{},niche:{}", userName, niche);
        InfluencerSearchInfo influencerSearchInfo = new InfluencerSearchInfo(userName,niche);
        Page<ResponseInfluencerDto> influencers = influencerService.findAllInfluencers(pageable,influencerSearchInfo);

        ResponseDto<Page<ResponseInfluencerDto>> responseDto = new ResponseDto<>(
                2003,
                "인플루언서 전체 조회 성공",
                influencers
        );
        return ResponseEntity.ok(responseDto);
    }
    @PostMapping("/influencers")
    @Operation(summary = "인플루언서 등록", description = "등록 폼을 통해 인플루언서 등록")
    public ResponseEntity<ResponseDto<Null>> registerInfluencer(@Parameter(hidden = true) @TokenId Long user_id, @Valid @RequestBody RegisterInfluencerDto registerInfluencerDto) {
        SaveInfluencerDto saveInfluencerDto = SaveInfluencerDto.from(registerInfluencerDto);
        influencerService.register(user_id, saveInfluencerDto);
        ResponseDto<Null> responseDto = new ResponseDto<>(
                2011,
                "인플루언서 등록 성공",
                null
        );
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/influencers/{id}")
    @Operation(summary = "인플루언서 정보 수정",description = "수정 폼을 통해 인플루언서 수정")
    public ResponseEntity<ResponseDto<ResponseInfluencerDto>> updateInfluencer(
            @Parameter(hidden = true) @TokenId Long user_id, @PathVariable("id") Long influencer_id,@Valid @RequestBody InfluencerUpdateRequestDto influencerUpdateRequestDto) {
        InfluencerPatch influencerPatch = InfluencerPatch.from(influencerUpdateRequestDto);
        ResponseInfluencerDto responseInfluencerDto = influencerService.update(user_id,influencer_id, influencerPatch);
        ResponseDto<ResponseInfluencerDto> responseDto = new ResponseDto<>(
                2006,
                "인플루언서 정보 수정 성공",
                responseInfluencerDto
        );
        return ResponseEntity.ok(responseDto);
    }
}
