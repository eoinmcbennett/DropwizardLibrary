package org.kainos.ea.api;

import org.kainos.ea.cli.Member;
import org.kainos.ea.client.*;
import org.kainos.ea.db.IDAO;
import org.kainos.ea.db.MemberDao;

import java.util.List;

public class MemberService {
    private final IDAO<Member> memberDao = new MemberDao();


    public List<Member> getAllMembers() throws FailedToFetchException {
        return memberDao.getAll();
    }

    public Member getMemberById(int id) throws FailedToFetchException, NotFoundException {
        Member book = memberDao.getById(id);

        if(book == null){
            throw new NotFoundException("Member with id " + id + " not found");
        }

        return book;
    }

    public int createMember(Member member) throws FailedToCreateException {
        if(member.getMemberId() > 0){
            throw new FailedToCreateException("Attempted to recreate book with established id: " + member.getMemberId());
        }
        return memberDao.create(member);
    }

    public void updateMember(int id,Member member) throws FailedToUpdateException, NotFoundException {
        try {
            if(memberDao.getById(id) == null){
                throw new NotFoundException("Member with id " + id + " was not found");
            }
        } catch (FailedToFetchException e){
            throw new FailedToUpdateException(e.getMessage());
        }
        memberDao.update(id,member);
    }

    public void deleteMember(int id) throws FailedToDeleteException, NotFoundException {
        try {
            if(memberDao.getById(id) == null) {
                throw new NotFoundException("Member with id " + id + " not found");
            }
            memberDao.delete(id);
        } catch (FailedToFetchException e) {
            throw new FailedToDeleteException(e.getMessage());
        }
    }
}
