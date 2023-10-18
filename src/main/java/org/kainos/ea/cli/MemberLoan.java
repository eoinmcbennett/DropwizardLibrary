package org.kainos.ea.cli;

public class MemberLoan {
    private Member member;
    private Loan loan;
    private Book book;

    public MemberLoan(Member member, Loan loan, Book book){
        this.member = member;
        this.loan = loan;
        this.book = book;
    }

    public Member getMember(){
        return this.member;
    }

    public Loan getLoan() {
        return this.loan;
    }

    public Book getBook() {
        return this.book;
    }
}
