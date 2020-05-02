package cz.cvut.fel.rsp.travelandwork.rest;

import cz.cvut.fel.rsp.travelandwork.dto.EnrollmentDto;
import cz.cvut.fel.rsp.travelandwork.dto.RequestWrapperEnrollment;
import cz.cvut.fel.rsp.travelandwork.exception.NotAllowedException;
import cz.cvut.fel.rsp.travelandwork.exception.NotFoundException;
import cz.cvut.fel.rsp.travelandwork.security.SecurityUtils;
import cz.cvut.fel.rsp.travelandwork.service.EnrollmentService;
import cz.cvut.fel.rsp.travelandwork.service.UserReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/enrollment")
public class EnrollmentController {

    private static final Logger LOG = LoggerFactory.getLogger(EnrollmentController.class);
    private final EnrollmentService enrollmentService;
    private final UserReviewService userReviewService;


    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, UserReviewService userReviewService) {
        this.enrollmentService = enrollmentService;
        this.userReviewService = userReviewService;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrollmentDto> getAllOfUserFinished() throws NotAllowedException {
        return enrollmentService.findAllOfUserFinished(SecurityUtils.getCurrentUser());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrollmentDto> getAllOfUserActiveAndCancel() throws NotAllowedException {
        return enrollmentService.findAllOfUserActive(SecurityUtils.getCurrentUser());
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER', 'ROLE_ADMIN')")
    @GetMapping(value = "/complete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrollmentDto> getAllOfUserFinishedAdmin(@PathVariable Long id) throws NotAllowedException, NotFoundException {
        return enrollmentService.findAllOfUserFinished(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER', 'ROLE_ADMIN')")
    @GetMapping(value = "/active/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrollmentDto> getAllOfUserActiveAndCancelAdmin(@PathVariable Long id) throws NotAllowedException, NotFoundException {
        return enrollmentService.findAllOfUserActive(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER', 'ROLE_ADMIN')")
    @GetMapping(value = "/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EnrollmentDto> getAllActiveEnded() {
        return enrollmentService.findAllActiveEnded();
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERUSER', 'ROLE_ADMIN')")
    @PatchMapping(value = "/close", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> close(@RequestBody RequestWrapperEnrollment requestWrapperEnrollment) throws Exception {
        enrollmentService.close(requestWrapperEnrollment.getEnrollmentDto());
        userReviewService.create(requestWrapperEnrollment.getEnrollmentDto(),SecurityUtils.getCurrentUser(),
                requestWrapperEnrollment.getTripSessionId(), requestWrapperEnrollment.getUserReview() );
        //LOG.debug("User {} successfully registered.", user);
        //return new ResponseEntity<>(headers, HttpStatus.CREATED);
        return null;
    }

}
