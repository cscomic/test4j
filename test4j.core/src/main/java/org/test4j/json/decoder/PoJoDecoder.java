package org.test4j.json.decoder;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.test4j.json.JSON;
import org.test4j.json.JSONException;
import org.test4j.json.decoder.base.DecoderException;
import org.test4j.json.decoder.base.MapPoJoBaseDecoder;
import org.test4j.json.encoder.object.PoJoEncoder;
import org.test4j.json.helper.JSONFeature;
import org.test4j.json.helper.JSONMap;
import org.test4j.json.helper.JSONObject;
import org.test4j.json.helper.JSONSingle;
import org.test4j.tools.commons.ClazzHelper;
import org.test4j.tools.commons.FieldHelper;

/**
 * 反序列json为pojo对象
 * 
 * @author darui.wudr
 */
@SuppressWarnings({ "rawtypes" })
public class PoJoDecoder extends MapPoJoBaseDecoder<Object> {

    public static final PoJoDecoder toPOJO = new PoJoDecoder();

    @Override
    protected void parseFromJSONMap(Object target, Type toType, JSONMap map, Map<String, Object> references) {
        Class type = ClazzHelper.getUnProxyType(target.getClass());
        List<Field> fields = ClazzHelper.getAllFields(type, PoJoEncoder.filterFields, false, false, false);
        for (JSONObject key : map.keySet()) {
            if (key == null) {
                continue;
            }
            if (!(key instanceof JSONSingle)) {
                throw new JSONException("illegal syntax, the pojo field name property must be a JSONSingle type.");
            }

            String fieldname = ((JSONSingle) key).toStringValue();
            if (this.isJSONKeyword(fieldname)) {
                continue;
            }
            Field field = this.getFieldByName(fields, fieldname);
            if (field == null) {
                continue;
            }
            try {
                Type fieldType = field.getGenericType();
                JSONObject fieldValue = map.get(key);
                Object value = JSON.toObject(fieldValue, fieldType, references);
                FieldHelper.setFieldValue(target, field, value);
            } catch (Exception e) {
                throw new JSONException("decode field[" + fieldname + "] error.", e);
            }
        }
    }

    private boolean isJSONKeyword(String name) {
        if (JSONFeature.ReferFlag.equals(name)) {
            return true;
        }
        if (JSONFeature.ClazzFlag.equals(name)) {
            return true;
        }
        if (JSONFeature.ReferFlag.equals(name)) {
            return true;
        }
        return false;
    }

    private Field getFieldByName(List<Field> fields, String name) {
        for (Field field : fields) {
            if (name.equals(field.getName())) {
                return field;
            }
        }
        return null;
    }

    @Override
    public boolean accept(Type type) {
        return true;
    }

    @Override
    protected Object getTarget(JSONMap map, Type toType) {
        Class claz = this.getTargetType(toType, map);
        if (this.isInterfaceOrAbstract(claz)) {
            throw new DecoderException("the type[" + claz.getName()
                    + "] is an interface or abstract class,that can't be instnaced.");

        } else {
            return this.newInstance(claz);
        }
    }
}
