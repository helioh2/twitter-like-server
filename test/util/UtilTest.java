/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import javax.rmi.CORBA.Util;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author helio
 */
public class UtilTest {
    
    private ArrayList<String> lista1 = new ArrayList<String>();
    private ArrayList<String> lista1_ord = new ArrayList<String>();
    private ArrayList<String> lista1_ord_rev = new ArrayList<String>();
    
    public UtilTest() {
    }
    
    @Before
    public void setUp() {
        
        lista1.add("josé");
        lista1.add("maria");
        lista1.add("ana");
        lista1.add("pedro");
        lista1.add("marcos");
        
        lista1_ord.add("ana");
        lista1_ord.add("josé");
        lista1_ord.add("marcos");
        lista1_ord.add("maria");
        lista1_ord.add("pedro");
        
        lista1_ord_rev.add("pedro");
        lista1_ord_rev.add("maria");
        lista1_ord_rev.add("marcos");
        lista1_ord_rev.add("josé");
        lista1_ord_rev.add("ana");
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of quickSort method, of class Util.
     */
    @Test
    public void testQuickSortReversed() {
        System.out.println("quickSortreverse");
        ArrayList<String> L =  lista1;
        ArrayList<String> L_expected =  lista1_ord_rev;
        int ini = 0;
        int fim = L.size();
        boolean revert = true;
        System.out.println("antes:");
        System.out.println(L);
        util.Util.quickSort(L, ini, fim, revert);
        System.out.println("resultado:");
        System.out.println(L);
        System.out.println("esperado:");
        System.out.println(L_expected);
        //assertEquals(L, L_expected);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of quickSort method, of class Util.
     */
    @Test
    public void testQuickSort() {
        System.out.println("quickSort");
        ArrayList<String> L =  lista1;
        ArrayList<String> L_expected =  lista1_ord;
        int ini = 0;
        int fim = L.size();
        boolean revert = false;
        System.out.println("antes:");
        System.out.println(L);
        util.Util.quickSort(L, ini, fim, revert);
        System.out.println("resultado:");
        System.out.println(L);
        System.out.println("esperado:");
        System.out.println(L_expected);
        //assertEquals(L, L_expected);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}