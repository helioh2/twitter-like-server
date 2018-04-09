/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.List;

/**
 *
 * @author helio
 */
public class Util {

    public static <T extends Comparable> List<T> sortReversed(List<T> L) {

        quickSort(L, 0, L.size(), true);
        return L;

    }

    /**
     * quicksort. o revert determina se vc quer ordenar de traz pra frente ou normal
     * @param <T>
     * @param L
     * @param ini
     * @param fim
     * @param revert 
     */
    public static <T extends Comparable> void quickSort(List<T> L, int ini, int fim, boolean revert) {
        int meio;

        if (ini < fim) {
            meio = partition(L, ini, fim, revert);
            quickSort(L, ini, meio, revert);
            quickSort(L, meio + 1, fim, revert);
        }
        
        //return L;
    }

    /**
     * 
     * @param <T>
     * @param L
     * @param ini
     * @param fim
     * @return 
     */
    public static <T extends Comparable> int partition(List<T> L, int ini, int fim, boolean revert) {
        T pivo;
        int topo, i;
        pivo = L.get(ini);
        topo = ini;

        for (i = ini + 1; i < fim; i++) {
            if ((revert && L.get(i).compareTo(pivo) > 0)
                    || (!revert && L.get(i).compareTo(pivo) < 0)) {   //v[i]<pivo){
                L.set(topo, L.get(i));
                L.set(i, L.get(topo + 1));         //v[i]=v[topo+1];
                topo++;
            }
        }
        L.set(topo, pivo); //v[topo]=pivo;
        return topo;
    }
}
