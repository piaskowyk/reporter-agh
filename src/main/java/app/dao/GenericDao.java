package app.dao;

import javax.persistence.PersistenceException;

import app.session.SessionService;
import org.hibernate.Session;
import org.hibernate.Transaction;


public abstract class GenericDao<T> {

    protected void save(final T object) throws PersistenceException {
        final Session session = SessionService.getSession();
        final Transaction tx = session.beginTransaction();
        session.save(object);
        session.merge(object);
        tx.commit();
    }

    protected void update(final T object) throws PersistenceException {
        final Session session = SessionService.getSession();
        final Transaction tx = session.beginTransaction();
        session.update(object);
        session.merge(object);
        tx.commit();
    }

    protected void remove(final T object) throws PersistenceException{
        final Session session = SessionService.getSession();
        final Transaction tx = session.beginTransaction();
        session.remove(object);
        tx.commit();
    }

    public Session currentSession() {
        return SessionService.getSession();
    }
}
