package com.ajjpj.amapper.javabean.japi;

import com.ajjpj.amapper.core.AMapperWorker;
import com.ajjpj.amapper.core.AQualifier;
import com.ajjpj.amapper.core.AType;
import com.ajjpj.amapper.javabean.builder.JavaBeanMapping;
import com.ajjpj.amapper.javabean.japi.classes.ClassRequiringContext;
import com.ajjpj.amapper.javabean.japi.classes.ClassWithContext;
import com.ajjpj.amapper.javabean.japi.classes.PriceClass;
import com.ajjpj.amapper.javabean.japi.classes.TestCurrencyProvider;
import org.junit.Test;
import scala.collection.immutable.Map;

import static org.junit.Assert.assertEquals;


/**
 * @author arno
 */
public class ContextTest {
    @Test
    public void testContext () {
        final JavaBeanMapper mapper = JavaBeanMapperBuilder.create()
            .withBeanMapping (JavaBeanMapping.create (ClassWithContext.class, ClassWithContext.class))
            .withBeanMapping (JavaBeanMapping.create(ClassRequiringContext.class, ClassRequiringContext.class)
                .removeMappingForTargetProp("price")
                .removeMappingForSourceProp("amount")
                .addOneWayMapping ("amount", Double.class, "price", PriceClass.class)
                )
            .withValueMapping(new AbstractValueMappingDef<Double, PriceClass, Object>(Double.class, PriceClass.class) {
                @Override
                public PriceClass map(Double sourceValue, AType sourceType, AQualifier sourceQualifier, AType targetType, AQualifier targetQualifier, AMapperWorker<?> worker, Map<String, Object> context) {
                    final TestCurrencyProvider tcp = (TestCurrencyProvider) context.get(TestCurrencyProvider.class.getName()).get();
                    return new PriceClass(sourceValue, tcp.getCurrency());
                }
            })
            .build();

        final ClassWithContext o = new ClassWithContext ();
        o.setCurrency ("USD");
        o.setRequiringContext (new ClassRequiringContext ());
        o.getRequiringContext ().setAmount (2.0);

        final ClassWithContext mapped = mapper.map (o, ClassWithContext.class);
        assertEquals ("2.0 USD", mapped.getRequiringContext ().getPrice ().toString ());
    }
}
