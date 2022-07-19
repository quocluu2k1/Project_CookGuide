package com.example.cookguide.models;

import java.sql.Date;
import java.util.List;

public class HistoryComment {
    private Date date;
    private List<HistoryCommentDetail> list;

    public HistoryComment(){}

    public HistoryComment(Date date, List<HistoryCommentDetail> list) {
        this.date = date;
        this.list = list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<HistoryCommentDetail> getList() {
        return list;
    }

    public void setList(List<HistoryCommentDetail> list) {
        this.list = list;
    }
}
