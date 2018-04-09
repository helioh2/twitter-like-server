/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class HashTagDoesNotExistsException extends Exception {

    /**
     * Creates a new instance of <code>HashTagDoesNotExistsException</code> without detail message.
     */
    public HashTagDoesNotExistsException() {
    }

    /**
     * Constructs an instance of <code>HashTagDoesNotExistsException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public HashTagDoesNotExistsException(String msg) {
        super(msg);
    }
}
