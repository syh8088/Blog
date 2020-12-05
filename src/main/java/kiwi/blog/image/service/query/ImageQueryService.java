package kiwi.blog.image.service.query;

import kiwi.blog.image.model.entity.Image;
import kiwi.blog.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageQueryService {

    private final ImageRepository imageRepository;

    public Image getImage(long imageNo) {
        return imageRepository.findById(imageNo).get();
    }
}
