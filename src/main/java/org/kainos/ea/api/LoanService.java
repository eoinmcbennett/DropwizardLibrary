package org.kainos.ea.api;

import org.checkerframework.checker.units.qual.A;
import org.kainos.ea.cli.Book;
import org.kainos.ea.cli.Loan;
import org.kainos.ea.cli.Member;
import org.kainos.ea.cli.MemberLoan;
import org.kainos.ea.client.FailedToCreateException;
import org.kainos.ea.client.FailedToFetchException;
import org.kainos.ea.client.NotFoundException;
import org.kainos.ea.db.BookDao;
import org.kainos.ea.db.IDAO;
import org.kainos.ea.db.LoanDao;
import org.kainos.ea.db.MemberDao;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LoanService {
    IDAO<Loan> loanDAO = new LoanDao();
    IDAO<Book> bookDAO = new BookDao();
    IDAO<Member> memberDAO = new MemberDao();

    public List<MemberLoan> getAllLoans() throws FailedToFetchException {
        try {
            List<Loan> loans = loanDAO.getAll();

            System.out.println(loans);

            List<MemberLoan> memberLoans = new ArrayList<>();

            for(Loan loan: loans){
                try {
                    Member member = memberDAO.getById(loan.getMemberId());
                    Book book = bookDAO.getById(loan.getBookId());
                    memberLoans.add(new MemberLoan(member,loan,book));
                } catch(NotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }

            return memberLoans;
        } catch (FailedToFetchException e) {
            System.err.println(e.getMessage());
            throw new FailedToFetchException(e.getMessage());
        }
    }

    public List<MemberLoan> getAllLoansForMemberId(int id) {
        try {
            List<Loan> loans = loanDAO.getAll();

            List<Loan> memberLoans = loans.stream().filter(loan -> loan.getMemberId() == id).collect(Collectors.toList());

            List<MemberLoan> memberLoanDetails = new ArrayList<>();

            for(Loan loan: memberLoans){
                try {
                    Member member = memberDAO.getById(loan.getMemberId());
                    Book book = bookDAO.getById(loan.getBookId());
                    MemberLoan memberLoan = new MemberLoan(member,loan,book);
                    memberLoanDetails.add(memberLoan);
                } catch (NotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }


            return memberLoanDetails;

        } catch (FailedToFetchException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String,Integer> getPublisherLoans() throws FailedToFetchException {
        try{
            Map<String,Integer> publisherLoans = new HashMap<>();
            List<Loan> allLoans = loanDAO.getAll();

            for(Loan loan: allLoans){
                try {
                    Book book = bookDAO.getById(loan.getBookId());
                    publisherLoans.put(book.getPublisher(),publisherLoans.getOrDefault(book.getPublisher(),0) + 1);
                } catch (FailedToFetchException e) {
                    throw new FailedToFetchException(e.getMessage());
                } catch (NotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }

            return publisherLoans;
        } catch (FailedToFetchException e){
            throw new FailedToFetchException(e.getMessage());
        }
    }

    public Map<String,Integer> getAuthorLoans() throws FailedToFetchException {
        try{
            Map<String,Integer> authorLoans = new HashMap<>();
            List<Loan> allLoans = loanDAO.getAll();

            for(Loan loan: allLoans){
                try {
                    Book book = bookDAO.getById(loan.getBookId());
                    authorLoans.put(book.getAuthor(),authorLoans.getOrDefault(book.getAuthor(),0) + 1);
                } catch (FailedToFetchException e) {
                    throw new FailedToFetchException(e.getMessage());
                } catch (NotFoundException e) {
                    System.err.println(e.getMessage());
                }
            }

            return authorLoans;
        } catch (FailedToFetchException e){
            throw new FailedToFetchException(e.getMessage());
        }
    }

    public double getAveragePriceOfBooksLoanedPublishedLastYear(){
        try{
            List<MemberLoan> allLoans = this.getAllLoans();
            final long YEAR_IN_MILLIS = Instant.now().minusMillis(60 * 60 * 24 * 365 * 1000).toEpochMilli();
            List<MemberLoan> loansWithBooksPublishedLastYear = allLoans.stream().filter(memberLoan -> memberLoan.getBook().getPublicationYear().after(new Date(YEAR_IN_MILLIS))).collect(Collectors.toList());
            System.out.println(loansWithBooksPublishedLastYear);
            double averagePrice = 0.0;

            if(!loansWithBooksPublishedLastYear.isEmpty()){
                for(MemberLoan loan: loansWithBooksPublishedLastYear){
                    averagePrice += loan.getBook().getPrice();
                    System.out.println(averagePrice);
                }

                averagePrice /= loansWithBooksPublishedLastYear.size();
            }

            return averagePrice;
        } catch (FailedToFetchException e) {
            throw new RuntimeException(e);
        }
    }

    public int createLoan(Loan loan) throws FailedToCreateException {
        try {
            if(loan.getLoanId() > 0){
                throw new FailedToCreateException("Attempted to create loan with allready exsiting id");
            }
            return loanDAO.create(loan);
        } catch (FailedToCreateException e) {
            throw new FailedToCreateException(e.getMessage());
        }
    }
}
