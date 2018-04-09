/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class FollowerDoesNotExistException extends Exception {

    /**
     * Creates a new instance of <code>FollowerDoesNotExistException</code> without detail message.
     */
    public FollowerDoesNotExistException() {
    }

    /**
     * Constructs an instance of <code>FollowerDoesNotExistException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FollowerDoesNotExistException(String msg) {
        super(msg);
    }
}
