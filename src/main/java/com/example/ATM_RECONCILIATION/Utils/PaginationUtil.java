package com.example.ATM_RECONCILIATION.Utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PaginationUtil {
    private PaginationUtil(){}
    public static <T> Page<T> paginateList(final Pageable pageable, List<T> list) {
        int first = Math.min((int)pageable.getOffset(), list.size());;
        int last = Math.min(first + pageable.getPageSize(), list.size());
        return new PageImpl<>(list.subList(first, last), pageable, list.size());
    }
}
