import com.epam.esm.impl.GiftCertificateRepository;
import com.epam.esm.impl.TagRepository;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

public class Main {
    public static void main(String... args) {
        TagRepository tagRepository = new TagRepository();
        GiftCertificateRepository giftCertificateRepository = new GiftCertificateRepository();

        Tag tag = new Tag("zidane");
        Tag tag2 = new Tag("kaka");
        Tag tag3 = new Tag("roberto carlos");

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
