package apt.hthang.doctruyenonline.service.impl;

import apt.hthang.doctruyenonline.entity.Chapter;
import apt.hthang.doctruyenonline.entity.History;
import apt.hthang.doctruyenonline.entity.User;
import apt.hthang.doctruyenonline.repository.HistoryRepository;
import apt.hthang.doctruyenonline.service.HistoryService;
import apt.hthang.doctruyenonline.utils.ConstantsListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;



@Service
public class HistoryServiceImpl implements HistoryService {
    
    private final HistoryRepository historyRepository;
    
    @Autowired
    public HistoryServiceImpl(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }
    
    /**
     * Lấy Chapter Mới đọc
     *
     * @param userId
     * @param storyId
     * @return ChapterSummary
     */
    @Override
    public Chapter findChapterReadByUser(Long userId, Long storyId) {
        Optional< History > ufavorites = historyRepository
                .findTopByUser_IdAndChapter_Story_IdAndChapter_StatusInOrderByDateViewDesc(userId, storyId, ConstantsListUtils.LIST_CHAPTER_DISPLAY);
        return ufavorites.map(History::getChapter).orElse(null);
    }
    
    /**
     * Kiểm tra tồn tại Favorites trong khoảng
     *
     * @param chapterId
     * @param locationIP
     * @param startDate
     * @param endDate
     * @return boolean
     */
    @Override
    public boolean checkChapterAndLocationIPInTime(Long chapterId, String locationIP, Date startDate, Date endDate) {
        return historyRepository
                .existsByChapter_IdAndLocationIPAndDateViewBetween(chapterId, locationIP, startDate, endDate);
    }
    
    /**
     * Lưu Lịch Sử đọc truyện
     *
     * @param chapter
     * @param user
     * @param LocationIP
     * @param uView
     * @return void
     */
    @Override
    public void saveHistory(Chapter chapter, User user, String LocationIP, Integer uView) {
        History history = new History();
        history.setStatus(uView);
        history.setUser(user);
        history.setChapter(chapter);
        history.setLocationIP(LocationIP);
        historyRepository.save(history);
    }
    
}
