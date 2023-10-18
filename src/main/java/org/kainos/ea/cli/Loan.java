package org.kainos.ea.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;

public class Loan {
    @JsonIgnore
    private int loanId;
    private int memberId;
    private int bookId;
    private Date loanDate;
    private Date returnDate;

    @JsonCreator
    public Loan(
            @JsonProperty("memberId") int memberId,
            @JsonProperty("bookId") int bookId,
            @JsonProperty("loanDate") Date loanDate,
            @JsonProperty("returnDate") Date returnDate) {
        this.loanId = -1;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public Loan(int loanId, int memberId, int bookId, Date loanDate, Date returnDate) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    @JsonProperty
    public int getLoanId() {
        return loanId;
    }

    @JsonProperty
    public int getMemberId() {
        return memberId;
    }

    @JsonProperty
    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @JsonProperty
    public int getBookId() {
        return bookId;
    }

    @JsonProperty
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @JsonProperty
    public Date getLoanDate() {
        return loanDate;
    }

    @JsonProperty
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    @JsonProperty
    public Date getReturnDate() {
        return returnDate;
    }

    @JsonProperty
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
