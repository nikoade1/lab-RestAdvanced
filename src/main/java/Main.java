import com.epam.esm.model.Tag;
import com.epam.esm.repository.impl.GiftCertificateRepository;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.service.TagService;

import java.util.List;

public class Main {
    public static void main(String... args) {
        TagRepository tagRepository = new TagRepository();
        GiftCertificateRepository giftCertificateRepository = new GiftCertificateRepository();

        TagService ts = new TagService(tagRepository);

        List<Tag> ls = ts.findAll();

    }
}
