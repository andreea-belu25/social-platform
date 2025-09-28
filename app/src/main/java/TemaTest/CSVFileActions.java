package TemaTest;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileActions {
    // Access the path where one of the three created files is located
    static String usersPath = System.getProperty("user.dir") + "/users.csv";
    static File usersFile = new File(usersPath);
    static String postsPath = System.getProperty("user.dir") + "/posts.csv";
    static File postsFile = new File(postsPath);
    static String commentsPath = System.getProperty("user.dir") + "/comments.csv";
    static File commentsFile = new File(commentsPath);

    // Function for writing to file
    public static void write(String args, File file) throws IOException {
        FileWriter write = new FileWriter(file, true);
        write.append(args);
        write.append("\n");
        write.close();
    }

    public static String doesUserExist(String user) throws IOException {
        usersFile.createNewFile(); // Create file if it doesn't exist
        Scanner inputFile = new Scanner(usersFile);
        
        while (inputFile.hasNext()) {
            String stringFile = inputFile.next();
            String[] data = stringFile.split(",");
            
            if (data[0].equals(user)) {
                inputFile.close();
                return data[1];
            }
        }
        
        inputFile.close();
        return null;
    }

    // Having the users file, form a vector with users from the file which I return
    public static Utilizator[] users() {
        Utilizator[] users = new Utilizator[0];
        
        try {
            CSVFileActions.usersFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.usersFile);
            
            while (inputFile.hasNext()) {
                String stringFile = inputFile.nextLine();
                String[] data = stringFile.split(",");
                String username = data[0];
                String password = data[1];
                String[] followingUsers;
                
                if (data.length == 3)  // data[2] == null, data cannot be indexed like this => exception
                    followingUsers = data[2].split("#");
                else
                    followingUsers = null;
                
                Utilizator user = new Utilizator(username, password, followingUsers);
                Utilizator[] usersCopy = new Utilizator[users.length + 1];
                
                int index = 0;
                for (index = 0; index < users.length; index++) {
                    usersCopy[index] = users[index];
                }
                usersCopy[index] = user;
                users = usersCopy;
            }
            
            inputFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        return users;
    }

    // Having the posts file, form a vector with posts from the file which I return
    public static Postare[] posts() {
        Postare[] posts = new Postare[0];
        
        try {
            CSVFileActions.postsFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.postsFile);
            
            while (inputFile.hasNext()) {
                String stringFile = inputFile.nextLine();
                String[] data = stringFile.split(",");
                String username = data[0];
                String postText = data[1];
                String datePost = data[2];
                String[] usersThatLiked;
                
                if (data.length == 4)
                    usersThatLiked = data[3].split("#");
                else
                    usersThatLiked = null;
                
                Postare post = new Postare(username, postText, datePost, usersThatLiked, posts.length + 1);
                Postare[] postsCopy = new Postare[posts.length + 1];
                
                int index = 0;
                for (index = 0; index < posts.length; index++) {
                    postsCopy[index] = posts[index];
                }
                postsCopy[index] = post;
                posts = postsCopy;
            }
            
            inputFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        return posts;
    }

    // Having the comments file, form a vector with comments from the file which I return
    public static Comentariu[] comments() {
        Comentariu[] comments = new Comentariu[0];
        
        try {
            CSVFileActions.commentsFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.commentsFile);
            
            while (inputFile.hasNext()) {
                String stringFile = inputFile.nextLine();
                String[] data = stringFile.split(",");
                String postId = data[0];
                String username = data[1];
                String commentText = data[2];
                String dateComment = data[3];
                String[] usersThatLiked;
                
                if (data.length == 5)
                    usersThatLiked = data[4].split("#");
                else
                    usersThatLiked = null;
                
                Comentariu comment = new Comentariu(postId, username, commentText, dateComment, usersThatLiked);
                Comentariu[] commentsCopy = new Comentariu[comments.length + 1];
                
                int index = 0;
                for (index = 0; index < comments.length; index++) {
                    commentsCopy[index] = comments[index];
                }
                commentsCopy[index] = comment;
                comments = commentsCopy;
            }
            
            inputFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        
        return comments;
    }

    // Print users to specific file
    public static void printUsers(Utilizator[] users) {
        for (Utilizator user : users) {
            try {
                write(user.toString(), usersFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    // Print posts to specific file
    public static void printPosts(Postare[] posts) {
        for (Postare post : posts) {
            try {
                write(post.toString(), postsFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    // Print comments to specific file
    public static void printComments(Comentariu[] comments) {
        for (Comentariu comment : comments) {
            try {
                write(comment.toString(), commentsFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
