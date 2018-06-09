package com.enocholumide.bootstrap;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import com.enocholumide.domain.school.course.Assignment;
import com.enocholumide.repositories.*;
import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.school.Program;
import com.enocholumide.domain.school.School;
import com.enocholumide.domain.school.course.Course;
import com.enocholumide.domain.school.course.Schedule;
import com.enocholumide.domain.school.grade.Grade;
import com.enocholumide.domain.shared.enumerated.Levels;
import com.enocholumide.domain.shared.enumerated.Period;
import com.enocholumide.domain.shared.enumerated.Session;
import com.enocholumide.domain.users.Staff;
import com.enocholumide.domain.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProgramsRepository programsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Override
    public void run(String... args) throws Exception {

        // CREATE SCHOOLS

        School engineering = (new School("Faculty of Engineering"));
        School environmental = (new School("Faculty of Environmental Technology"));
        School arts = (new School("Faculty of Arts"));
        School sciences = (new School("Faculty of Sciences"));
        School management = (new School("Faculty of Management"));

        this.schoolRepository.save(engineering);
        this.schoolRepository.save(environmental);
        this.schoolRepository.save(arts);
        this.schoolRepository.save(sciences);
        this.schoolRepository.save(management);

        // CREATE DEPARTMENTS

        Department surveying = (new Department("Surveying", environmental));
        Department geo_informatics = (new Department("Geo-informatics", environmental));
        Department computer_science = (new Department("Computer Science", engineering));
        Department architecture = (new Department("Architecture", environmental));
        Department english = (new Department("English Studies", arts));
        Department chemistry = (new Department("Chemistry", sciences));
        Department busadmin = (new Department("Business Administration", management));

        this.departmentRepository.save(surveying);
        this.departmentRepository.save(geo_informatics);
        this.departmentRepository.save(computer_science);
        this.departmentRepository.save(architecture);
        this.departmentRepository.save(english);
        this.departmentRepository.save(chemistry);
        this.departmentRepository.save(busadmin);

        // CREATE PROGRAMS

        Program surveyingMaster = new Program(surveying, "Master in Surveying", Levels.MASTER, 2);
        Program surveyingBachelor = new Program(surveying, "Bachelor in Surveying", Levels.BACHELOR, 6);
        Program geoInformaticsBachelor = new Program(geo_informatics, "Bachelor in Geoinformatics", Levels.BACHELOR, 6);

        this.programsRepository.save(surveyingMaster);
        this.programsRepository.save(surveyingBachelor);
        this.programsRepository.save(geoInformaticsBachelor);

        // CREATE USERS

        Student olumide = new Student (
                "Olumide",
                "Igbiloba",
                "https://media.licdn.com/dms/image/C5603AQHnQxbadUX5_Q/profile-displayphoto-shrink_100_100/0?e=1531958400&v=beta&t=rc8O0NrheHHILd9KciSZLsXl7uoDNCUtnnkucT6JnFY",
                geo_informatics ,
                "54123");

        Student sandra = new Student(
                "Sandra",
                "Jenks",
                "https://www.mills.edu/uniquely-mills/students-faculty/student-profiles/images/student-profile-gabriela-mills-college.jpg",
                architecture,
                "54124");

        Staff edwards = new Staff(
                "Edward",
                "Peters",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ2D1b_5x2bxNV0A03Aw-3C1v0VvDeO9Nv5wIll_Ny8FM2y_d9nPA",
                architecture,
                "5879MC"
        );

        this.usersRepository.save(olumide);
        this.usersRepository.save(sandra);
        this.usersRepository.save(edwards);

        // CREATE SCHEDULES

        Schedule landSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().minusDays(10)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );
        Schedule javaSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().minusDays(1)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );
        Schedule geoFencingSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().plusDays(2)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );


        // CREATE ASSIGNMENTS
        Assignment assignment = new Assignment();
        assignment.setDeadline(java.sql.Date.valueOf(LocalDate.now().plusMonths(2)));
        assignment.setTitle("Machine Learning for Production");
        assignment.setType("Paper");


        // CREATE COURSE

        Course landSurveying201 = new Course("Land Surveying", "LDSU 201", 2, Levels.BACHELOR, Session.WINTER);
        Course java101 = new Course("Introduction to Java", "ITJA 101", 1, Levels.BACHELOR, Session.SUMMER);
        Course geoFencing = new Course("Basics of geoFencing", "GEOF 101", 1, Levels.MASTER, Session.SUMMER);

        this.coursesRepository.save(landSurveying201);
        this.coursesRepository.save(java101);
        this.coursesRepository.save(geoFencing);

        landSurveying201.getLecturers().add(edwards);
        landSurveying201.addSchedule(landSchedule);
        landSurveying201.addAssignment(assignment);
        landSurveying201.getPrograms().add(surveyingBachelor);
        landSurveying201.getPrograms().add(geoInformaticsBachelor);
        landSurveying201.addStudent(olumide);
        landSurveying201.addStudent(sandra);
        this.coursesRepository.save(landSurveying201);

        java101.getLecturers().add(edwards);
        java101.addSchedule(javaSchedule);
        java101.getPrograms().add(surveyingMaster);
        java101.addStudent(olumide);
        this.coursesRepository.save(java101);


        geoFencing.getLecturers().add(edwards);
        geoFencing.addSchedule(geoFencingSchedule);
        geoFencing.getPrograms().add(geoInformaticsBachelor);
        geoFencing.getPrograms().add(surveyingBachelor);
        geoFencing.addStudent(sandra);
        geoFencing.addStudent(olumide);
        this.coursesRepository.save(geoFencing);

        //this.usersRepository.save(olumide);

        // GRADES
        Grade olumideGrade = new Grade(landSurveying201, olumide, landSurveying201.getSession(), 75); //Course course, Student student, Session session, double score

        Grade olumideJava= new Grade(java101, olumide, java101.getSession()     , 95);

        this.gradeRepository.save(olumideGrade);
        this.gradeRepository.save(olumideJava);


        // NEWS

        News libraryChat = new News(sandra.getDepartment(), sandra, "Library Chat", "" +
                "We are having a meeting by 4 am today in the library. We will be discussing the new exam regulations at the conference room, care to come around?, just type yes!",
                "http://stjosephspublicschool.com/wp-content/uploads/image/complab2.jpg");

        News drawingSets = (new News(olumide.getDepartment(), olumide, "Found drawing sets", "" +
                "We are meeting by 4 am today, care to come around?",
                "https://www.dur.ac.uk/images/st-marys.college/facilities/Library.jpg"));

        Comment comment = new Comment();
        comment.setNews(drawingSets);
        comment.setApplicationUser(sandra);
        comment.setText("I was in the class looking for Olumide. I will come and get it for him");

        libraryChat.getLikes().add(olumide);
        drawingSets.getComments().add(comment);

        this.newsRepository.save(libraryChat);
        this.newsRepository.save(drawingSets);

    }

}
