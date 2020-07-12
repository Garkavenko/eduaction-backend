package ru.vsueducation.db.dao;

import ru.vsueducation.db.models.*;
import ru.vsueducation.db.utils.SessionProvider;

import java.util.List;

public class CourseWorkTemplateDao {
    public List<CourseWorkTemplate> getAllCourseWorks() {
        return SessionProvider.runSession(session -> session.createQuery("from CourseWorkTemplate").list());
    }

    public CourseWorkTemplate getCourseWorkTemplateById(final int id) {
        return SessionProvider.getEntryById("CourseWorkTemplate", id);
    }

    public void createCourseWork(
        int discipline_id,
        int season_id,
        int discipline_profile_id,
        int year_number,
        int user_id
    ) {
        SessionProvider.runTransaction(session -> {
            final CourseWorkTemplate template = new CourseWorkTemplate();
            final EducationTablesDao educationTablesDao = new EducationTablesDao();
            final Discipline discipline = educationTablesDao.getDisciplineById(discipline_id);
            final DisciplineProfile disciplineProfile = educationTablesDao.getDisciplineProfileById(discipline_profile_id);
            if (disciplineProfile.getDiscipline_id() != discipline.getId()) {
                throw new RuntimeException("discipline profile should be from discipline");
            }
            template.setDiscipline(discipline);
            template.setDisciplineProfile(new DisciplineProfile() {{ setId(discipline_id); }});
            template.setSeason(new Season() {{ setId(season_id); }});
            template.setYear_number(year_number);
            template.setUser(new User() {{ setId(user_id); }});
            session.save(template);
            return null;
        });
    }
}
