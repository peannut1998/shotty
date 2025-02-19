package com.shotty.shotty.global.auth.exception.handler;

import com.shotty.shotty.global.auth.exception.custom_exception.InvalidRefreshTokenException;
import com.shotty.shotty.global.common.dto.ResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class JwtExceptionHandler {
    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<ResponseDto<Void>> handleExpiredJwtException (){
        ResponseDto<Void> responseDto = new ResponseDto<>(4002, "만료된 토큰", null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = InvalidRefreshTokenException.class)
    public ResponseEntity<ResponseDto<Void>> handleInvalidRefreshTokenException (InvalidRefreshTokenException e){
        ResponseDto<Void> responseDto = new ResponseDto<>(4003, e.getMessage(), null);
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}


