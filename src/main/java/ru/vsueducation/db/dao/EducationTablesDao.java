package ru.vsueducation.db.dao;

import org.hibernate.query.Query;
import ru.vsueducation.db.models.Discipline;
import ru.vsueducation.db.models.DisciplineProfile;
import ru.vsueducation.db.utils.SessionProvider;

import java.util.List;

public class EducationTablesDao {
    public List<Discipline> getAllDisciplines() {
        return SessionProvider.runSession(session -> session.createQuery("from Discipline").list());
    }

    public Discipline getDisciplineById(final int id) {
        return SessionProvider.runSession(session -> {
            final Query<Discipline> query = session.createQuery("from Discipline where id = :i");
            query.setParameter("i", id);
            return query.getSingleResult();
        });
    }

    public DisciplineProfile getDisciplineProfileById(final int id) {
        return SessionProvider.runSession(session -> {
            final Query<DisciplineProfile> query = session.createQuery("from DisciplineProfile where id = :i");
            query.setParameter("i", id);
            return query.getSingleResult();
        });
    }
}
