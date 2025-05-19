package com.jhj.annual.user.results;

import lombok.*;
import org.springframework.web.bind.annotation.SessionAttribute;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultTuple<T> {
    private Result result;
    private T payload;
}
