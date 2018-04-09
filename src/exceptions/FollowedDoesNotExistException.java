/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class FollowedDoesNotExistException extends Exception {

    /**
     * Creates a new instance of <code>FollowingDoesNotExistException</code> without detail message.
     */
    public FollowedDoesNotExistException() {
    }

    /**
     * Constructs an instance of <code>FollowingDoesNotExistException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FollowedDoesNotExistException(String msg) {
        super(msg);
    }
}
