package TemaTest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class Comentariu implements Likeable {
    private String postId;
    private String username;
    private String commentText;
    private String dateComment;
    private String[] usersThatLiked;

    public Comentariu(String postId, String username, String commentText, String dateComment, String[] usersThatLiked) {
        this.postId = postId;
        this.username = username;
        this.commentText = commentText;
        this.dateComment = dateComment;
        this.usersThatLiked = usersThatLiked;
    }

    public Comentariu(String postId, String username, String commentText, String dateComment) {
        this.postId = postId;
        this.username = username;
        this.commentText = commentText;
        this.dateComment = dateComment;
    }

    public String getPostId() {
        return postId;
    }

    public String getUsername() {
        return username;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getDateComment() {
        return dateComment;
    }

    public String[] getUsersThatLiked() {
        return usersThatLiked;
    }

    public static Comentariu doesCommentExist(String commentId) {
        for (int index = 0; index < App.comments.length; index++) {
            if (index == Integer.parseInt(commentId) - 1)
                return App.comments[index];
        }
        return null;
    }

    public boolean isPostCommentedByUser(String username) {
        if (this.username.equals(username))
            return true;
        return false;
    }

    public boolean isAlreadyLiked(String username) {
        if (this.usersThatLiked != null) {
            for (int index = 0; index < this.usersThatLiked.length; index++) {
                if (this.usersThatLiked[index].equals(username))
                    return true;
            }
        }
        return false;
    }

    public static void deleteUserComment(Integer commentId) {
        Comentariu[] commentsCopy = new Comentariu[App.comments.length - 1];
        int k = 0;
        
        for (int index = 0; index < App.comments.length; index++) {
            if (index != commentId)
                commentsCopy[k++] = App.comments[index];
        }
        
        App.comments = commentsCopy;
    }

    public void addUsernameThatLiked(String username) {
        if (this.usersThatLiked != null) {
            String[] usersThatLikedCommentCopy = new String[this.usersThatLiked.length + 1];
            int index = 0;
            
            for (index = 0; index < this.usersThatLiked.length; index++) {
                usersThatLikedCommentCopy[index] = this.usersThatLiked[index];
            }
            
            usersThatLikedCommentCopy[index] = username;
            this.usersThatLiked = usersThatLikedCommentCopy;
        } else {  // First username added
            String[] usersThatLikedCommentCopy = new String[1];
            usersThatLikedCommentCopy[0] = username;
            this.usersThatLiked = usersThatLikedCommentCopy;
        }
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
            finalUser = this.postId + "," + this.username + "," + this.commentText + "," + this.dateComment + "," + finalUsersThatLiked;
        else
            finalUser = this.postId + "," + this.username + "," + this.commentText + "," + this.dateComment;
            
        return finalUser;
    }

    public static void addNewComment(Comentariu newComment) {
        Comentariu[] commentsCopy = new Comentariu[App.comments.length + 1];
        int index = 0;
        
        for (index = 0; index < App.comments.length; index++) {
            commentsCopy[index] = App.comments[index];
        }
        
        commentsCopy[index] = newComment;
        App.comments = commentsCopy;
    }

    public boolean isUnliked(String username) {
        if (this.usersThatLiked != null) {
            for (int index = 0; index < this.usersThatLiked.length; index++) {
                if (this.usersThatLiked[index].equals(username))
                    return false;
            }
        }
        return true;
    }

    public void deleteUsernameThatLiked(String username) {
        String[] usersThatLikedCommentCopy = new String[this.usersThatLiked.length - 1];
        int k = 0;
        
        for (int index = 0; index < this.usersThatLiked.length; index++) {
            if (!this.usersThatLiked[index].equals(username))
                usersThatLikedCommentCopy[k++] = this.usersThatLiked[index];
        }
        
        this.usersThatLiked = usersThatLikedCommentCopy;
    }

    public int compareTo(Comentariu comentariu) {
        // Compare two comments by their posting dates
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        try {
            Date thisDate = dateFormat.parse(this.dateComment);
            Date commentDate = dateFormat.parse(comentariu.getDateComment());
            int result = thisDate.compareTo(commentDate);
            int indexComment = 0, thisIndex = 0;
            
            if (result != 0)
                return result;
                
            for (int index = 0; index < App.comments.length; index++) {
                if (App.comments[index] == comentariu)
                    indexComment = index;
                if (App.comments[index] == this)
                    thisIndex = index;
            }
            
            return indexComment - thisIndex;
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
        
        return 0;
    }

    public static void likeComment(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No comment identifier to like was provided'}");
            return;
        }

        String commentId = App.toString(App.extractParameterValue(strings[3]));
        Comentariu commentToLike = doesCommentExist(commentId);
        if (commentToLike == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[1]));
        if (commentToLike != null && commentToLike.getUsername().equals(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
            return;
        }

        if (commentToLike != null && commentToLike.isAlreadyLiked(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to like was not valid'}");
            return;
        }

        if (commentToLike != null)
            commentToLike.addUsernameThatLiked(username);
            
        System.out.print("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void unlikeComment(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No comment identifier to unlike was provided'}");
            return;
        }

        String commentId = App.toString(App.extractParameterValue(strings[3]));
        Comentariu commentToUnlike = doesCommentExist(commentId);
        if (commentToUnlike == null) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to unlike was not valid'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[1]));
        if (commentToUnlike != null && commentToUnlike.getUsername().equals(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to unlike was not valid'}");
            return;
        }

        if (commentToUnlike != null && commentToUnlike.isUnliked(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The comment identifier to unlike was not valid'}");
            return;
        }

        if (commentToUnlike != null)
            commentToUnlike.deleteUsernameThatLiked(username);
            
        System.out.print("{'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void commentPost(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No text provided'}");
            return;
        }

        String text = App.toString(App.extractParameterValue(strings[4]));
        if (text.length() > 300) {
            System.out.println("{ 'status' : 'error', 'message' : 'Comment text length exceeded'}");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  // Generate comment date
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);
        String username = App.toString(App.extractParameterValue(strings[1]));
        String postId = App.toString(App.extractParameterValue(strings[3]));
        
        if (Postare.doesPostExist(postId) == null)
            return;
            
        Comentariu newComment = new Comentariu(postId, username, text, currentDateAsString);
        addNewComment(newComment);
        System.out.println("{ 'status' : 'ok', 'message' : 'Comment added successfully'}");
    }

    public static void deleteComment(String[] strings) {
        if (strings.length == 3) {
            System.out.print("{ 'status' : 'error', 'message' : 'No identifier was provided'}");
            return;
        }

        String commentId = App.toString(App.extractParameterValue(strings[3]));
        if (App.comments.length < Integer.parseInt(commentId)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            return;
        }

        String username = App.toString(App.extractParameterValue(strings[1]));
        Comentariu commentToDelete = doesCommentExist(commentId);
        if (commentToDelete == null)
            return;
            
        if (!commentToDelete.isPostCommentedByUser(username)) {
            System.out.print("{ 'status' : 'error', 'message' : 'The identifier was not valid'}");
            return;
        }
        
        deleteUserComment(Integer.parseInt(commentId) - 1);
        System.out.println("{ 'status' : 'ok', 'message' : 'Operation executed successfully'}");
    }

    public static void getMostCommentedPosts(String[] strings) {
        // Occurrence vector for number of comments from each post
        Integer[] numberOfComments = new Integer[App.comments.length];
        for (int index = 0; index < App.comments.length; index++)
            numberOfComments[index] = 0;

        for (int index = 0; index < App.comments.length; index++) {
            numberOfComments[Integer.parseInt(App.comments[index].getPostId()) - 1]++;
        }

        // Display 5 or the length of the most commented posts occurrence vector
        String stringToPrint = "{'status':'ok','message': [";
        for (int index1 = 0; index1 < Math.min(numberOfComments.length, 5); index1++) {
            int Max = -1, indexMax = -1;
            
            for (int index = 0; index < numberOfComments.length; index++) {
                if (numberOfComments[index] > Max) {
                    Max = numberOfComments[index];
                    indexMax = index;
                }
            }

            Postare postToPrint = App.posts[indexMax];
            stringToPrint = stringToPrint + "{'post_id':'" + (indexMax + 1);
            stringToPrint = stringToPrint + "','post_text':'" + postToPrint.getPostText();
            stringToPrint = stringToPrint + "','post_date':'" + postToPrint.getDatePost();
            stringToPrint = stringToPrint + "','username':'" + postToPrint.getUsername();
            stringToPrint = stringToPrint + "','number_of_comments':'" + numberOfComments[indexMax] + "'},";
            numberOfComments[indexMax] = -1; // To ignore currentMax at next step (next index1)
        }

        // Treat last element separately due to final braces
        stringToPrint = stringToPrint.substring(0, stringToPrint.length() - 1);
        stringToPrint = stringToPrint + "]}";
        System.out.print(stringToPrint);
    }
}
