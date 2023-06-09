package apt.hthang.doctruyenonline.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Date;


public interface FollowSummar {
    
    StoryFollow getStory();
    
    @Value("#{@myComponent.getChapterReading(target.story.id, target.user.id)}")
    Long getChapterId();
    
    public interface StoryFollow {
        
        Long getId();
        
        String getVnName();
        
        Date getUpdateDate();
        
    }
}
