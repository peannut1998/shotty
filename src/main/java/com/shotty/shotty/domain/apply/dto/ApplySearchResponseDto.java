package com.shotty.shotty.domain.apply.dto;

import com.shotty.shotty.domain.apply.domain.Apply;
import com.shotty.shotty.domain.bid.domain.Bid;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplySearchResponseDto {
    @Schema(description = "지원 식별 Id",example = "1L")
    private Long apply_id;
    @Schema(description = "지원 폼 제목",example = "구름빵 홍보 영상 지원합니다")
    private String title;

    @Schema(description = "공고 식별 Id",example = "1L")
    private Long post_id;

    @Schema(description = "등록한 공고의 title",example = "구름빵 홍보 영상")
    private String postTitle;

    @Schema(description = "지원의 입찰 여부",example = "true")
    private Boolean bidded;

    @Schema(description = "업로드된 쇼츠 영상 id", example = "CdHc0rl3IM4")
    private String shortsId;

    @Schema(description = "최종 승인 여부", example = "false")
    private Boolean accepted;

    public static ApplySearchResponseDto from(Apply apply) {
        Bid bid = apply.getBid();

        Boolean bidded = false;
        String shortsId = null;
        Boolean accepted = false;

        if (bid != null) {
            bidded = true;
            shortsId = bid.getShortsId();
            accepted = bid.getAccepted();
        }
        return new ApplySearchResponseDto(
                apply.getId(),
                apply.getTitle(),
                apply.getPost().getId(),
                apply.getPost().getTitle(),
                bidded,
                shortsId,
                accepted
        );
    }
}
