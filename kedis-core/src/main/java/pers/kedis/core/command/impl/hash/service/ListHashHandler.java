package pers.kedis.core.command.impl.hash.service;

import pers.kedis.core.dto.KedisData;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author kwsc98
 */
public class ListHashHandler implements HashHandlerInterface<List<KedisData>> {


    @Override
    public int delByFields(List<KedisData> dataList, Set<KedisData> fieldSet) {
        int isDel = 0;
        Iterator<KedisData> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            KedisData nextStr = iterator.next();
            if (fieldSet.contains(nextStr)) {
                iterator.remove();
                iterator.next();
                iterator.remove();
                isDel = 1;
            } else {
                iterator.next();
            }
        }
        return isDel;
    }

    @Override
    public KedisData getValueByField(List<KedisData> data, KedisData field) {
        for (int i = 0; i < data.size(); i += 2) {
            if (data.get(i).equals(field)) {
                return data.get(i + 1);
            }
        }
        return null;
    }

    @Override
    public int getValueByPattern(List<KedisData> preList, List<KedisData> dataList, int index, int count, Pattern pattern) {
        for (int i = 0; index < preList.size() && i < count; index += 2, i++) {
            if (pattern.matcher(preList.get(index).getData()).find()) {
                dataList.add(preList.get(index));
                dataList.add(preList.get(index + 1));
            }
        }
        if (index >= preList.size()) {
            index = 0;
        }
        return index;
    }

    @Override
    public void setData(List<KedisData> list, KedisData field, KedisData value) {
        boolean isHave = false;
        for (int i = 0; i < list.size(); i += 2) {
            if (Objects.equals(field.getData(), list.get(i).getData())) {
                list.set(i + 1, value);
                isHave = true;
                break;
            }
        }
        if (!isHave) {
            list.add(field);
            list.add(value);
        }
    }
}
