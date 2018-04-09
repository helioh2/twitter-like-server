/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class UserAlreadyFollowedException extends Exception {

    /**
     * Creates a new instance of <code>UserAlreadyFollowedException</code> without detail message.
     */
    public UserAlreadyFollowedException() {
    }

    /**
     * Constructs an instance of <code>UserAlreadyFollowedException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UserAlreadyFollowedException(String msg) {
        super(msg);
    }
}
