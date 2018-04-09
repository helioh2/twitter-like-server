/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class InvalidUsernameException extends Exception {

    /**
     * Creates a new instance of <code>InvalidUsernameException</code> without detail message.
     */
    public InvalidUsernameException() {
    }

    /**
     * Constructs an instance of <code>InvalidUsernameException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidUsernameException(String msg) {
        super(msg);
    }
}
