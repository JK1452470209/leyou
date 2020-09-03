package com.leyou.common.advice;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @outhor Mr.JK
 * @create 2020-05-12  17:14
 * 通用异常控制器
 */
@ControllerAdvice//默认拦截全部Controller
public class CommonExceptionHandler {

    @ExceptionHandler(LyException.class) //拦截这种异常
    public ResponseEntity<ExceptionResult> handleException(LyException e){
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getCode()).body(new ExceptionResult(e.getExceptionEnum()));
    }

}
