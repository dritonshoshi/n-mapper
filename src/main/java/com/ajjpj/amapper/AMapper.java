package com.ajjpj.amapper;

import com.ajjpj.afoundation.collection.immutable.AOption;
import com.ajjpj.amapper.core.diff.ADiff;
import com.ajjpj.amapper.core.tpe.AQualifier;
import com.ajjpj.amapper.core.tpe.AType;


/**
 * @author arno
 */
public interface AMapper {
    AOption<Object> map(Object source, AType sourceType, AQualifier sourceQualifier, Object target, AType targetType, AQualifier targetQualifier);
    ADiff diff(Object sourceOld, Object sourceNew, AType sourceType, AQualifier sourceQualifier, AType targetType, AQualifier targetQualifier);
}
