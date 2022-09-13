import com.epam.esm.TagRepository;
import com.epam.esm.model.Tag;

public class Main {
    public static void main(String... args) {
        TagRepository repository = new TagRepository();

        Tag tag = new Tag("jim");
        repository.add(tag);
        System.out.println("Added tag " + tag);

        tag = repository.find(tag.getId());
        System.out.println("Found student " + tag);

        Tag tag2 = new Tag("bob");
        repository.add(tag2);
        System.out.println("Added tag " + tag2);

        repository.findAll().forEach(System.out::println);

    }
}
