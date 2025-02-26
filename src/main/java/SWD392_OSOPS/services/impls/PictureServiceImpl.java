package SWD392_OSOPS.services.impls;

import SWD392_OSOPS.entities.Picture;
import SWD392_OSOPS.repositories.PictureRepository;
import SWD392_OSOPS.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public Picture getPictureById(int p) {
        return pictureRepository.getReferenceById(p);
    }

    @Override
    public void addPicture(Picture p) {
        pictureRepository.save(p);
    }

    @Override
    public void editPicture(Picture p) {

    }


    @Override
    public void deletePicture(Picture p) {
        pictureRepository.deleteById(p.getPictureId());
    }


}
