package com.joney.shop.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(Exception e){
        //컨트롤러 파일이 100만개 있는경우... 매번 쓰기 귀찮은데?
        //하나의 파일을 만들어서 모든 파일에 적용되도록 하자
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 아이템을 찾을 수 없습니다.");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handler1(){
        //컨트롤러 파일이 100만개 있는경우... 매번 쓰기 귀찮은데?
        //하나의 파일을 만들어서 모든 파일에 적용되도록 하자
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error");
    }

}
