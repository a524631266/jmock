package com.zhangll.jmock.core.error;

import com.zhangll.flink.model.FieldToken;
import com.zhangll.jmock.core.model.FieldToken;

public class FieldIllegalArgumentException extends RuntimeException {

    public FieldIllegalArgumentException(FieldToken currentTokenInfo) {
        super(currentTokenInfo.toString());
    }

}
