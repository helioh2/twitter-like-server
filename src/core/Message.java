/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author helio
 */
public class Message implements Comparable<Message>{
    
    private String message_text;
    private User author;
    private Date time;

    public Message(String message_text, User author) {
        this.message_text = message_text;
        this.author = author;
        time = new Date();
    }

    
    
    /**
     * @return the message_text
     */
    public String getMessage_text() {
        return message_text;
    }


    /**
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    @Override
    public int compareTo(Message t) {
        int time_comp = this.time.compareTo(t.time);
        if (time_comp != 0){ //muito provavel
            return 0-time_comp; //tempo maior deve sempre ser listado antes de tempo menor
        }
        int message_comp = this.message_text.compareTo(t.message_text);
        if (message_comp != 0){ 
            return message_comp; 
        }
        //else
        return author.compareTo(t.author); //há o caso raro onde dois usuarios postam ao mesmo tempo a mesma mensagem
    }
    
    
    
    
     public ArrayList<String> extractHashTags() {
        
        ArrayList<String> ret = new ArrayList<String>();
        
        if (message_text.startsWith("#")){//pegar hashtag se for primeira palavra
            String tag = message_text.split(" ")[0];
            ret.add(removePunctuation(tag));
        }
        
        message_text = message_text.replaceAll("[\\(\\)\"]", " "); //troca parenteses e  aspas por espaços
        
        String[] subs = message_text.split(" #"); //separa string onde aparece espaço em branco + #
        for (int i = 1; i<subs.length; i++){ //do segundo pra frente, pois o primeiro nao tinha o #
            
            String tag = "#"+subs[i].split(" ")[0];  //separa a substring onde tem espaço 
            //em branco pra pegar só a palavra da hashtag
            ret.add(removePunctuation(tag));                                                  
        }
        
        return ret;
        
    }
    
    /**
     * Remove pontuações do final da hashtag.
     * @param tag
     * @return 
     */
    private String removePunctuation(String tag) {
        for (int i = tag.length()-1; i>=1; i--){ //de traz pra frente, vai eliminando a pontuação. ex: ".", "!!!", "?!!"
            if (tag.charAt(i) == 46 || tag.charAt(i) == 33 
                    || (tag.charAt(i)>=58 && tag.charAt(i)<=63)
                    || tag.charAt(i) == 44){
                tag = tag.substring(0, tag.length()-1);
            } else break;
        }
        return tag;
    }
}
