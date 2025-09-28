package TemaTest;

// Define common functions from Comment and Post classes
public interface Likeable {
    public boolean isAlreadyLiked(String username);
    public void addUsernameThatLiked(String username);
    public boolean isUnliked(String username);
    public void deleteUsernameThatLiked(String username);
}
