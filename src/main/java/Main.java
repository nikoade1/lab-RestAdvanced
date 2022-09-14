import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.TagRepository;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

public class Main {
    public static void main(String... args) {
        TagRepository tagRepository = new TagRepository();
        GiftCertificateRepository giftCertificateRepository = new GiftCertificateRepository();

        Tag tag = new Tag("jim");
        Tag tag2 = new Tag("bob");
        Tag tag3 = new Tag("blake");

        GiftCertificate jimBirthday = new GiftCertificate("jim", "as", 10, 4);
        GiftCertificate bobBirthday = new GiftCertificate("bob", "ad", 14, 1);

        jimBirthday.addTag(tag);
        jimBirthday.addTag(tag2);
        bobBirthday.addTag(tag2);

        tagRepository.add(tag);
        tagRepository.add(tag2);
        tagRepository.add(tag3);

        giftCertificateRepository.add(jimBirthday);
        giftCertificateRepository.add(bobBirthday);

    }
}
