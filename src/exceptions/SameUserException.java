/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class SameUserException extends Exception {

    /**
     * Creates a new instance of <code>SameUserException</code> without detail message.
     */
    public SameUserException() {
    }

    /**
     * Constructs an instance of <code>SameUserException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SameUserException(String msg) {
        super(msg);
    }
}
