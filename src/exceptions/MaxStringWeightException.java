/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author helio
 */
public class MaxStringWeightException extends Exception {

    /**
     * Creates a new instance of <code>MaxStringWeightException</code> without detail message.
     */
    public MaxStringWeightException() {
    }

    /**
     * Constructs an instance of <code>MaxStringWeightException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public MaxStringWeightException(String msg) {
        super(msg);
    }
}
