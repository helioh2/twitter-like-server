/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import core.Message;
import core.Hashtag;
import core.User;
import exceptions.FollowedDoesNotExistException;
import exceptions.FollowerDoesNotExistException;
import exceptions.HashTagDoesNotExistsException;
import exceptions.InvalidUsernameException;
import exceptions.MaxStringWeightException;
import exceptions.SameUserException;
import exceptions.UserAlreadyExistsException;
import exceptions.UserAlreadyFollowedException;
import exceptions.UserDoesNotExistException;
import exceptions.UserIsNotFollowedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author helio
 */
class SocialNetworkDatabase {
    
    private final HashMap<String,User> user_table;
    private final HashMap<String,Hashtag> hashtags;
    private final int MAX_MSG_SIZE = 140;
    
    public SocialNetworkDatabase() {
        user_table = new HashMap<String, User>();
        hashtags = new HashMap<String, Hashtag>();
    }
    
    public void addUser(String username) throws UserAlreadyExistsException, InvalidUsernameException{
        
        if (isUserRegistered(username)){
            throw new UserAlreadyExistsException();
        }
        if (username.length() > 20 || username.length() < 3){
            throw new InvalidUsernameException();
        }
        for (char c: username.toCharArray()){
            if (c < 65 || (c > 90 && c < 97) || c > 122){
                throw new InvalidUsernameException();
            }
        }
        synchronized (user_table){
            user_table.put(username, new User(username));
        }
    }
    
    public void postMessage(String username, String message) throws UserDoesNotExistException, MaxStringWeightException{
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        if (message.length() > MAX_MSG_SIZE){
            throw new MaxStringWeightException();
        }
        
        User user = user_table.get(username);
        Message msg = new Message(message, user);
        user.postMessage(msg);
        
        ArrayList<String> htonmsg = msg.extractHashTags();
        
        synchronized(hashtags){
            for (String tag: htonmsg){ //adicionar hashtags
                if (hashtags.containsKey(tag)){ 
                    Hashtag hashtag = hashtags.get(tag);
                    hashtag.addMessage(msg);             
                } else {
                    hashtags.put(tag, new Hashtag(tag));
                    hashtags.get(tag).addMessage(msg);
                }
            }      
        }
    }
    
    
    
    public ArrayList<String> listMessages(String username) throws UserDoesNotExistException{
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        return user_table.get(username).listMessages();
        
    }
    
    public void follow(String username1, String username2) throws UserDoesNotExistException, UserAlreadyFollowedException, FollowerDoesNotExistException, FollowedDoesNotExistException, SameUserException {
        
        if (!isUserRegistered(username1)){
            throw new FollowerDoesNotExistException();
        } else if (!isUserRegistered(username2)){
            throw new FollowedDoesNotExistException();
        } else if (username1.equals(username2)){
            throw new SameUserException();
        }
        
        User user1 = user_table.get(username1);
        User user2 = user_table.get(username2);
        user1.follow(user2);
        
    }
    
    public void unfollow(String username1, String username2) throws UserDoesNotExistException, UserIsNotFollowedException, FollowerDoesNotExistException, FollowedDoesNotExistException, SameUserException {
        
        if (!isUserRegistered(username1)){
            throw new FollowerDoesNotExistException();
        } else if (!isUserRegistered(username2)){
            throw new FollowedDoesNotExistException();
        } else if (username1.equals(username2)){
            throw new SameUserException();
        }
        
        User user1 = user_table.get(username1);
        User user2 = user_table.get(username2);
        user1.unfollow(user2);
        
    }
    
    public ArrayList<String> listFollowers(String username) throws UserDoesNotExistException {
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        return user_table.get(username).listFollowers();
        
    }
    
    public ArrayList<String> listFollowing(String username) throws UserDoesNotExistException {
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        return user_table.get(username).listFollowing();
    }
    
    public ArrayList<String> listFollowingMessages(String username) throws UserDoesNotExistException {
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        return user_table.get(username).listFollowingMessages(); 
        
    }
    
    public ArrayList<String> listStatistics(String username) throws UserDoesNotExistException {
        
        if (!isUserRegistered(username)){
            throw new UserDoesNotExistException();
        }
        
        ArrayList<String> ret = new ArrayList<String>();
        HashMap<String, Integer> statistics = user_table.get(username).listStatistics();
        
        ret.add(""+statistics.get("Messages"));
        ret.add(""+statistics.get("Following"));
        ret.add(""+statistics.get("Followers"));
        
        return ret;
        
    }
    
    public ArrayList<String> listTrendTopics() {
        
        ArrayList<String> ret = new ArrayList<String>();
        
        //ORDENAR lista <ANTIGO> USAVA QUICKSORT pra ordenar
//        ArrayList<Hashtag> sorted = new ArrayList<Hashtag>();
//        for (Hashtag tag: hashtags.values()){ //O(n)
//            sorted.add(tag);
//        }
//        util.Util.sortReversed(sorted);  //O(n²)
        //FIM ordenar
        
                
        //ORDENAR lista (usando treeset, inserção ordenada (O(n.logn))
        TreeSet<Hashtag> sorted = new TreeSet<Hashtag>();
        for (Hashtag tag: hashtags.values()){
            sorted.add(tag);
        }
        //FIM ordenar
        
        int i = 0;
        for (Iterator<Hashtag> iter = sorted.descendingIterator();
                iter.hasNext() && i<5; i++){
            ret.add(iter.next().toString());
        }
        return ret;
        
    }
    
    public ArrayList<String> listMessagesWithHashtag(String hashtagstr) throws HashTagDoesNotExistsException{
        
        if (!hashtags.containsKey(hashtagstr)){
            throw new HashTagDoesNotExistsException();
        }
        
        ArrayList<String> ret = new ArrayList<String>();
        Hashtag hashtag1 = hashtags.get(hashtagstr);
        for (Message m: hashtag1.getMessages()){
           ret.add(m.getAuthor().getUsername()+" "+m.getMessage_text());
        }
        
        return ret;
    }

    private /*synchronized*/ boolean isUserRegistered(String username) {
        return user_table.containsKey(username);
    }

    
    
    
    
}
