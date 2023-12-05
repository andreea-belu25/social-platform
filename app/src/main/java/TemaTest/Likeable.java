package TemaTest;

//  definesc fct comune din clasele Comentariu si Postare
public interface Likeable {
    public boolean isAlreadyLiked (String username);
    public void addUsernameThatLiked (String username);
    public boolean isUnliked (String username);
    public void deleteUsernameThatLiked(String username);

}
