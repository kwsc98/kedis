package pers.kedis.core.common.structure;

import pers.kedis.core.dto.KedisData;

/**
 * @author kwsc98
 */
public class Dict {

    DictEntry[] array;

    int size;

    int used;


    private static class DictEntry {
        String key;

        KedisData value;

        DictEntry next;
    }

}
