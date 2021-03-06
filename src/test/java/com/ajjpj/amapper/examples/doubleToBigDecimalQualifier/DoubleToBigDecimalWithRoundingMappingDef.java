package com.ajjpj.amapper.examples.doubleToBigDecimalQualifier;

import com.ajjpj.afoundation.collection.immutable.AMap;
import com.ajjpj.amapper.core.AMapperDiffWorker;
import com.ajjpj.amapper.core.AMapperWorker;
import com.ajjpj.amapper.core.AValueMappingDef;
import com.ajjpj.amapper.core.diff.ADiffBuilder;
import com.ajjpj.amapper.core.path.APath;
import com.ajjpj.amapper.core.tpe.AQualifiedSourceAndTargetType;
import com.ajjpj.amapper.javabean.JavaBeanTypes;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * @author arno
 */
public class DoubleToBigDecimalWithRoundingMappingDef implements AValueMappingDef<Double, BigDecimal, Object> {
    @Override
    public boolean canHandle(AQualifiedSourceAndTargetType types) {
        return types.sourceType().equals(JavaBeanTypes.create(Double.class)) &&
                types.targetType().equals(JavaBeanTypes.create(BigDecimal.class)) &&
                types.targetQualifier().get("round n digits").isDefined();
    }

    @Override
    public BigDecimal map(Double sourceValue, AQualifiedSourceAndTargetType types, AMapperWorker<?> worker, AMap<String, Object> context) {
        if(sourceValue == null) {
            return null;
        }

        final int numDigits = Integer.valueOf(types.targetQualifier().get("round n digits").get());
        return BigDecimal.valueOf(sourceValue).setScale(numDigits, RoundingMode.HALF_UP);
    }

    @Override
    public void diff(ADiffBuilder diff, Double sourceOld, Double sourceNew, AQualifiedSourceAndTargetType types, AMapperDiffWorker<?> worker, AMap<String, Object> contextOld, AMap<String, Object> contextNew, APath path, boolean isDerived) {
        throw new UnsupportedOperationException("unimplemented for this example");
    }
}
