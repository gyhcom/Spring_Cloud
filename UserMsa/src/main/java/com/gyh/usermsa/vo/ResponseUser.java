package com.gyh.usermsa.vo;

import java.util.List;
import lombok.Data;
import lombok.Setter;

@Data
//Data가 아닌 setter,getter만 붙이면 에러남 나중에 이유를 찾아봐야함
public class ResponseUser {

    private String email;
    private String name;
    private String userId;

    private List<ResponseOrder> orders;
}
