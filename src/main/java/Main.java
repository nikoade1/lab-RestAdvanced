import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.impl.GiftCertificateRepository;
import com.epam.esm.repository.impl.TagRepository;

import java.util.Arrays;
import java.util.HashSet;

public class Main {
    public static void main(String... args) {
        TagRepository tagRepository = new TagRepository();
        GiftCertificateRepository giftCertificateRepository = new GiftCertificateRepository();

        GiftCertificate g = new GiftCertificate();
        g.setPrice(14);
        g.setDuration(12);
        g.setName("gifttt");
        g.setDescription("saocari");
        g.setTags(new HashSet<>(Arrays.asList(new Tag("fasollasi"), new Tag("doremi"))));
        giftCertificateRepository.add(g);
    }
}
