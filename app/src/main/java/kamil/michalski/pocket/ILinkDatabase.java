package kamil.michalski.pocket;


import java.util.List;

public interface ILinkDatabase {

    List<Link> getLinks();

    void create(Link link);

}
