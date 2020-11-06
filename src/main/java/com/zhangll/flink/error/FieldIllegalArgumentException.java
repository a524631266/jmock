package com.zhangll.flink.error;

import com.zhangll.flink.model.FieldToken;

public class FieldIllegalArgumentException extends RuntimeException {

    public FieldIllegalArgumentException(FieldToken currentTokenInfo) {
        super(currentTokenInfo.toString());
    }

}
