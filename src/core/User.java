/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

//import com.google.common.collect.TreeMultiset;
import exceptions.UserAlreadyFollowedException;
import exceptions.UserIsNotFollowedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 *
 * @author helio
 */
public class User implements Comparable<User> {
    
    private String username;
    private LinkedList<User> following;
    private LinkedList<User> followers;
    private LinkedList<Message> messages;

    public User(String username) {
        this.username = username;
        this.following = new LinkedList<User>();
        this.followers = new LinkedList<User>(); //garante remoção eficiente
        this.messages =  new LinkedList<Message>(); 
    }

    
    @Override
    public int compareTo(User t) {
        return this.username.compareTo(t.getUsername());
    }

    /**
     * @return the username
     */
    public String getUsername() {
        
        return username;
    }

    public ArrayList<String> listFollowers(){
        
        ArrayList<String> list = new ArrayList<String>();
        for (User u: followers){
            list.add(u.getUsername());
        }
        return list;
        
    }
    
    public ArrayList<String> listFollowing(){
        
        ArrayList<String> list = new ArrayList<String>();
        for (User u: following){
            list.add(u.getUsername());
        }
        return list;
        
    }
    
    public ArrayList<String> listMessages() {
        
        ArrayList<String> list = new ArrayList<String>();
        for (Message m: messages){
            list.add(m.getMessage_text());
        }
        return list;
        
    }
    
   
    public ArrayList<String> listFollowingMessages(){
        
        TreeSet<Message> sorted = new TreeSet<Message>();
        for (User u: following){
            sorted.addAll(u.messages); //O(n.logn)
        }
        
        ArrayList<String> ret = new ArrayList<String>(); 
        for (Message m: sorted){
            ret.add(m.getAuthor().getUsername()+" "+m.getMessage_text());
        }
        return ret;
        
    }
    
    public HashMap<String,Integer> listStatistics() {
        
        HashMap<String, Integer> statistics = new HashMap<String, Integer>();
        statistics.put("Messages", messages.size());
        statistics.put("Followers", followers.size());
        statistics.put("Following", following.size());
        
        return statistics;
        
    }
    
    
    public synchronized void postMessage(Message message) {
        messages.addFirst(message);
    }

    public synchronized void follow(User other_user) throws UserAlreadyFollowedException {
        if (isFollowing(other_user)){
            throw new UserAlreadyFollowedException();
        }
        following.addFirst(other_user);
        other_user.addFollower(this);
        
    }
    
    public synchronized void unfollow(User other_user) throws UserIsNotFollowedException {
        if (!isFollowing(other_user)){
            throw new UserIsNotFollowedException();
        }
        following.remove(other_user);
        other_user.removeFollower(this);
    }

    private synchronized void addFollower(User other_user) {
        followers.addFirst(other_user);
    }
    
    private synchronized void removeFollower(User other_user) {
        followers.remove(other_user);
    }
    
    private boolean isFollowing(User other_user) {
        return following.contains(other_user);
    }

    
    
    
}
