package cz.cvut.fel.rsp.travelandwork.service;

import cz.cvut.fel.rsp.travelandwork.dao.AchievementCategorizedDao;
import cz.cvut.fel.rsp.travelandwork.model.AchievementCategorized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AchievementCategorizedService {
    private final AchievementCategorizedDao achievementCategorizedDao;

    @Autowired
    public AchievementCategorizedService(AchievementCategorizedDao achievementCategorizedDao) {
        this.achievementCategorizedDao = achievementCategorizedDao;
    }

    @Transactional
    public List<AchievementCategorized> findAll() {
        return achievementCategorizedDao.findAll();
    }

    @Transactional
    public List<AchievementCategorized> findAllInCategory(Category category) {
        List<AchievementCategorized> all = achievementCategorizedDao.findAll();
        List<AchievementCategorized> result = new ArrayList<AchievementCategorized>();
        for(AchievementCategorized a : all) {
            if(a.getCategory().equals(category)) {
                result.add(a);
            }
        }

        if(result.isEmpty()) {
            result = null;
        }

        return result;
    }

    @Transactional
    public AchievementCategorized find(Long id) {
        return achievementCategorizedDao.find(id);
    }

    @Transactional
    public void create(AchievementCategorized achievement) {
        achievementCategorizedDao.persist(achievement);
    }

    @Transactional
    public void update(AchievementCategorized achievement) {
        Objects.requireNonNull(achievement);
        achievementCategorizedDao.update(achievement);
    }
}
