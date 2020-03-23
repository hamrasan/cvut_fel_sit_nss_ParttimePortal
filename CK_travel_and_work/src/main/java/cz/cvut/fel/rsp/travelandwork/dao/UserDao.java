package cz.cvut.fel.rsp.travelandwork.dao;

import cz.cvut.fel.rsp.travelandwork.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class UserDao extends BaseDao<User> {
    public UserDao(){super(User.class);}

    public User findByUsername(String username){
        {
            try {
                return em.createNamedQuery("User.findByUsername", User.class).setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }
    }

    public User find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(User.class, id);
    }

    public User find(String email) {
        Objects.requireNonNull(email);
        return em.find(User.class, email);
    }

    public User findByEmail(String email) {
        try {
            return em.createNamedQuery("User.findByEmail", User.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }




}