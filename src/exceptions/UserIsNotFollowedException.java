/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class UserIsNotFollowedException extends Exception {

    /**
     * Creates a new instance of <code>UserIsNotFollowedException</code> without detail message.
     */
    public UserIsNotFollowedException() {
    }

    /**
     * Constructs an instance of <code>UserIsNotFollowedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UserIsNotFollowedException(String msg) {
        super(msg);
    }
}
