package SWD392_OSOPS.services;

import SWD392_OSOPS.entities.Picture;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {
    public void addPicture(Picture p);
    public void editPicture(Picture p);
    public void deletePicture(Picture p);
    public Picture getPictureById(int p);
}
