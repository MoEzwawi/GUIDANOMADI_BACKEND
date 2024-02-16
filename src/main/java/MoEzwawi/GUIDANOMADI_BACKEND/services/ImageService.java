package MoEzwawi.GUIDANOMADI_BACKEND.services;

import MoEzwawi.GUIDANOMADI_BACKEND.entities.Property;
import MoEzwawi.GUIDANOMADI_BACKEND.entities.Image;
import MoEzwawi.GUIDANOMADI_BACKEND.exceptions.NotFoundException;
import MoEzwawi.GUIDANOMADI_BACKEND.repositories.ImageRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private Cloudinary cloudinary;
    public Image findById(Long id){
        return this.imageRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }
    public Image findThumbnail(Property property){
        return this.imageRepository.findPropertyThumbnail(property);
    }
    public List<Image> findByProperty(Property property){
        return this.imageRepository.findByProperty(property);
    }
    public String uploadImage(MultipartFile file) throws IOException {
        String url = (String) this.cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap())
                .get("url");
        return url;
    }
    public Image save(Property property, MultipartFile file) throws IOException {
        String imageUrl = this.uploadImage(file);
        return this.imageRepository.save(new Image(property,imageUrl));
    }
    public Image save(Property property, String url){
        return this.imageRepository.save(new Image(property,url));
    }
/*    public Image uploadFirstThumbnail(Property property, MultipartFile file) throws IOException {
        Image firstThumbnail = this.save(property,file);
        firstThumbnail.setThumbnail();
        return this.imageRepository.save(firstThumbnail);
    }*/
    public Image uploadThumbnail(Property property, MultipartFile file) throws IOException {
        Image oldThumbnail = this.findThumbnail(property);
        if(oldThumbnail != null){
            oldThumbnail.setNotThumbnail();
            this.imageRepository.save(oldThumbnail);
        }
        String newUrl = this.uploadImage(file);
        Image newThumbnail = new Image(property,newUrl);
        newThumbnail.setThumbnail();
        return this.imageRepository.save(newThumbnail);
    }
    public Image setImageAsNewThumbnail(Property property, Long imageId){
        Image oldThumbnail = this.findThumbnail(property);
        if(oldThumbnail != null){
            oldThumbnail.setNotThumbnail();
            this.imageRepository.save(oldThumbnail);
        }
        Image newThumbnail = this.findById(imageId);
        newThumbnail.setThumbnail();
        return this.imageRepository.save(newThumbnail);
    }
}
