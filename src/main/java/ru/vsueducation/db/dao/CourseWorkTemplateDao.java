package ru.vsueducation.db.dao;

import org.hibernate.query.Query;
import ru.vsueducation.db.models.*;
import ru.vsueducation.db.utils.QueryUtils;
import ru.vsueducation.db.utils.SessionProvider;

import java.util.List;

public class CourseWorkTemplateDao {
    public List<CourseWorkTemplate> getAllCourseWorks(
            final Integer disciplineId,
            final Integer profileId,
            final Integer seasonId,
            final Integer year
    ) {
        return SessionProvider.runSession(session -> {
            /*final StringBuilder queryString = new StringBuilder("from CourseWorkTemplate");
            if (disciplineId != null || profileId != null || seasonId != null || year != null) queryString.append(" where ");
            if (disciplineId != null) QueryUtils.addAndParameter(queryString, false, "discipline_id", "d");
            if (profileId != null) QueryUtils.addAndParameter(queryString, disciplineId != null, "discipline_profile_id", "p");
            if (seasonId != null) QueryUtils.addAndParameter(queryString, profileId != null, "season_id", "s");
            if (year != null) QueryUtils.addAndParameter(queryString, seasonId != null, "year_number", "y");
            final Query query = session.createQuery(queryString.toString());
            if (disciplineId != null) query.setParameter("d", disciplineId);
            if (profileId != null) query.setParameter("p", profileId);
            if (year != null) query.setParameter("y", year);
            if (seasonId != null) query.setParameter("s", seasonId);*/
            final Query query = new QueryUtils.QuerySelectBuilder(session, "CourseWorkTemplate")
                    .addAndParameter("discipline_id", "d", disciplineId)
                    .addAndParameter("discipline_profile_id", "p", profileId)
                    .addAndParameter("season_id", "s", seasonId)
                    .addAndParameter("year_number", "y", year)
                    .build();
            return query.list();
        });
    }

    public void removeTasksFromTemplate(List<Integer> taskIds, Integer templateId) {
        SessionProvider.runTransaction(session -> {
            taskIds.forEach(id -> {
                final TaskToCourse taskToCourse = new TaskToCourse();
                taskToCourse.setTask_id(id);
                taskToCourse.setCourse_work_template_id(templateId);
                final Query query = session.createQuery("delete TaskToCourse where task_id = :t AND course_work_template_id = :c");
                query.setParameter("t", id);
                query.setParameter("c", templateId);
                query.executeUpdate();
            });
            return null;
        });
    }

    public List<Season> getAllSeasons() {
        return SessionProvider.runSession(session -> session.createQuery("from Season").list());
    }

    public void addTaskToTemplate(final int taskId, final int templateId) {
        final TaskToCourse taskToCourse = new TaskToCourse();
        taskToCourse.setCourse_work_template_id(templateId);
        taskToCourse.setTask_id(taskId);
        SessionProvider.runTransaction(session -> {
            session.save(taskToCourse);
            return null;
        });
    }

    public CourseWorkTemplate getCourseWorkTemplateById(final int id) {
        return SessionProvider.getEntryById("CourseWorkTemplate", id);
    }

    public CourseWorkTemplate createCourseWork(
        int discipline_id,
        int season_id,
        int discipline_profile_id,
        int year_number,
        int user_id
    ) {
        return SessionProvider.runTransaction(session -> {
            final CourseWorkTemplate template = new CourseWorkTemplate();
            final EducationTablesDao educationTablesDao = new EducationTablesDao();
            final Discipline discipline = educationTablesDao.getDisciplineById(discipline_id);
            final DisciplineProfile disciplineProfile = educationTablesDao.getDisciplineProfileById(discipline_profile_id);
            if (disciplineProfile.getDiscipline_id() != discipline.getId()) {
                throw new RuntimeException("discipline profile should be from discipline");
            }
            template.setDiscipline(discipline);
            template.setDisciplineProfile(new DisciplineProfile() {{ setId(discipline_profile_id); }});
            template.setSeason(new Season() {{ setId(season_id); }});
            template.setYear_number(year_number);
            template.setUser(new User() {{ setId(user_id); }});
            session.save(template);
            return template;
        });
    }

    public void removeCourseWork(final int courseId) {
        SessionProvider.runTransaction(session -> {
            final CourseWorkTemplate template = new CourseWorkTemplate();
            template.setId(courseId);
            session.remove(template);
            return null;
        });
    }
}
