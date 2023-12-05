package TemaTest;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileActions{
    //  accesarea path-ului la care se afla unul dintre cele trei fisiere create
    static String usersPath = System.getProperty("user.dir") + "/users.csv";
    static File usersFile = new File(usersPath);
    static String postsPath = System.getProperty("user.dir") + "/posts.csv";
    static File postsFile = new File(postsPath);
    static String commentsPath = System.getProperty("user.dir") + "/comments.csv";
    static File commentsFile = new File(commentsPath);

    //  functie pentru scriere in fisier
    public static void write(String args, File file) throws IOException {
        FileWriter write = new FileWriter(file, true);
        write.append(args);
        write.append("\n");

        write.close();
    }

    public static String doesUserExist(String user) throws IOException {
        usersFile.createNewFile(); // creaza fisierul daca nu exista
        Scanner inputFile = new Scanner(usersFile);
        while(inputFile.hasNext()) {
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

    //  avand fisierul de users, formez un vector cu userii din fisier pe care il returnez
    public static Utilizator[] users() {
        Utilizator[] users = new Utilizator[0];
        try {
            CSVFileActions.usersFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.usersFile);
            while(inputFile.hasNext()) {
                String stringFile = inputFile.nextLine();
                String[] data = stringFile.split(",");
                String username = data[0];
                String password = data[1];
                String[] followingUsers;
                if (data.length == 3)  // data[2] == null, data nu poate fi indexat asa => exceptie
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

    //  avand fisierul de posts, formez un vector cu posts-urile din fisier pe care il returnez
    public static Postare[] posts() {
        Postare[] posts = new Postare[0];
        try {
            CSVFileActions.postsFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.postsFile);
            while(inputFile.hasNext()) {
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

    //  avand fisierul de comments, formez un vector cu comments-urile din fisier pe care il returnez
    public static Comentariu[] comments() {
        Comentariu[] comments = new Comentariu[0];
        try {
            CSVFileActions.commentsFile.createNewFile();
            Scanner inputFile = new Scanner(CSVFileActions.commentsFile);
            while(inputFile.hasNext()) {
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

    //  printez userii in fisierul specific
    public static void printUsers(Utilizator[] users) {
        for (Utilizator user: users) {
           try {
               write(user.toString(), usersFile);
           } catch (IOException exception) {
                exception.printStackTrace();
           }
        }
    }

    //  printez postarile in fisierul specific
    public static void printPosts(Postare[] posts) {
        for (Postare post: posts) {
            try {
                write(post.toString(), postsFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    //  printez comentariile in fisierul specific
    public static void printComments(Comentariu[] comments) {
        for (Comentariu comment: comments) {
            try {
                write(comment.toString(), commentsFile);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
