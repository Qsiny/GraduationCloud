package com.qsiny.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageEntity {

    private Integer page = 1;
    private Integer size = 10;

    public PageEntity(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}
