package my.interest.lang.tamil.impl;

import common.lang.impl.AbstractCharacter;
import my.interest.lang.tamil.bean.MultipleWordSplitResult;
import my.interest.lang.tamil.bean.SimpleSplitResult;
import my.interest.lang.tamil.internal.api.HandlerFactory;
import my.interest.lang.tamil.parser.impl.CompositeParser;
import my.interest.lang.tamil.parser.impl.sax.SaxParser;
import tamil.lang.TamilFactory;
import tamil.lang.TamilWord;
import tamil.lang.api.parser.*;
import tamil.lang.known.IKnownWord;
import tamil.lang.known.non.derived.IBasePeyar;
import tamil.lang.known.non.derived.IBaseVinai;
import tamil.lang.known.thodar.ThodarMozhiBuilder;
import tamil.lang.spi.CompoundWordParserProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author velsubra
 */
public class CompoundWordParserProviderImpl implements CompoundWordParserProvider {

    //static final CompoundWordParser imp =




    /**
     * Creates a new parser.
     *
     * @return the compound parser.
     */
    @Override
    public CompoundWordParser crate() {
       return new CompositeParser();

    }


}
