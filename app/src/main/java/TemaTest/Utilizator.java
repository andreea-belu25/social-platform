package TemaTest;

import jdk.jshell.execution.Util;

import java.io.IOException;
import java.util.Arrays;

public class Utilizator {
    private String username;
    private String password;
    private String[] followingUsers;

    public Utilizator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Utilizator(String username, String password, String[] followingUsers) {
        this.username = username;
        this.password = password;
        this.followingUsers = followingUsers;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String[] getFollowingUsers() {
        return followingUsers;
    }

    public void addNewFollowingUser(String username) {
        if (this.followingUsers != null) {
            String[] usersCopy = new String[this.followingUsers.length + 1];
            int index = 0;
            
            for (index = 0; index < this.followingUsers.length; index++) {
                usersCopy[index] = this.followingUsers[index];
            }
            
            usersCopy[index] = username;
            this.followingUsers = usersCopy;
        } else {
            String[] usersCopy = new String[1];
            usersCopy[0] = username;
            this.followingUsers = usersCopy;
        }
    }

    public void deleteUserFollowed(String username) {
        String[] usersCopy = new String[this.followingUsers.length - 1];
        int k = 0;
        
        for (int index = 0; index < this.followingUsers.length; index++) {
            if (!this.followingUsers[index].equals(username))
                usersCopy[k++] = this.followingUsers[index];
        }
        
        this.followingUsers = usersCopy;
    }

    // Form a string with all users I follow, delimiting them with #
    public String toString() {
        String finalFollowingUsers = "";
        
        if (this.followingUsers != null && this.followingUsers.length > 0) {
            int index = 0;
            for (index = 0; index < this.followingUsers.length - 1; index++) {
                finalFollowingUsers = finalFollowingUsers + this.followingUsers[index];
                finalFollowingUsers = finalFollowingUsers + "#";
            }
            finalFollowingUsers = finalFollowingUsers + this.followingUsers[index];
        }
        
        String finalUser = "";
        if (!finalFollowingUsers.isEmpty())
            finalUser = this.username + "," + this.password + "," + finalFollowingUsers;
        else
            finalUser = this.username + "," + this.password;
            
        return finalUser;
    }

    // I don't make it static because it belongs to the object from the current class
    public boolean isUserFollowed(String username) {
        if (this.followingUsers != null) {
            for (int index = 0; index < this.followingUsers.length; index++) {
                if (this.followingUsers[index].equals(username))
                    return true;
            }
        }
        return false;
    }

    public boolean isUserUnfollowed(String username) {
        if (this.followingUsers != null) {
            for (int index = 0; index < this.followingUsers.length; index++) {
                if (this.followingUsers[index].equals(username))
                    return false;
            }
        }
        return true;
    }

    public static Utilizator findUser(String user) {
        for (int index = 0; index < App.users.length; index++)
            if (App.users[index].getUsername().equals(user))
                return App.users[index];
        return null;
    }

    public static boolean isUserAuthentificated(String[] strings) {
        if (strings.length == 1 || strings.length == 2) {
            System.out.print("{ 'status' : 'error', 'message' : 'You need to be authenticated'}");
            return false;
        }

        String[] userAndPassword = App.usernameAndPassword(strings);
        try {
            String passwordUsername = CSVFileActions.doesUserExist(userAndPassword[0]);
            if (passwordUsername == null || !passwordUsername.equals(userAndPassword[1])) {
                System.out.print("{ 'status' : 'error', 'message' : 'Login failed'}");
                return false;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        return true;
    }

    public static void addNewUser(String username, String password) {
        Utilizator user = new Utilizator(username, password);
        Utilizator[] usersCopy = new Utilizator[App.users.length + 1];
        int index = 0;
        
        for (index = 0; index < App.users.length; index++) {
            usersCopy[index] = App.users[index];
        }
        
        usersCopy[index] = user;
        App.users = usersCopy;
    }

    // Add a post to the posts of the transmitted user
    public static Postare[] addPostToUserPosts(String username) {
        Postare[] postsFollowings = new Postare[0];
        
        for (int index1 = 0; index1 < App.posts.length; index1++) {
            if (App.posts[index1].getUsername().equals(username)) {
                Postare[] postsCopy = new Postare[postsFollowings.length + 1];
                int index2 = 0;
                
                for (index2 = 0; index2 < postsFollowings.length; index2++) {
                    postsCopy[index2] = postsFollowings[index2];
                }
                
                postsCopy[index2] = App.posts[index1];
                postsFollowings = postsCopy;
            }
        }
        
        return postsFollowings;
    }

    // Get the posts of users I follow
    public Postare[] getFollowingPost() {
        Postare[] postsFollowings = new Postare[0];
        
        for (int index = 0; index < this.followingUsers.length; index++) {
            for (int index1 = 0; index1 < App.posts.length; index1++) {
                if (App.posts[index1].getUsername().equals(this.followingUsers[index])) {
                    Postare[] postsCopy = new Postare[postsFollowings.length + 1];
                    int index2 = 0;
                    
                    for (index2 = 0; index2 < postsFollowings.length; index2++) {
                        postsCopy[index2] = postsFollowings[index2];
                    }
                    
                    postsCopy[index2] = App.posts[index1];
                    postsFollowings = postsCopy;
                }
            }
        }
        
        return postsFollowings;
    }

    public static void createUser(String[] strings) {
        if (strings.length == 1) {
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide username'}");
            return;
        }

        if (strings.length == 2) {
            System.out.print("{ 'status' : 'error', 'message' : 'Please provide password'}");
            return;
        }

        String[] userAndPassword = App.usernameAndPassword(strings);
        try {
            if (CSVFileActions.doesUserExist(userAndPassword[0]) != null) {
                System.out.print("{ 'status' : 'error', 'message' : 'User already exists'}");
            } else {
                addNewUser(userAndPassword[0], userAndPassword[1]);
                System.out.print("{ 'status' : 'ok', 'message' : 'User created successfully'}");
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void followUser(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to follow was provided'}");
            return;
        }

        String usernameToFollow = App.toString(App.extractParameterValue(strings[3]));
        Utilizator userToFollow = findUser(usernameToFollow);
        String usernameCurrent = App.toString(App.extractParameterValue(strings[1]));
        Utilizator userCurrent = findUser(usernameCurrent);

        if (userToFollow == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to follow was not valid'}");
            return;
        }

        if (userCurrent != null && userCurrent.isUserFollowed(usernameToFollow)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to follow was not valid'}");
            return;
        }

        if (userCurrent != null)
            userCurrent.addNewFollowingUser(usernameToFollow);

        System.out.print("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void unfollowUser(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to unfollow was provided'}");
            return;
        }

        String usernameToFollow = App.toString(App.extractParameterValue(strings[3]));
        Utilizator userToFollow = findUser(usernameToFollow);
        String usernameCurrent = App.toString(App.extractParameterValue(strings[1]));
        Utilizator userCurrent = findUser(usernameCurrent);

        if (userToFollow == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to unfollow was not valid'}");
            return;
        }

        if (userCurrent != null && userCurrent.isUserUnfollowed(usernameToFollow)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to unfollow was not valid'}");
            return;
        }

        if (userCurrent != null)
            userCurrent.deleteUserFollowed(usernameToFollow);

        System.out.print("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void getUserPosts(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to list posts was provided'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[3]));
        int nr = 0;

        for (int index = 0; index < App.users.length; index++) {
            if (!App.users[index].getUsername().equals(username))
                nr++;
        }

        // The user doesn't exist in the users vector
        if (nr == App.users.length) {
            System.out.print("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
            return;
        }

        // Check if current user follows username
        nr = 0;
        String usernameCurrent = App.toString(App.extractParameterValue(strings[1]));
        Utilizator userCurrent = findUser(usernameCurrent);

        if (userCurrent != null) {
            if (userCurrent.getFollowingUsers() != null) {
                for (int index = 0; index < userCurrent.getFollowingUsers().length; index++)
                    if (!userCurrent.getFollowingUsers()[index].equals(username))
                        nr++;
                        
                if (nr == userCurrent.getFollowingUsers().length) {
                    System.out.print("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
                    return;
                }
            }

            if (userCurrent.getFollowingUsers() == null) {
                System.out.print("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
                return;
            }
        }

        // Vector with user posts which I order by date and then print
        Postare[] postsFollowings = addPostToUserPosts(username);
        Arrays.sort(postsFollowings);

        String stringToPrint = "{ 'status' : " + "'ok'" + ", " + "'message' : [";
        for (int index = 0; index < postsFollowings.length - 1; index++) {
            stringToPrint = stringToPrint + "{'post_id':'" + (postsFollowings[index].getPostId());
            stringToPrint = stringToPrint + "', 'post_text':'" + postsFollowings[index].getPostText();
            stringToPrint = stringToPrint + "','post_date':'" + postsFollowings[index].getDatePost() + "'},";
        }

        stringToPrint = stringToPrint + "{'post_id':'" + (postsFollowings[postsFollowings.length - 1].getPostId());
        stringToPrint = stringToPrint + "', 'post_text':'" + postsFollowings[postsFollowings.length - 1].getPostText();
        stringToPrint = stringToPrint + "','post_date':'" + postsFollowings[postsFollowings.length - 1].getDatePost() + "'}]}";
        System.out.println(stringToPrint);
    }

    // Display users that the user follows
    public static void getFollowing(String[] strings) {
        String usernameCurrent = App.toString(App.extractParameterValue(strings[1]));
        Utilizator userCurrent = findUser(usernameCurrent);

        if (userCurrent != null) {
            String stringToPrint = "{ 'status' : " + "'ok'" + ", " + "'message' : [";
            for (int index = 0; index < userCurrent.getFollowingUsers().length - 1; index++) {
                stringToPrint = stringToPrint + "'" + userCurrent.getFollowingUsers()[index] + "', ";
            }
            stringToPrint = stringToPrint + "'" + userCurrent.getFollowingUsers()[userCurrent.getFollowingUsers().length - 1] + "']}";
            System.out.print(stringToPrint);
        }
    }

    // Display users who follow the user
    public static void getFollowers(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No username to list followers was provided'}");
            return;
        }

        String usernameToFollow = App.toString(App.extractParameterValue(strings[3]));
        int nr = 0;
        for (int index = 0; index < App.users.length; index++) {
            if (!App.users[index].getUsername().equals(usernameToFollow))
                nr++;
                
            // Doesn't exist in the users vector
            if (nr == App.users.length) {
                System.out.print("{ 'status' : 'error', 'message' : 'The username to list followers was not valid'}");
                return;
            }
        }

        String usernameCurrent = App.toString(App.extractParameterValue(strings[1]));
        Utilizator userCurrent = findUser(usernameCurrent);
        
        if (userCurrent != null) {
            String stringToPrint = "{ 'status' : " + "'ok'" + ", " + "'message' : [";
            for (int index = 0; index < App.users.length - 1; index++) {
                if (App.users[index].getFollowingUsers() != null) {
                    for (int index1 = 0; index1 < App.users[index].getFollowingUsers().length; index1++) {
                        if (usernameCurrent.equals(App.users[index].getFollowingUsers()[index1])) {
                            stringToPrint = stringToPrint + "'" + App.users[index].getUsername() + "',";
                        }
                    }
                }
            }
            
            if (App.users[App.users.length - 1].getFollowingUsers() != null) {
                for (int index1 = 0; index1 < App.users[App.users.length - 1].getFollowingUsers().length; index1++) {
                    if (usernameCurrent.equals(App.users[App.users.length - 1].getFollowingUsers()[index1])) {
                        stringToPrint = stringToPrint + "'" + App.users[App.users.length - 1].getUsername() + "']}";
                    }
                }
            }
            System.out.print(stringToPrint);
        }
    }

    public void getFollowingsPosts(String[] strings) {
        Postare[] postsFollowings = getFollowingPost();
        Arrays.sort(postsFollowings);

        String stringToPrint = "{ 'status' : " + "'ok'" + ", " + "'message' : [";
        for (int index = 0; index < postsFollowings.length - 1; index++) {
            stringToPrint = stringToPrint + "{'post_id':'" + (postsFollowings[index].getPostId());
            stringToPrint = stringToPrint + "', 'post_text':'" + postsFollowings[index].getPostText();
            stringToPrint = stringToPrint + "','post_date':'" + postsFollowings[index].getDatePost();
            stringToPrint = stringToPrint + "','username':'" + postsFollowings[index].getUsername() + "'},";
        }

        stringToPrint = stringToPrint + "{'post_id':'" + (postsFollowings[postsFollowings.length - 1].getPostId());
        stringToPrint = stringToPrint + "', 'post_text':'" + postsFollowings[postsFollowings.length - 1].getPostText();
        stringToPrint = stringToPrint + "','post_date':'" + postsFollowings[postsFollowings.length - 1].getDatePost();
        stringToPrint = stringToPrint + "','username':'" + postsFollowings[postsFollowings.length - 1].getUsername() + "'}]}";
        System.out.println(stringToPrint);
    }

    // following - I follow them
    // followed - those who follow me

    /* I use an occurrence vector to retain how many people follow each user
     * then I display users in descending order of number of followers, also treating the possibility
     * where I don't have 5 such users, in which case I will display all users
     */
    public static void getMostFollowedUsers(String[] strings) {
        Integer[] numberOfFollowersPerUser = new Integer[App.users.length];
        for (int index = 0; index < App.users.length; index++)
            numberOfFollowersPerUser[index] = 0;

        for (int index = 0; index < App.users.length; index++) {
            for (int index1 = 0; index1 < App.users[index].getFollowingUsers().length; index1++) {
                String usernameFollowed = App.users[index].getFollowingUsers()[index1];
                for (int index2 = 0; index2 < App.users.length; index2++) {
                    if (App.users[index2].getUsername().equals(usernameFollowed)) {
                        numberOfFollowersPerUser[index2]++;
                        break;
                    }
                }
            }
        }

        String stringToPrint = "{'status':'ok','message': [";
        for (int index1 = 0; index1 < Math.min(numberOfFollowersPerUser.length, 5); index1++) {
            int Max = -1, indexMax = -1;
            for (int index = 0; index < numberOfFollowersPerUser.length; index++) {
                if (numberOfFollowersPerUser[index] > Max) {
                    Max = numberOfFollowersPerUser[index];
                    indexMax = index;
                }
            }
            
            Utilizator userToPrint = App.users[indexMax];
            stringToPrint = stringToPrint + "{'username':'" + userToPrint.getUsername();
            stringToPrint = stringToPrint + "','number_of_followers':'" + numberOfFollowersPerUser[indexMax] + "'},";
            numberOfFollowersPerUser[indexMax] = -1; // To ignore currentMax at the next step (next index1)
        }

        stringToPrint = stringToPrint.substring(0, stringToPrint.length() - 1);
        stringToPrint = stringToPrint + " ]}";
        System.out.print(stringToPrint);
    }

    public static void getMostLikedUsers(String[] strings) {
        Integer[] numberOfLikesAndComments = new Integer[App.users.length];
        for (int index = 0; index < App.users.length; index++)
            numberOfLikesAndComments[index] = 0;

        // Store the number of likes from posts
        for (int index = 0; index < App.posts.length; index++) {
            Postare post = App.posts[index];
            String username = post.getUsername();
            for (int index1 = 0; index1 < App.users.length; index1++) {
                if (App.users[index1].getUsername().equals(username)) {
                    if (post.getUsersThatLiked() != null)
                        numberOfLikesAndComments[index1] += post.getUsersThatLiked().length;
                }
            }
        }

        // Store the number of likes from comments
        for (int index = 0; index < App.comments.length; index++) {
            Comentariu comment = App.comments[index];
            String username = comment.getUsername();
            for (int index1 = 0; index1 < App.comments.length; index1++) {
                if (App.comments[index1].getUsername().equals(username)) {
                    if (comment.getUsersThatLiked() != null) {
                        numberOfLikesAndComments[index1] += comment.getUsersThatLiked().length;
                    }
                }
            }
        }

        // Analogous to other functions where I worked with the occurrence vector
        String stringToPrint = "{'status':'ok','message': [";
        for (int index1 = 0; index1 < Math.min(numberOfLikesAndComments.length, 5); index1++) {
            int Max = -1, indexMax = -1;
            for (int index = 0; index < numberOfLikesAndComments.length; index++) {
                if (numberOfLikesAndComments[index] > Max) {
                    Max = numberOfLikesAndComments[index];
                    indexMax = index;
                }
            }
            
            Utilizator userToPrint = App.users[indexMax];
            stringToPrint = stringToPrint + "{'username':'" + userToPrint.getUsername();
            stringToPrint = stringToPrint + "','number_of_likes':'" + numberOfLikesAndComments[indexMax] + "'},";
            numberOfLikesAndComments[indexMax] = -1; // To ignore currentMax at the next step (next index1)
        }

        stringToPrint = stringToPrint.substring(0, stringToPrint.length() - 1);
        stringToPrint = stringToPrint + "]}";
        System.out.print(stringToPrint);
    }
}
