/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

//import com.google.common.collect.TreeMultiset;
import java.util.LinkedList;
//import java.util.TreeSet;

/**
 *
 * @author helio
 */
public class Hashtag implements Comparable<Hashtag> {
    
    private String value;
    private Integer occurences;
    private LinkedList<Message> messages;

    public Hashtag(String value) {
        this.value = value;
        occurences = 0;
        messages = new LinkedList<Message>();
    }
    
    @Override
    public String toString() {
        return value;
    }

    /**
     * Hashtag t1 é maior q t2 se: 
     * SE numero de ocorrencia é diferente: numero de ocorrências de t1 é maior que de t2 
     * OU SE num ocorrencias é igual: nome da string é menor
     * @param t
     * @return 
     */
    @Override
    public int compareTo(Hashtag t) {
        if (this.occurences.equals(t.occurences)){
            return 0-this.value.compareTo(t.value);
        }
        return this.occurences.compareTo(t.occurences);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof String){
//            return this.value.equals(o);
//        }
//        if (o instanceof Hashtag){
//            return this.value.equals(((Hashtag)o).value);
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 67 * hash + (this.value != null ? this.value.hashCode() : 0);
//        return hash;
//    }

    public void addMessage(Message msg) {
        occurences++; //incrementa mesmo qdo há duas ref à hashtag na mesma msg
        if (!messages.contains(msg)){ //trata caso em que uma mesma mensagem tem mais de um ref à mesma hashtag
            messages.addFirst(msg);
        }
    }
    

    public LinkedList<Message> getMessages() {
        return messages;
    }
    
    
    
    
    
}
