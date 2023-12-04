package TemaTest;

import java.io.IOException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Comparator;
import java.text.ParseException;
public class Postare implements Likeable, Comparable<Postare>{
    private String username;
    private String postText;
    private String datePost;
    private int postId;
    private String[] usersThatLiked;

    public Postare(String username, String postText, String datePost, String[] usersThatLiked, int postId) {
        this.username = username;
        this.postText = postText;
        this.datePost = datePost;
        this.usersThatLiked = usersThatLiked;
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public String getPostText() {
        return postText;
    }

    public String getDatePost() {
        return datePost;
    }

    public String[] getUsersThatLiked() {
        return usersThatLiked;
    }

    public int getPostId() {
        return postId;
    }

    public static Postare doesPostExist (String postId) { // nu o fac static pentru ca apartine de obiectul din clasa curenta
            for (int index = 0; index < App.posts.length; index++) {
                if (index == Integer.parseInt(postId) - 1)
                    return App.posts[index];
            }
        return null;
    }
    public boolean isAlreadyLiked (String username) { // nu o fac static pentru ca apartine de obiectul din clasa curenta
        if (this.usersThatLiked != null) {
            for (int index = 0; index < this.usersThatLiked.length; index++) {
                if (this.usersThatLiked[index].equals(username))
                    return true;
            }
        }
        return false;
    }

    public void addUsernameThatLiked (String username) {
        if (this.usersThatLiked != null) {
            String[] usersThatLikedCopy = new String[this.usersThatLiked.length + 1];
            int index = 0;
            for (index = 0; index < this.usersThatLiked.length; index++) {
                usersThatLikedCopy[index] = this.usersThatLiked[index];
            }
            usersThatLikedCopy[index] = username;
            this.usersThatLiked = usersThatLikedCopy;
        } else {
            String[] usersThatLikedCopy = new String[1];
            usersThatLikedCopy[0] = username;
            this.usersThatLiked = usersThatLikedCopy;
        }
    }

    public boolean isUnliked (String username) { // nu o fac static pentru ca apartine de obiectul din clasa curenta
        if (this.usersThatLiked != null) {
            for (int index = 0; index < this.usersThatLiked.length; index++) {
                if (this.usersThatLiked[index].equals(username))
                    return false;
            }
        }
        return true;
    }

    public void deleteUsernameThatLiked(String username) {
        String[] usersThatLikedCopy = new String[this.usersThatLiked.length - 1];
        int k = 0;
        for (int index = 0; index < this.usersThatLiked.length; index++) {
            if (!this.usersThatLiked[index].equals(username))
                usersThatLikedCopy[k++] = this.usersThatLiked[index];
        }
        this.usersThatLiked = usersThatLikedCopy;
    }

    public static void addNewPost(Postare newPost) {
        Postare[] postsCopy = new Postare[App.posts.length + 1];
        int index = 0;
        for (index = 0; index < App.posts.length; index++) {
            postsCopy[index] = App.posts[index];
        }
        postsCopy[index] = newPost;
        App.posts = postsCopy;
    }

    public static void deleteUserPost(Integer postId) {
        Postare[] postsCopy = new Postare[App.posts.length - 1];
        int k = 0;
        for (int index = 0; index < App.posts.length; index++) {
            if (index != postId)
                postsCopy[k++] = App.posts[index];
        }
        App.posts = postsCopy;
    }

    public String toString() {
        String finalUsersThatLiked = "";
        if (this.usersThatLiked != null && this.usersThatLiked.length > 0) {
            int index = 0;
            for (index = 0; index < this.usersThatLiked.length - 1; index++) {
                finalUsersThatLiked = finalUsersThatLiked + this.usersThatLiked[index];
                finalUsersThatLiked = finalUsersThatLiked + "#";
            }
            finalUsersThatLiked = finalUsersThatLiked + this.usersThatLiked[index];
        }
        String finalUser = "";
        if (!finalUsersThatLiked.isEmpty())
            finalUser = this.username + "," + this.postText + "," + this.datePost + "," + finalUsersThatLiked;
        else
            finalUser = this.username + "," + this.postText + "," + this.datePost;
        return finalUser;
    }

    public static Date getDate(String postString) {
        String dateString = postString.split(",")[2].trim(); // Extracting the date part
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static Comentariu[] addCommentToAPost (String postId) {
        Comentariu[] commentsPost = new Comentariu[0];
        for (int index1 = 0; index1 < App.comments.length; index1++) {
            if (App.comments[index1].getPostId().equals(postId)) {
                Comentariu[] commentsCopy = new Comentariu[commentsPost.length + 1];
                int index2 = 0;
                for (index2 = 0; index2 < commentsPost.length; index2++) {
                    commentsCopy[index2] = commentsPost[index2];
                }
                commentsCopy[index2] = App.comments[index1];
                commentsPost = commentsCopy;
            }
        }
        return commentsPost;
    }

    public int compareTo(Postare postare) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date thisDate = dateFormat.parse(this.datePost);
            Date postDate = dateFormat.parse(postare.datePost);
            int result = thisDate.compareTo(postDate);
            int indexPost = 0, thisIndex = 0;
            if (result != 0)
                return result;
            for (int index = 0; index < App.posts.length; index++) {
                if (App.posts[index] == postare)
                    indexPost = index;
                if (App.posts[index] == this)
                    thisIndex = index;
            }
            return indexPost - thisIndex;
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public static void createPost(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No text provided'}");
            return;
        }

        String text = App.toString(App.extractParameterValue(strings[3]));
        if(text.length() > 300) {
            System.out.print("{ 'status' : 'error', 'message' : 'Post text length exceeded'}");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);
        String username = App.toString(App.extractParameterValue(strings[1]));
        Postare newPost = new Postare(username, text, currentDateAsString, null, App.posts.length + 1);
        addNewPost(newPost);
        System.out.print("{ 'status' : 'ok', 'message' : 'Post added successfully'}");
    }

    public static void deletePost(String[] strings) {
        if(strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No identifier was provided'}");
            return;
        }

        String str = App.toString(App.extractParameterValue(strings[3]));
        Integer postId = Integer.valueOf(str);
        if (App.posts.length < postId) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            return;
        }
        deleteUserPost(postId - 1);
        System.out.print("{'status' : 'ok', 'message' : 'Post deleted successfully'}");
    }

    public static void likePost(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No post identifier to like was provided'}");
            return;
        }

        String postId = App.toString(App.extractParameterValue(strings[3]));
        Postare postToLike = doesPostExist(postId);
        if (postToLike == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[1]));
        if (postToLike != null && postToLike.getUsername().equals(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }

        if (postToLike != null && postToLike.isAlreadyLiked(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to like was not valid'}");
            return;
        }

        if (postToLike != null)
            postToLike.addUsernameThatLiked(username);
        System.out.print("{'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void unlikePost(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No post identifier to unlike was provided'}");
            return;
        }

        String postId = App.toString(App.extractParameterValue(strings[3]));
        Postare postToUnlike = doesPostExist(postId);
        if (postToUnlike == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[1]));
        if (postToUnlike != null && postToUnlike.getUsername().equals(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }

        if (postToUnlike != null && postToUnlike.isUnliked(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier to unlike was not valid'}");
            return;
        }

        if (postToUnlike != null)
            postToUnlike.deleteUsernameThatLiked(username);
        System.out.print("{'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void getPostDetails(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No post identifier was provided'}");
            return;
        }

        String postId = App.toString(App.extractParameterValue(strings[3]));
        int nr = 0;
        for (int index = 0; index < App.posts.length; index++) {
            if (index != Integer.parseInt(postId) - 1)
                nr++;
        }

        if (nr == App.posts.length) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier was not valid'}");
            return;
        }

        Comentariu[] commentsPost = addCommentToAPost(postId);
        Arrays.sort(commentsPost);

        Postare post = App.posts[Integer.parseInt(postId) - 1];
        String stringToPrint = "{'status':'ok','message': [{'post_text':'";
        stringToPrint = stringToPrint + post.getPostText() + "','post_date':'" + post.getDatePost();
        if (post.getUsersThatLiked() != null)
            stringToPrint = stringToPrint + "','username':'" + post.getUsername() + "','number_of_likes':'" + post.getUsersThatLiked().length;
        else
            stringToPrint = stringToPrint + "','username':'" + post.getUsername() + "','number_of_likes':'" + "0";
        stringToPrint = stringToPrint + "','comments': [{'comment_id':'";

        for (int index = 0; index < commentsPost.length - 1; index++) {
            stringToPrint = stringToPrint + commentsPost[index].getPostId() + "','comment_text':'" + commentsPost[index].getCommentText();
            stringToPrint = stringToPrint + "','comment_date':'" + commentsPost[index].getDateComment() + "','username':'" + commentsPost[index].getUsername();
            if (commentsPost[index].getUsersThatLiked() != null)
                stringToPrint = stringToPrint + "','number_of_likes':'" + commentsPost[index].getUsersThatLiked().length;
            else
                stringToPrint = stringToPrint + "','number_of_likes':'" + "0";
            stringToPrint = stringToPrint + "'}] ";
        }

        /// last element
        stringToPrint = stringToPrint + commentsPost[commentsPost.length - 1].getPostId() + "','comment_text':'" + commentsPost[commentsPost.length - 1].getCommentText();
        stringToPrint = stringToPrint + "','comment_date':'" + commentsPost[commentsPost.length - 1].getDateComment() + "','username':'" + commentsPost[commentsPost.length - 1].getUsername();
        if (commentsPost[commentsPost.length - 1].getUsersThatLiked() != null)
            stringToPrint = stringToPrint + "','number_of_likes':'" + commentsPost[commentsPost.length - 1].getUsersThatLiked().length;
        else
            stringToPrint = stringToPrint + "','number_of_likes':'" + "0";
        stringToPrint = stringToPrint + "'}] }] }";
        System.out.println(stringToPrint);
    }

    public static void getMostLikedPosts(String[] strings) {
        Postare[] postsCopy = new Postare [App.posts.length];
        System.arraycopy(App.posts, 0, postsCopy, 0, App.posts.length);
        Arrays.sort(postsCopy, new Comparator<Postare>() {
            public int compare(Postare postare, Postare t1) {
                int lengthPostare = 0;
                int lengthT1 = 0;
                if (postare.getUsersThatLiked() != null)
                    lengthPostare = postare.getUsersThatLiked().length;
                if (t1.getUsersThatLiked() != null)
                    lengthT1 = t1.getUsersThatLiked().length;

                return lengthT1 - lengthPostare; //ordonare descrescatoare
            }
        });

        String stringToPrint = "{'status':'ok','message': [";
        int min = Math.min(postsCopy.length, 5) - 1;
        for (int index = 0; index < min; index++) {
            stringToPrint = stringToPrint + "{'post_id':'" + postsCopy[index].getPostId();
            stringToPrint = stringToPrint + "','post_text':'" + postsCopy[index].getPostText();
            stringToPrint = stringToPrint + "','post_date':'" + postsCopy[index].getDatePost();
            stringToPrint = stringToPrint + "','username':'" + postsCopy[index].getUsername();
            if (postsCopy[index].getUsersThatLiked() != null)
                stringToPrint = stringToPrint + "','number_of_likes':'" + postsCopy[index].getUsersThatLiked().length + "'},";
            else
                stringToPrint = stringToPrint + ",'number_of_likes':'" + "0" + "'},";

        }

        stringToPrint = stringToPrint + "{'post_id':'" + postsCopy[min].getPostId();
        stringToPrint = stringToPrint + "','post_text':'" + postsCopy[min].getPostText();
        stringToPrint = stringToPrint + "','post_date':'" + postsCopy[min].getDatePost();
        stringToPrint = stringToPrint + "','username':'" + postsCopy[min].getUsername();
        if (postsCopy[min].getUsersThatLiked() != null)
            stringToPrint = stringToPrint + "','number_of_likes':'" + postsCopy[min].getUsersThatLiked().length + "'} ]}";
        else
            stringToPrint = stringToPrint + "','number_of_likes':'" + "0" + "'} ]}";
        System.out.print(stringToPrint);
    }
}
