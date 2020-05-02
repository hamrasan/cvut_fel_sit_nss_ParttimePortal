package cz.cvut.fel.rsp.travelandwork.service;

import cz.cvut.fel.rsp.travelandwork.dao.AchievementSpecialDao;
import cz.cvut.fel.rsp.travelandwork.dao.EnrollmentDao;
import cz.cvut.fel.rsp.travelandwork.dao.UserDao;
import cz.cvut.fel.rsp.travelandwork.dto.AchievementSpecialDto;
import cz.cvut.fel.rsp.travelandwork.dto.EnrollmentDto;
import cz.cvut.fel.rsp.travelandwork.exception.NotAllowedException;
import cz.cvut.fel.rsp.travelandwork.exception.NotFoundException;
import cz.cvut.fel.rsp.travelandwork.model.AchievementSpecial;
import cz.cvut.fel.rsp.travelandwork.model.Enrollment;
import cz.cvut.fel.rsp.travelandwork.model.EnrollmentState;
import cz.cvut.fel.rsp.travelandwork.model.User;
import cz.cvut.fel.rsp.travelandwork.service.security.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentDao enrollmentDao;
    private final TranslateService translateService;
    private final AccessService accessService;
    private final UserDao userDao;
    private final AchievementSpecialDao achievementSpecialDao;

    @Autowired
    public EnrollmentService(EnrollmentDao enrollmentDao, TranslateService translateService, AccessService accessService, UserDao userDao, AchievementSpecialDao achievementSpecialDao) {
        this.enrollmentDao = enrollmentDao;
        this.translateService =  translateService;
        this.accessService = accessService;
        this.userDao = userDao;
        this.achievementSpecialDao = achievementSpecialDao;
    }

    private List<Enrollment> findAll(){
        return enrollmentDao.findAll();
    }

    @Transactional
    public List<EnrollmentDto> findAllDto(){

        List<EnrollmentDto> enrollmentDtos = new ArrayList<>();

        for (Enrollment e : enrollmentDao.findAll()) {
            enrollmentDtos.add(translateService.translateEnrollment(e));
        }

        return enrollmentDtos;
    }


    @Transactional
    public List<EnrollmentDto> findAllActiveEnded(){
        List<EnrollmentDto> enrollmentDtos = findAllDto();
        List<EnrollmentDto> newEnrollmentDtos = new ArrayList<>();
        for (EnrollmentDto e: enrollmentDtos) {
            if (e.getState().equals(EnrollmentState.ACTIVE) && e.getTripSession().getTo_date().isBefore(ChronoLocalDate.from(LocalDateTime.now()))){
                newEnrollmentDtos.add(e);
            }
        }
        return newEnrollmentDtos;
    }


    private Enrollment find(Long id){
        return enrollmentDao.find(id);
    }

    @Transactional
    public List<EnrollmentDto> findAllOfUser(User current_user) throws NotAllowedException {

        User user = accessService.getUser(current_user);
        if (user == null) throw new NotAllowedException();

        List<Enrollment> enrollments = user.getTravel_journal().getEnrollments();
        List<EnrollmentDto> enrollmentDtos = new ArrayList<EnrollmentDto>();

        if (enrollments != null && enrollments.size()>0){
            for (Enrollment e : enrollments) {
                enrollmentDtos.add(translateService.translateEnrollment(e));
            }
        }
        return enrollmentDtos;
    }

    @Transactional
    public List<EnrollmentDto> findAllOfUserFinished(User current_user) throws NotAllowedException {
        List<EnrollmentDto> userEnrollments = findAllOfUser(current_user);
        List<EnrollmentDto> finished = new ArrayList<EnrollmentDto>();

        for (EnrollmentDto enrollmentDto : userEnrollments) {
            if (enrollmentDto.getState()== EnrollmentState.FINISHED) finished.add(enrollmentDto);
        }
        return finished;
    }

    @Transactional
    public List<EnrollmentDto> findAllOfUserActive(User current_user) throws NotAllowedException {
        List<EnrollmentDto> userEnrollments = findAllOfUser(current_user);
        List<EnrollmentDto> active_canceled = new ArrayList<EnrollmentDto>();

        for (EnrollmentDto enrollmentDto : userEnrollments) {
            if (enrollmentDto.getState()!= EnrollmentState.FINISHED) active_canceled.add(enrollmentDto);
        }
        return active_canceled;
    }

    @Transactional
    public List<EnrollmentDto> findAllOfUserFinished(Long id) throws NotFoundException, NotAllowedException {
        User user = userDao.find(id);
        if (user == null) throw new NotFoundException();
        return findAllOfUserFinished(user);
    }

    @Transactional
    public List<EnrollmentDto> findAllOfUserActive(Long id) throws NotFoundException, NotAllowedException {
        User user = userDao.find(id);
        if (user == null) throw new NotFoundException();
        return findAllOfUserActive(user);
    }

    @Transactional
    public void close(EnrollmentDto enrollmentDto){
        Enrollment enrollment = find(enrollmentDto.getId());
        enrollment.setState(EnrollmentState.FINISHED);
        enrollment.setActual_xp_reward(enrollmentDto.getActual_xp_reward());

        List<AchievementSpecial> achievementSpecials = new ArrayList<>();
        for (AchievementSpecialDto achievementSpecialDto : enrollmentDto.getRecieved_achievements_special()) {
            achievementSpecials.add(achievementSpecialDao.find(achievementSpecialDto.getId()));
        }

        enrollment.setRecieved_achievements_special(achievementSpecials);
        enrollmentDao.update(enrollment);
    }
}
