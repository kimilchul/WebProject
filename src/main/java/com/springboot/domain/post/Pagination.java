package com.springboot.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Pagination {

    private int pageSize = 10;

    private int blockSize = 10;

    private int startBlock;

    private int endBlock;

    private int currentPage;

    private int startPage = 1;

    private int endPage;

    private int startIndex = 0;

    private int previousPage;

    private int nextPage;


    @Builder
    public Pagination(int endPage,int currentPage){
        if(previousPage>1){
            this.previousPage=currentPage-1;
        }else{
            this.previousPage=1;
        }

        if(nextPage>endPage){
            this.nextPage=endPage;
        }else{
            this.nextPage=currentPage+1;
        }

        this.startBlock = (((currentPage-1)/10)*10)+1;

        if((((currentPage-1)/10)*10)+10>endPage){
            this.endBlock = endPage;
        }else{
            this.endBlock = (((currentPage-1)/10)*10)+10;
        }

        this.endPage = endPage;
        this.currentPage = currentPage;
    }
}
