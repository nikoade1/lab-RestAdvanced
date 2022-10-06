import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.User;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.OrderDAO;
import com.epam.esm.repository.TagDAO;
import com.epam.esm.repository.UserDAO;
import com.epam.esm.repository.impl.GiftCertificateRepository;
import com.epam.esm.repository.impl.OrderRepository;
import com.epam.esm.repository.impl.TagRepository;
import com.epam.esm.repository.impl.UserRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;

public class Main {
    public static void main(String... args) {
        UserDAO userDAO = new UserRepository();
        OrderDAO orderDAO = new OrderRepository();
        GiftCertificateDAO giftCertificateDAO = new GiftCertificateRepository();
        TagDAO tagDAO = new TagRepository();

        GiftCertificateService giftCertificateService = new GiftCertificateService(giftCertificateDAO, tagDAO);
        UserService userService = new UserService(userDAO, orderDAO, giftCertificateService);

        userService.add(new User("jon", "snow", 10000));

    }
}
