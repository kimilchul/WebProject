package com.springboot.util;

import com.springboot.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class PageVO {
    private int total;
    private int currentPage;
    private int totalPages;
    private int startPage;
    private int endPage;
    private int pagingCount;
    private List<Post> content;

    @Builder
    public PageVO(int total, int currentPage, int size, int pagingCount, List<Post> content) {
        this.total = total;
        this.currentPage = currentPage;
        this.content = content;
        this.pagingCount = pagingCount;

        if(total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if(total % size > 0) {
                totalPages++;
            }

            startPage = currentPage / pagingCount * pagingCount + 1;
            if(currentPage % pagingCount == 0) {
                startPage -= pagingCount;
            }

            endPage = startPage + pagingCount - 1 ;
            if(endPage > totalPages) {
                endPage = totalPages;
            }
        }
    }

    public boolean hasNoArticles() {
        return this.total == 0;
    }

    public boolean hasArticles() {
        return this.total > 0;
    }
}