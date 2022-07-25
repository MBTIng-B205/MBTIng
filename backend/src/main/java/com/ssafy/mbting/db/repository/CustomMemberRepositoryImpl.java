package com.ssafy.mbting.db.repository;

import com.ssafy.mbting.db.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

public class CustomMemberRepositoryImpl implements CustomMemberRepository{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Member findByEmail(String email) {
        try {
            return (Member) em.createQuery("select m from Member m where m.email = :email")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
