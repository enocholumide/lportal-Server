package com.enocholumide.bootstrap;

import com.enocholumide.domain.news.Comment;
import com.enocholumide.domain.news.News;
import com.enocholumide.domain.organisation.Organisation;
import com.enocholumide.domain.organisation.UserOrganisation;
import com.enocholumide.domain.school.course.*;
import com.enocholumide.domain.shared.enumerated.*;
import com.enocholumide.domain.users.Student;
import com.enocholumide.repositories.*;
import com.enocholumide.domain.school.Department;
import com.enocholumide.domain.school.Program;
import com.enocholumide.domain.school.School;
import com.enocholumide.domain.users.Staff;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(Instant.now().toString());

        System.out.println(Instant.parse("2018-07-07T19:19:27.050Z"));

        Instant instant = Instant.parse("2018-07-07T19:19:27.050Z");
        System.out.println(instant.toString());


        // CREATE ORGANISATION
        Organisation nwUniversity = new Organisation("North West University");
        nwUniversity.setLogoUrl("https://i.pinimg.com/originals/3c/fb/8e/3cfb8e78c1403123665b40820dc9179b.jpg");
        this.organisationRepository.save(nwUniversity);

        // CREATE SCHOOLS

        School engineering = (new School(nwUniversity, "Faculty of Engineering"));
        School environmental = (new School(nwUniversity, "Faculty of Environmental Technology"));
        School arts = (new School(nwUniversity, "Faculty of Arts"));
        School sciences = (new School(nwUniversity, "Faculty of Sciences"));
        School management = (new School(nwUniversity, "Faculty of Management"));

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

         Staff joseph = new Staff(
         "Joseph",
         "Frank",
         "admin@lportal.com",
         "https://www.impulse-info.com/wp-content/uploads/2017/04/John-Doe.jpg",
         architecture,
         "5879MC"
         );

         UserOrganisation userOrganisationJoseph = new UserOrganisation(architecture.getSchool().getOrganisation(), joseph);
         userOrganisationJoseph.getRoles().add(Role.ADMIN);
         userOrganisationJoseph.getRoles().add(Role.TEACHER);

         joseph.getOrganisations().add(userOrganisationJoseph);
         joseph.setPassword(passwordEncoder.encode("secret"));
         this.usersRepository.save(joseph);

        Student olumide = new Student (
                "Olumide",
                "Igbiloba",
                "enocholumide@gmail.com",
                "https://media.licdn.com/dms/image/C5603AQHnQxbadUX5_Q/profile-displayphoto-shrink_100_100/0?e=1531958400&v=beta&t=rc8O0NrheHHILd9KciSZLsXl7uoDNCUtnnkucT6JnFY",
                geoInformaticsBachelor ,
                "54123");

        olumide.getOrganisations().add(new UserOrganisation(geo_informatics.getSchool().getOrganisation(), olumide));
        olumide.setPassword(passwordEncoder.encode("secret2"));
        this.usersRepository.save(olumide);

        Student sandra = new Student(
                "Sandra",
                "Jenks",
                "sandrajenks@yahoo.com",
                "https://www.mills.edu/uniquely-mills/students-faculty/student-profiles/images/student-profile-gabriela-mills-college.jpg",
                surveyingBachelor,
                "54124");

        UserOrganisation userOrganisation= new UserOrganisation(surveyingBachelor.getDepartment().getSchool().getOrganisation(), sandra);
        userOrganisation.getRoles().add(Role.STUDENT);
        sandra.getOrganisations().add(userOrganisation);
        sandra.setPassword(passwordEncoder.encode("secret3"));
        this.usersRepository.save(sandra);

        surveyingBachelor.getStudents().add(sandra);
        geoInformaticsBachelor.getStudents().add(olumide);

        this.programsRepository.save(surveyingBachelor);
        this.programsRepository.save(geoInformaticsBachelor);


        // CREATE COURSES

        Course landSurveying201 = new Course(surveyingBachelor,"Land Surveying", "LDSU 201", 2, Levels.BACHELOR, Session.WINTER);
        Course java101 = new Course(geoInformaticsBachelor, "Introduction to Java", "ITJA 101", 1, Levels.BACHELOR, Session.SUMMER);
        Course geoFencing = new Course(surveyingMaster, "Basics of geoFencing", "GEOF 101", 1, Levels.MASTER, Session.SUMMER);

        // Assign teacher
        landSurveying201.getLecturers().add(joseph);
        java101.getLecturers().add(joseph);
        geoFencing.getLecturers().add(joseph);

        this.coursesRepository.save(landSurveying201);
        this.coursesRepository.save(java101);
        this.coursesRepository.save(geoFencing);

        surveyingBachelor.getCourses().add(landSurveying201);
        surveyingMaster.getCourses().add(geoFencing);
        geoInformaticsBachelor.getCourses().add(java101);

        this.programsRepository.save(surveyingMaster);
        this.programsRepository.save(surveyingBachelor);
        this.programsRepository.save(geoInformaticsBachelor);


        // ADD COURSES
        sandra.getStudentCourses().add(new StudentCourse(landSurveying201, sandra, Status.JOINED));
        sandra.getStudentCourses().add(new StudentCourse(java101, sandra, Status.JOINED));
        this.usersRepository.save(sandra);


        // NEWS

        News drawingSets = (new News(joseph.getDepartment(), joseph, "Found drawing sets", "" +
                "Found drawing sets in the studio, someone left it in the studio since last week?",
                "https://www.dur.ac.uk/images/st-marys.college/facilities/Library.jpg"));

        Comment comment = new Comment();
        comment.setNews(drawingSets);
        comment.setApplicationUser(joseph);
        comment.setText("Call or text me to get this item");

        drawingSets.getLikes().add(joseph);
        drawingSets.getComments().add(comment);

        this.newsRepository.save(drawingSets);

        // CREATE SCHEDULES

        Schedule landSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().minusDays(10)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );
        Schedule javaSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().minusDays(1)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );
        Schedule geoFencingSchedule = new Schedule(java.sql.Date.valueOf(LocalDate.now().plusDays(2)), java.sql.Date.valueOf(LocalDate.now().plusMonths(6)), Period.WEEKLY );

        // CREATE ASSIGNMENTS
        Assignment assignment = new Assignment();
        assignment.setDeadline((Instant.now().plus(3, ChronoUnit.DAYS)));
        assignment.setTitle("Machine Learning for Production");
        assignment.setType("Paper");

        landSurveying201.addAssignment(assignment);
        this.coursesRepository.save(landSurveying201);

        /**

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

         */

    }



}
