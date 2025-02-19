package com.shotty.shotty.domain.account.api;

import com.shotty.shotty.domain.account.application.AccountService;
import com.shotty.shotty.domain.account.domain.Account;
import com.shotty.shotty.domain.account.dto.AccountCreateReqDto;
import com.shotty.shotty.domain.account.dto.AccountResDto;
import com.shotty.shotty.domain.account.dto.AccountUpdateReqDto;
import com.shotty.shotty.global.common.custom_annotation.annotation.TokenId;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "출금 계좌 관련 API")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "출금 계좌 등록")
    @PostMapping("/api/accounts")
    public ResponseEntity<ResponseDto<AccountResDto>> createAccount(@Parameter(hidden = true) @TokenId Long userId, @RequestBody AccountCreateReqDto accountCreateReqDto) {
        AccountResDto accountResDto = accountService.createAccount(accountCreateReqDto, userId);

        ResponseDto<AccountResDto> responseDto = new ResponseDto<>(
            2001,
                "계좌 등록 성공",
                accountResDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "내 출금 계좌 조회")
    @GetMapping("/api/accounts")
    public ResponseEntity<ResponseDto<AccountResDto>> getAccounts(@Parameter(hidden = true) @TokenId Long userId) {
        AccountResDto accountResDto = accountService.getAccountOf(userId);

        ResponseDto<AccountResDto> responseDto = new ResponseDto<>(
                2002,
                "출금 계좌 정보 조회 완료",
                accountResDto
        );

        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "계좌 정보 변경")
    @PutMapping("/api/accounts")
    public ResponseEntity<ResponseDto<AccountResDto>> updateAccount(@TokenId Long userId, @RequestBody AccountUpdateReqDto accountUpdateReqDto) {
        AccountResDto accountResDto = accountService.updateAccount(accountUpdateReqDto, userId);

        ResponseDto<AccountResDto> responseDto = new ResponseDto<>(
                2007,
                "출금 계좌 정보 수정 완료",
                accountResDto
        );

        return ResponseEntity.ok(responseDto);
    }
}
