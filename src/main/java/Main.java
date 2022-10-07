import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
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
import com.epam.esm.service.TagService;
import com.epam.esm.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Main {
    private static final String filePath = "/src/main/resources/words";
    private static List<String> lines;
    private static Random random;

    private static final int userNumber = 1000;
    private static final int tagNumber = 1000;
    private static final int giftCertificateNumber = 10000;

    public static void main(String... args) throws IOException {

        UserDAO userDAO = new UserRepository();
        OrderDAO orderDAO = new OrderRepository();
        GiftCertificateDAO giftCertificateDAO = new GiftCertificateRepository();
        TagDAO tagDAO = new TagRepository();

        TagService tagService = new TagService(tagDAO);
        GiftCertificateService giftCertificateService = new GiftCertificateService(giftCertificateDAO, tagDAO);
        UserService userService = new UserService(userDAO, orderDAO, giftCertificateService);

        random = new Random();
        loadFile(new File("").getAbsolutePath() + filePath);

        //createUsers(userService);
        //createTags(tagService);
        //createGiftCertificates(giftCertificateService, tagService);
        //makeOrders(userService, giftCertificateService);

    }

    private static void makeOrders(UserService userService, GiftCertificateService giftCertificateService) {
        List<User> users = userService.findAll(1, userNumber);
        User user1 = users.get(random.nextInt(users.size()));
        User user2 = users.get(random.nextInt(users.size()));

        List<GiftCertificate> giftCertificates = giftCertificateService.findAll(1, 5);
        giftCertificates.forEach(gc -> {
            userService.orderGiftCertificate(user1.getId(), gc.getId());
            userService.orderGiftCertificate(user2.getId(), gc.getId());
        });

    }

    private static void createGiftCertificates(GiftCertificateService giftCertificateService,
                                               TagService tagService) {
        for (int i = 0; i < giftCertificateNumber; i ++) {
            String name = getRandomWord();
            String description = getRandomWord();
            double price = random.nextDouble() * random.nextInt(500) + 1;
            int duration = (random.nextInt(25) + 4) % 100;
            Set<Tag> tags = getRandomTags(tagService);

            GiftCertificate gc = new GiftCertificate(name, description, price, duration, tags);
            giftCertificateService.add(gc);
        }
    }

    private static Set<Tag> getRandomTags(TagService tagService) {
        int numTags = random.nextInt(10);
        List<Tag> allTags = tagService.findAll(1, tagNumber);
        Set<Tag> randomTags = new HashSet<>();
        for(int i = 0; i < numTags; i ++) {
            int index = random.nextInt(allTags.size());
            randomTags.add(allTags.get(index));
        }
        return randomTags;
    }

    private static void createUsers(UserService userService) {
        for (int i = 0; i < userNumber; i ++) {
            String userName = getRandomWord();
            String userLastname = getRandomWord();
            int userMoney = random.nextInt(100000);

            User user = new User(userName, userLastname, userMoney);
            userService.add(user);
        }
    }

    private static void createTags(TagService tagService) {
        for (int i = 0; i < tagNumber; i ++ ) {
            String name = getRandomWord();
            Tag tag = new Tag(name);
            tagService.add(tag);
        }
    }

    private static String getRandomWord() {
        return lines.get(random.nextInt(lines.size()));
    }

    private static void loadFile(String path) {
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
