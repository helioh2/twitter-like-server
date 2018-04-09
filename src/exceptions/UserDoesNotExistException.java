/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class UserDoesNotExistException extends Exception {

    /**
     * Creates a new instance of <code>UserDoesNotExistException</code> without detail message.
     */
    public UserDoesNotExistException() {
    }

    /**
     * Constructs an instance of <code>UserDoesNotExistException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UserDoesNotExistException(String msg) {
        super(msg);
    }
}
